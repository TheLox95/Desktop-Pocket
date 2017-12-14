package app.entities.Article.DB;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Leonardo on 08/12/2017.
 */
public class QueryBuilderTest {
    @Test
    public void buildDeleteQuery() throws Exception {
        QueryBuilder queryBuilder = new QueryBuilder();

        LinkedHashMap<String, String> map = new LinkedHashMap();
        map.put("ID", "2");

        Assert.assertEquals("DELETE FROM `TEST` WHERE ID='2'", queryBuilder.buildDeleteQuery("TEST", map));
    }

    @Test
    public void buildSelectQuery() throws Exception {
        QueryBuilder queryBuilder = new QueryBuilder();

        LinkedHashMap<String, String> map = new LinkedHashMap();
        map.put("id", "65465");

        Assert.assertEquals("SELECT * from `article` WHERE `id`='65465'", queryBuilder.buildSelectQuery("article", map));

        LinkedHashMap<String, String> map2 = new LinkedHashMap();

        Assert.assertEquals("SELECT * from `article`", queryBuilder.buildSelectQuery("article", map2));
    }

    @Test
    public void buildSaveQuery() throws Exception {
        QueryBuilder queryBuilder = new QueryBuilder();

        LinkedHashMap<String, String> map = new LinkedHashMap();
        map.put("id", "65465");
        map.put("url", "www.google.com");
        map.put("title", "Example title");
        map.put("isFavorite", "true");
        map.put("isArchived", "false");
        map.put("preview", "This is a preview");
        map.put("isArticle", "false");
        map.put("wordsAmmo", "500");
        map.put("savedDate", "10/12/2017");

        String query = queryBuilder.buildSaveQuery("article", map);

        Assert.assertEquals("INSERT INTO `article` (`id`, `url`, `title`, `isFavorite`, `isArchived`, `preview`, `isArticle`, `wordsAmmo`, `savedDate`) VALUES (?,?,?,?,?,?,?,?,?)", query);
    }

}