package app.entities.Article.Interfaces.Http;

import app.entities.Article.Article.Article;

import java.util.ArrayList;

/**
 * Created by Leonardo on 30/11/2017.
 */
public interface IOnHttpResponse {
    void handleResponse(ArrayList<Article> articles);
}
