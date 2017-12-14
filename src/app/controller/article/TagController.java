package app.controller.article;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * Created by Leonardo on 04/12/2017.
 */
public class TagController extends StackPane {

    @FXML
    private Label label;

    @FXML
    BorderPane closeMark;

    @FXML
    BorderPane frame;

    private Consumer<TagController> _onCloseMarkcallback;

    private boolean _isSelected = false;
    private boolean _isActivate = true;
    private Consumer<TagController> _callback;
    private EventHandler<MouseEvent> setActivateStatus;
    private EventHandler<MouseEvent> closeMarkCallback;

    public TagController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../view/Tag.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    public void initialize(){
        TagController that = this;

        setActivateStatus = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                selected();
            }
        };

        closeMarkCallback = new EventHandler<MouseEvent>() {
            public void handle(javafx.scene.input.MouseEvent e) {
                unselected(e);
            }
        };

        this.setEventHandler(MouseEvent.MOUSE_CLICKED, setActivateStatus);
        this.closeMark.addEventHandler(MouseEvent.MOUSE_CLICKED, closeMarkCallback);
    }

    public void setText(String text) {
        this.label.setText(text);
    }

    public String getText() {
        return this.label.getText();
    }

    public void onClick(Consumer<TagController> fun){
        this._callback = fun;
    }

    public void onCloseMark(Consumer<TagController> fun){
        _onCloseMarkcallback = fun;
    }

    private void unselected(MouseEvent e){
        this._onCloseMarkcallback.accept(this);
        this.closeMark.setVisible(false);
        this._isSelected = false;
        this.frame.getParent().getStyleClass().remove("selected");
        this._isSelected = false;
        e.consume();
    }

    public void activate(){
        this._isActivate = true;
        this.getStyleClass().remove("deactivated");
        this.getStyleClass().add("unselected");
    }

    public void desactivate(){
        this._isActivate = false;
        this.getStyleClass().add("deactivated");
        this.getStyleClass().remove("unselected");
        System.out.println(this.frame.getParent().getStyleClass());
    }

    private void selected(){
        if (this._isSelected == false && _isActivate == true){
            this.frame.getParent().getStyleClass().add("selected");

            this._isSelected = true;
            this._callback.accept(this);
            this.closeMark.setVisible(true);
        }
    }

}
