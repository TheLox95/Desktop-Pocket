package app.controller.login;

import app.entities.Article.API.ApiService;
import app.entities.Article.DB.Database;
import app.entities.Article.DB.QueryBuilder;
import app.entities.Article.Http.HttpModule;
import app.entities.Article.Interfaces.DB.IDB;
import app.entities.Article.Interfaces.Http.HttpMethod;
import app.entities.Article.Interfaces.Http.IHttpModule;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by Leonardo on 12/12/2017.
 */
public class LoginController extends AnchorPane{

    @FXML
    Button logInButton;

    IDB database = new Database(new QueryBuilder());
    IHttpModule module = new HttpModule();
    PocketLoginController loginform;
    String codeToValidate;
    Consumer _logSuccesfullyCallback;


    public LoginController(Consumer<Boolean> fun){
        this._logSuccesfullyCallback = fun;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../view/LoginForm.fxml"));
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
        String apiCode = checkApiCodeOnDb();
        if (apiCode != ""){
            ApiService._apiCode = apiCode;
            this._logSuccesfullyCallback.accept(true);
        }else{
            logInButton.setOnMouseClicked((event) -> {


                try {
                    codeToValidate = askApiCode();

                    String url = "https://getpocket.com/auth/authorize?request_token="+codeToValidate+"&redirect_uri=https://www.heroku.com/";
                    loginform = new PocketLoginController(url, this::formCallback);

                    buildLoginWindow();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logInButton.setOnMouseClicked((e) -> {
                    System.out.println("upload form");
                });
            });
        }
    }

    private void buildLoginWindow(){
        Stage stage = new Stage();
        stage.setTitle("My New Stage Title");
        stage.setScene(new Scene(loginform, 450, 450));
        stage.show();
    }

    private String askApiCode() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        Map<String, String> body = new HashMap<String, String>();
        body.put("consumer_key", "72875-fb4630dbd2db545c666c9e4e");
        body.put("redirect_uri", "https://www.heroku.com/");
        StringBuffer buffer = module.makeRequest("https://getpocket.com/v3/oauth/request", HttpMethod.POST, headers, body);
        return buffer.toString().split("=")[1];
    }

    private String checkApiCodeOnDb(){
        LinkedHashMap recordCode = new LinkedHashMap();
        ArrayList<ArrayList<String>> codeRes = database.getRecord("config", recordCode);
        if (codeRes.size() > 0){
            return codeRes.get(0).get(0);
        }
        return "";
    }

    private void formCallback(Boolean res){
        Map<String, String> headers2 = new HashMap();
        headers2.put("Content-Type", "application/x-www-form-urlencoded");

        Map<String, String> body2 = new HashMap();
        body2.put("consumer_key", "72875-fb4630dbd2db545c666c9e4e");
        body2.put("code", codeToValidate);
        String apiCode = "";
        try {
            StringBuffer buff = module.makeRequest("https://getpocket.com/v3/oauth/authorize", HttpMethod.POST, headers2, body2);
            apiCode = buff.toString().split("&")[0].split("=")[1];
            ApiService api = new ApiService(module);
            System.out.println(api.pingServer(apiCode));


            LinkedHashMap record = new LinkedHashMap();
            record.put("code", apiCode);
            database.saveRecord("config", record);
            loginform.closeFrom();
            _logSuccesfullyCallback.accept(true);
            ApiService._apiCode = apiCode;
        } catch (Exception e) {
            System.out.println("not autenticated");
            System.out.println(e.getMessage());
        }
    }
}
