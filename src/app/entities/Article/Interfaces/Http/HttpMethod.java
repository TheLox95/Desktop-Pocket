package app.entities.Article.Interfaces.Http;

/**
 * Created by Leonardo on 02/12/2017.
 */
public enum HttpMethod {
    POST("POST"), GET("GET");

    private final String _method;

    HttpMethod(String post) {
        this._method = post;
    }

    public String getValue() { return _method; }
}
