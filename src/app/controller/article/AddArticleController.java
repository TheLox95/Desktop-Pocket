package app.controller.article;

import app.controller.ConnectivityController;
import app.entities.Article.Article.Article;
import app.entities.Article.Article.DaoArticle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Consumer;

import javafx.stage.Popup;
import org.apache.commons.validator.routines.UrlValidator;

/**
 * Created by Leonardo on 06/12/2017.
 */
public class AddArticleController extends AnchorPane{

    @FXML
    Button addButton;

    @FXML
    TextField textBox;

    DaoArticle _dao;
    Consumer onSendCallback;
    Consumer onResponseCallback;
    ConnectivityController connectivityController;

    public AddArticleController(DaoArticle dao, ConnectivityController manager) {
        connectivityController = manager;
        if (dao != null){
            _dao = dao;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../view/AddArticleFrom.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void initialize(){
        addButton.setDisable(true);
        textBox.setOnKeyReleased((e) ->{
        UrlValidator defaultValidator = new UrlValidator();
        if (defaultValidator.isValid(textBox.getText()) == true){
            addButton.setDisable(false);
        }else{
            addButton.setDisable(true);
        }
        });
        addButton.setOnMouseClicked((e) ->{
            if (onSendCallback != null){
                onSendCallback.accept(0);
            }
            sendAddRequest();
        });
    }

    public void onSending(Consumer fun){
        onSendCallback = fun;
    }

    public void onResponse(Consumer<Article> fun){
        onResponseCallback = fun;
    }

    private void sendAddRequest(){
        if (connectivityController.thereIsInternetConnection() == true){
            _dao.postArticleToApi(textBox.getText(), onResponseCallback);
        }else{
            _dao.queuePostArticle(textBox.getText(), (art)->{
                System.out.println(art);
            });
        }
    }
}
