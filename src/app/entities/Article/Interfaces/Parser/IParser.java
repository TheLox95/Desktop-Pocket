package app.entities.Article.Interfaces.Parser;

import app.entities.Article.Article.Article;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Leonardo on 30/11/2017.
 */
public interface IParser {
    Article parse(String data);
    ArrayList<String> parseApiResponse(StringBuffer data);
    URL afterSaveResponseParser(StringBuffer data);
    ArrayList<Article> dbResult(ArrayList<ArrayList<String>> data);
}
