package uk.co.codeecho.mandrake.core.url;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import uk.co.codeecho.mandrake.core.router.Router;

public class InternalURLStreamHandlerFactory implements URLStreamHandlerFactory{

    private Router router;

    public InternalURLStreamHandlerFactory(Router router) {
        this.router = router;
    }
    
    @Override
    public URLStreamHandler createURLStreamHandler(String protocol) {
        if(protocol.equals("http") || protocol.equals("https")){
            return new InternalURLStreamHandler(router);
        }else{
            return null;
        }
    }

}
