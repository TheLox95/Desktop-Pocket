package app.controller.helpers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * Created by Leonardo on 13/12/2017.
 */
public class NoConnectionController extends AnchorPane{

    @FXML
    Button reTryButton;

    public NoConnectionController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../view/NoConnection.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void onButtonClick(Consumer fun){
        reTryButton.setOnMouseClicked((e) -> fun.accept(true));
    }
}
