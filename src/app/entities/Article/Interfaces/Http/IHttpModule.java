package app.entities.Article.Interfaces.Http;

import java.util.ArrayList;
import java.util.Map;

public interface IHttpModule {
    StringBuffer makeRequest(String url, HttpMethod method, Map<String, String> headers, Map<String, String> body) throws Exception;
}
