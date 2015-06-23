package uk.co.codeecho.mandrake.desktop;

import java.net.URL;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import uk.co.codeecho.mandrake.core.router.Router;
import uk.co.codeecho.mandrake.core.url.InternalURLStreamHandlerFactory;

public abstract class Application extends javafx.application.Application {

    private static final String BASE_URL = "http://app/";

    @Override
    public void start(Stage stage) {

        Scene scene = new Scene(new Group());

        URL.setURLStreamHandlerFactory(new InternalURLStreamHandlerFactory(setup(stage)));

        VBox root = new VBox();

        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(browser);

        webEngine.load(BASE_URL + getRoot());

        root.getChildren().addAll(scrollPane);
        scene.setRoot(root);

        stage.setScene(scene);
        stage.show();
    }
    
    protected abstract Router setup(Stage stage);
    
    protected String getRoot(){
        return "";
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
    }
}
