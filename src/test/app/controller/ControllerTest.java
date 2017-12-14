package app.controller;

import app.Main;
import app.controller.MainController.Controller;
import app.entities.Article.Article.DaoArticle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testfx.framework.junit.ApplicationTest;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

/**
 * Created by Leonardo on 30/11/2017.
 */

public class ControllerTest extends ApplicationTest {

    @Override
    public void start (Stage stage) throws Exception {
        ConnectivityController mockConectControll= Mockito.mock(ConnectivityController.class);
        DaoArticle mockDao= Mockito.mock(DaoArticle.class);
        when(mockConectControll.thereIsInternetConnection()).thenReturn(false);
        doAnswer(
                new Answer<ArrayList>() {
                    public ArrayList answer(InvocationOnMock invocation) {
                        ((Consumer) invocation.getArguments()[0]).accept(new ArrayList<>());
                        return null;
                    }
                }).when(mockDao).getArticlesFromDB(any(Consumer.class));

        Controller controller = new Controller(mockConectControll, mockDao);

        stage.setScene(new Scene(controller));
        stage.show();
        stage.toFront();
    }

    @Test
    public void testEnglishInput () {
        Label message = lookup("There is no internet conenction").query();
        assertEquals("There is no internet conenction", message.getText());
    }

}