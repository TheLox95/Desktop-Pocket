package app.controller.article;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by Leonardo on 01/12/2017.
 */
public class ArticleController extends HBox {
    @FXML
    Label title;

    String _url;


    public ArticleController(String url) {
        _url = url;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../view/article.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setTitle(String title){
        this.title.setText(title);
    }

    public void onClick(){
        try {
            Desktop.getDesktop().browse(new URL(_url).toURI());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
