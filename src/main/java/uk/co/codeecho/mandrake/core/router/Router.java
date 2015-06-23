package uk.co.codeecho.mandrake.core.router;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import uk.co.codeecho.mandrake.core.controller.Controller;
import uk.co.codeecho.mandrake.core.controller.ControllerChainImpl;
import uk.co.codeecho.mandrake.core.request.HttpMethod;
import uk.co.codeecho.mandrake.core.request.Response;
import uk.co.codeecho.mandrake.core.task.Task;
import it.sauronsoftware.cron4j.Scheduler;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import uk.co.codeecho.mandrake.core.controller.impl.RendererController;
import uk.co.codeecho.mandrake.core.controller.impl.ResponseController;
import uk.co.codeecho.mandrake.core.controller.impl.SubRouterController;
import uk.co.codeecho.mandrake.core.formatter.Formatter;
import uk.co.codeecho.mandrake.core.formatter.FormatterNotRegisteredException;
import uk.co.codeecho.mandrake.core.formatter.impl.JSONFormatter;
import uk.co.codeecho.mandrake.core.formatter.impl.TextFormatter;
import uk.co.codeecho.mandrake.core.renderer.Renderer;
import uk.co.codeecho.mandrake.core.renderer.RendererNotRegisteredException;
import uk.co.codeecho.mandrake.core.renderer.impl.jade.JadeRenderer;
import uk.co.codeecho.mandrake.core.request.Request;
import uk.co.codeecho.mandrake.core.request.impl.WrappedRequest;
import uk.co.codeecho.mandrake.core.util.pattern.PathPattern;
import uk.co.codeecho.mandrake.core.util.pattern.PathPatternMatchResult;

public class Router {

    private Scheduler scheduler;

    private Map<String, Renderer> renderers = new HashMap<String, Renderer>();
    private Map<String, Formatter> formatters = new HashMap<String, Formatter>();
    private Map<HttpMethod, Map<PathPattern, LinkedList<Controller>>> controllers = new EnumMap<HttpMethod, Map<PathPattern, LinkedList<Controller>>>(HttpMethod.class);
    private Map<Integer, LinkedList<Controller>> statusControllers = new HashMap<Integer, LinkedList<Controller>>();
    private Map<Class<? extends Exception>, LinkedList<Controller>> exceptionControllers = new HashMap<Class<? extends Exception>, LinkedList<Controller>>();
    private Map<String, Controller> controllerMap = new HashMap<String, Controller>();

    private LinkedList<Controller> context;

    public Router() {
        scheduler = new Scheduler();
        scheduler.start();
        for (HttpMethod method : HttpMethod.values()) {
            controllers.put(method, new LinkedHashMap<PathPattern, LinkedList<Controller>>());
        }
        formatters.put("json", new JSONFormatter());
        formatters.put("text", new TextFormatter());
        renderers.put("jade", new JadeRenderer());
    }

    public Response handle(Request request) {
        Request wrappedRequest = null;
        Response response = null;
        try {
            HttpMethod method = request.getMethod();
            String path = request.getPath();
            if (path.isEmpty()) {
                path = "/";
            }
            for (Entry<PathPattern, LinkedList<Controller>> entry : controllers.get(method).entrySet()) {
                PathPatternMatchResult matchResult = entry.getKey().test(path);
                if (matchResult.matches()) {
                    wrappedRequest = new WrappedRequest(request, matchResult.getParameters());
                    response = new ControllerChainImpl(wrappedRequest, entry.getValue()).advance();
                    break;
                }
            }
            if (response == null) {
                response = Response.status(404).build();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response = Response.status(500).body(ex).build();
            try {
                for (Entry<Class<? extends Exception>, LinkedList<Controller>> entry : exceptionControllers.entrySet()) {
                    if (entry.getKey().isAssignableFrom(ex.getClass())) {
                        response = new ControllerChainImpl(wrappedRequest, entry.getValue()).advance(response);
                    }
                }
            } catch (Exception ex2) {
                ex2.printStackTrace();
                response = Response.status(500).body(ex2).build();
            }
        }
        if (statusControllers.containsKey(response.getStatus())) {
            try {
                response = new ControllerChainImpl(wrappedRequest, statusControllers.get(response.getStatus())).advance(response);
            } catch (Exception ex) {
                ex.printStackTrace();
                response = Response.status(500).build();
            }
        }
        return response;
    }

    public Router route(HttpMethod method) {
        return route(method, "/{path}");
    }

    public Router route(String path) {
        List<LinkedList> contexts = new ArrayList<LinkedList>();
        PathPattern pathPattern = new PathPattern(path);
        for (HttpMethod method : HttpMethod.values()) {
            LinkedList<Controller> context = new LinkedList<Controller>();
            controllers.get(method).put(pathPattern, context);
            contexts.add(context);
        }
        this.context = new DelegatingLinkedList<Controller>(contexts);
        return this;
    }

    public Router route(HttpMethod method, String path) {
        context = new LinkedList<Controller>();
        controllers.get(method).put(new PathPattern(path), context);
        return this;
    }

    public Router route(int status) {
        context = new LinkedList<Controller>();
        statusControllers.put(status, context);
        return this;
    }

    public Router route(Class<? extends Exception> exceptionType) {
        context = new LinkedList<Controller>();
        exceptionControllers.put(exceptionType, context);
        return this;
    }

    public Router to(Controller controller) {
        context.add(controller);
        return this;
    }

    public Router to(String alias) {
        return to(controllerMap.get(alias));
    }

    public Router to(Router router) {
        return to(new SubRouterController(router));
    }

    public Router onLoad(Task task) {
        task.run();
        return this;
    }

    public Router schedule(String cronExpression, final Task task) {
        scheduler.schedule(cronExpression, new Runnable() {

            @Override
            public void run() {
                task.run();
            }
        });
        return this;
    }

    public Router render(String template) {
        for (Entry<String, Renderer> entry : renderers.entrySet()) {
            if (template.endsWith("." + entry.getKey())) {
                return to(new RendererController(entry.getValue(), template));
            }
        }
        throw new RendererNotRegisteredException(template);
    }

    public Router response(Response response) {
        return to(new ResponseController(response));
    }

    public Router response(Object response) {
        return to(new ResponseController(Response.ok(response).build()));
    }

    public Router response(int status) {
        return to(new ResponseController(Response.status(status).build()));
    }

    public Router response(int status, Object response) {
        return to(new ResponseController(Response.status(status).body(response).build()));
    }

    public Router redirect(String path) {
        return to(new ResponseController(Response.redirect(path).build()));
    }

    public Router json() {
        return format("json");
    }

    public Router text() {
        return format("text");
    }

    public Router format(String type) {
        if (formatters.containsKey(type)) {
            return to(formatters.get(type));
        } else {
            throw new FormatterNotRegisteredException(type);
        }
    }

    public void register(String alias, Controller controller) {
        controllerMap.put(alias, controller);
    }

    public void register(String type, Renderer renderer) {
        renderers.put(type, renderer);
    }

    public void register(String type, Formatter formatter) {
        formatters.put(type, formatter);
    }

    public class DelegatingLinkedList<E> extends LinkedList<E> {

        private List<LinkedList> lists = new ArrayList<LinkedList>();

        public DelegatingLinkedList(List<LinkedList> lists) {
            this.lists = lists;
        }

        @Override
        public boolean add(E e) {
            for (LinkedList list : lists) {
                list.add(e);
            }
            return true;
        }
    }

}
