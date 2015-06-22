package uk.co.codeecho.mandrake.core.url;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import uk.co.codeecho.mandrake.core.request.HttpMethod;
import uk.co.codeecho.mandrake.core.request.Request;
import uk.co.codeecho.mandrake.core.request.Response;
import uk.co.codeecho.mandrake.core.request.impl.RequestImpl;
import uk.co.codeecho.mandrake.core.router.Router;

public class InternalURLConnection extends HttpURLConnection {

    private Router router;
    private Response response;
    private HttpMethod method = HttpMethod.GET;
    private boolean handled = false;
    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    public InternalURLConnection(Router router, URL u) {
        super(u);
        this.router = router;
    }

    @Override
    public void connect() throws IOException {

    }

    private void handle() throws IOException {
        if (!handled) {
            String path = url.getPath();
            Request request;
            if (method.equals(HttpMethod.GET)) {
                String query = url.getQuery();
                Map<String, String> parameters = new HashMap<String, String>();
                if (query != null) {
                    String[] params = query.split("&");
                    for (String param : params) {
                        String paramName = param.substring(0, param.indexOf("="));
                        String paramValue = param.substring(param.indexOf("=") + 1);
                        parameters.put(paramName, paramValue);
                    }
                }
                request = new RequestImpl(method, path, query, parameters);
            } else {
                request = new RequestImpl(method, path, new ByteArrayInputStream(outputStream.toByteArray()));
            }
            response = router.handle(request);
            if (response.getStatus() >= 300 && response.getStatus() < 400) {
                String location = response.getHeader("Location");
                url = new URL(url.getProtocol(), url.getHost(), url.getPort(), "/" + location);
                handle();
            } else {
                handled = true;
            }
        }
    }

    @Override
    public void disconnect() {

    }

    @Override
    public boolean usingProxy() {
        return false;
    }

    @Override
    public void setRequestMethod(String method) throws ProtocolException {
        this.method = HttpMethod.valueOf(method);
    }

    @Override
    public int getResponseCode() throws IOException {
        handle();
        return response.getStatus();
    }

    @Override
    public boolean getDoOutput() {
        return true;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return outputStream;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        handle();
        Object body = response.getBody();
        InputStream data;
        if (body == null) {
            body = "";
        }
        if (body instanceof InputStream) {
            data = (InputStream) body;
        } else {
            data = new ByteArrayInputStream(body.toString().getBytes());
        }
        return data;
    }

}
