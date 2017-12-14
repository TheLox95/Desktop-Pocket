package app.entities.Article.Http;

import app.entities.Article.Interfaces.Http.HttpMethod;
import app.entities.Article.Interfaces.Http.IHttpModule;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Leonardo on 02/12/2017.
 */
public class HttpModuleTest {
    @Test
    public void makeRequest() throws Exception {
        Map<String, String> body = new HashMap();
        body.put("access_token", "25079c48-b592-e1fc-f9e0-11536b");
        body.put("consumer_key", "72875-fb4630dbd2db545c666c9e4e");
        body.put("count", "3");
        body.put("detailType", "complete");

        IHttpModule module = new HttpModule();
        try{
            module.makeRequest("https://getpocket.com/v3/get", HttpMethod.POST, new HashMap(), body);
        }catch (IOException e){
            System.out.println(e.getLocalizedMessage());
            System.out.println(e.getMessage());
        }
    }

}