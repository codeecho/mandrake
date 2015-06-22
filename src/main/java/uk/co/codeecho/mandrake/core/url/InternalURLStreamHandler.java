package uk.co.codeecho.mandrake.core.url;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import uk.co.codeecho.mandrake.core.router.Router;

public class InternalURLStreamHandler extends URLStreamHandler{

    private Router router;

    public InternalURLStreamHandler(Router router) {
        this.router = router;
    }
    
    @Override
    protected URLConnection openConnection(URL u) throws IOException {
        return new InternalURLConnection(router, u);
    }

}
