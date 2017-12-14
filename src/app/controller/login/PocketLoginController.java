package app.controller.login;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Leonardo on 12/12/2017.
 */
public class PocketLoginController extends AnchorPane{

    @FXML
    WebView webView;

    private String _url;

    private Consumer<Boolean> onLoadCallback;

    public PocketLoginController(String url, Consumer<Boolean> fun){
        onLoadCallback = fun;
        this._url = url;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../view/PocketLoginWebView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void initialize() {
        WebEngine webEngine = webView.getEngine();
        webEngine.load(this._url);

        webEngine.titleProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue == null){
                    return;
                }
                Pattern pattern = Pattern.compile("([hH]eroku)");
                Matcher matcher = pattern.matcher(newValue);

                if (matcher.find() == true){
                    System.out.println(webEngine.getLocation());
                    Matcher secondMatcher = pattern.matcher(webEngine.getLocation());
                    if (secondMatcher.find()){
                        onLoadCallback.accept(true);
                    }
                }
            }
        });
    }

    public void closeFrom(){
        Stage stage = (Stage) this.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}
