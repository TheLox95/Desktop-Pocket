package app.entities.Article.Http;

public class ServerResponseException  extends Exception {
    private int _code;

    public ServerResponseException(int code, String message) {
        super(message);
        this._code = code;
    }
}

