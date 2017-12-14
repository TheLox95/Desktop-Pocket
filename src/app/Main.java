package app;

import app.controller.ConnectivityController;
import app.controller.MainController.Controller;
import app.controller.login.LoginController;
import app.entities.Article.API.ApiService;
import app.entities.Article.Article.DaoArticle;
import app.entities.Article.Article.Parser;
import app.entities.Article.DB.Database;
import app.entities.Article.DB.QueryBuilder;
import app.entities.Article.Http.HttpModule;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    Stage primaryStage;
    Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception{
        scene = new Scene(new AnchorPane(), 800, 600);
        LoginController logIn = new LoginController((is) -> {
            scene.setRoot(new Controller(new ConnectivityController(),  new DaoArticle(new ApiService(new HttpModule()), new Database(new QueryBuilder()), new Parser())));
        });
        primaryStage.setTitle("Desktop Pocket");
        // primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
