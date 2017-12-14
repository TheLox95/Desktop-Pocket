package app.entities.Article.API;

import app.entities.Article.Article.Article;
import app.entities.Article.Http.ServerResponseException;
import app.entities.Article.Interfaces.Http.HttpMethod;
import app.entities.Article.Interfaces.Http.IApiEndPoint;
import app.entities.Article.Interfaces.Http.IHttpModule;
import app.entities.Article.Interfaces.Http.IOnHttpResponse;
import app.entities.Article.Interfaces.Parser.IParser;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Leonardo on 30/11/2017.
 */
public class ApiService implements IApiEndPoint {
    private ArrayList<Article> _articles;
    private ArrayList<IOnHttpResponse> _callbacks;
    private IParser _parser;
    private IHttpModule _httpModule;
    private String _url = "https://getpocket.com/v3/get";
    private String _consumerKey = "72875-fb4630dbd2db545c666c9e4e";
    public static String _apiCode = "";
    private String _postJsonData = new StringBuilder("access_token=25079c48-b592-e1fc-f9e0-11536b&consumer_key="+_consumerKey+"&count=3&detailType=complete").toString();

    public ApiService(IHttpModule http){
        this._httpModule = http;
    }

    public boolean pingServer(String apiCode){
        Map<String, String> headers = new HashMap();
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        Map<String, String> body = new HashMap();
        body.put("access_token", _apiCode);
        body.put("consumer_key", "72875-fb4630dbd2db545c666c9e4e");
        body.put("count", "1");
        body.put("detailType", "complete");

        try {
            StringBuffer res =  _httpModule.makeRequest(this._url, HttpMethod.POST, headers, body);
            return res instanceof StringBuffer;

        } catch (Exception e) {
            if (e.getMessage() != null){
                return false;
            }
        }
        return false;
    }

    @Override
    public StringBuffer fetchArticles(int index) throws ServerResponseException {
        StringBuffer res = this._buildFetchRequest(index);
        return res;
    }

    @Override
    public StringBuffer fetchArticleByUrl(String url) throws ServerResponseException {
        Map<String, String> headers = new HashMap();
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        Map<String, String> body = new HashMap();
        body.put("access_token", _apiCode);
        body.put("consumer_key", "72875-fb4630dbd2db545c666c9e4e");
        body.put("search", url);
        body.put("detailType", "complete");

        try {
            StringBuffer res =  _httpModule.makeRequest(this._url, HttpMethod.POST, headers, body);
            return res;

        } catch (Exception e) {
            throw _processErrors(e);
        }
    }

    private StringBuffer _buildFetchRequest(int index) throws ServerResponseException {
        Map<String, String> headers = new HashMap();
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        Map<String, String> body = new HashMap();
        body.put("access_token", _apiCode);
        body.put("consumer_key", "72875-fb4630dbd2db545c666c9e4e");
        body.put("count", "20");
        if (index != 0){
            body.put("offset", String.valueOf(index));
        }
        body.put("detailType", "complete");

        try {
            StringBuffer res =  _httpModule.makeRequest(this._url, HttpMethod.POST, headers, body);
            return res;

        } catch (Exception e) {
            throw _processErrors(e);
        }
    }

    @Override
    public StringBuffer postArticle(String url) throws ServerResponseException {
        Map<String, String> headers = new HashMap();
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        Map<String, String> body = new HashMap();
        body.put("access_token", _apiCode);
        body.put("consumer_key", "72875-fb4630dbd2db545c666c9e4e");
        body.put("url", url);

        try {
            System.out.println("making request");
            return _httpModule.makeRequest("https://getpocket.com/v3/add", HttpMethod.POST, headers, body);

        } catch (Exception e) {
            System.out.println("erorr" + e.getMessage());
            throw _processErrors(e);
        }

    }

    public ServerResponseException _processErrors(Exception e) {
        ServerResponseException ex = new ServerResponseException(400, "Server response with: 400 - Invalid request, please make sure you follow the documentation for proper syntax");

        if(e.getMessage().contains("401") == true){
            ex = new ServerResponseException(401, "Server response with: 401 - Problem authenticating the user");
        }else if(e.getMessage().contains("403") == true){
            ex = new ServerResponseException(403, "Server response with: 403 - User was authenticated, but access denied due to lack of permission or rate limiting");
        }else if(e.getMessage().contains("503") == true){
            ex = new ServerResponseException(503, "Server response with: 503 - Pocket's sync server is down for scheduled maintenance.");
        }else if(e.getMessage().contains("200") == false){
            ex = new ServerResponseException(200, "Server response with: Unexpected error different from 200 status code");
        }
        return ex;
    }
}
