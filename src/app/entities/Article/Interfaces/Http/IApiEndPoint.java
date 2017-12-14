package app.entities.Article.Interfaces.Http;

import app.entities.Article.Article.Article;
import app.entities.Article.Http.ServerResponseException;

import java.util.ArrayList;


/**
 * Created by Leonardo on 30/11/2017.
 */
public interface IApiEndPoint {
    StringBuffer fetchArticles(int index) throws ServerResponseException;
    StringBuffer fetchArticleByUrl(String url) throws ServerResponseException;
    StringBuffer postArticle(String url) throws ServerResponseException;
}
