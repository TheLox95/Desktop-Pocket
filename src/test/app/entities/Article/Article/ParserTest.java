package app.entities.Article.Article;

import app.entities.Article.Interfaces.Parser.IParser;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.net.URL;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Leonardo on 01/12/2017.
 */
public class ParserTest {
    @Test
    public void dbResult() throws Exception {
        IParser parser = new Parser();

        ArrayList<String> line = new ArrayList<String>();
        line.add("17099709");
        line.add("https://www.infoq.com/presentations/Null-References-The-Billion-Dollar-Mistake-Tony-Hoare");
        line.add("Null References: The Billion Dollar Mistake");
        line.add("0");
        line.add("0");
        line.add("Key Takeaways Null references have historically been a bad idea Early compilers provided opt-out switches for run-time checks, at the expense of correctness Programming language designers should be responsible for the errors in programs written in that language Customer requests and markets may not");
        line.add("1");
        line.add("196");
        line.add("1512683143");

        ArrayList<String> line2 = new ArrayList<String>();
        line2.add("1223561067");
        line2.add("https://blog.egorand.me/testing-runtime-permissions-lessons-learned/");
        line2.add("Testing Runtime Permissions: Lessons Learned");
        line2.add("1");
        line2.add("1");
        line2.add("Recently I've been working on adding the Android M Runtime Permissions support to the project I'm on at the moment. As an Android user, I find runtime permissions awesome.");
        line2.add("1");
        line2.add("2144");
        line2.add("1512844920");

        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        list.add(line);
        list.add(line2);
        ArrayList<Article> articles = parser.dbResult(list);

        Article article1 = articles.get(0);
        Article article2 = articles.get(1);

        Assert.assertEquals(17099709, article1.getId());
        Assert.assertEquals("https://www.infoq.com/presentations/Null-References-The-Billion-Dollar-Mistake-Tony-Hoare", article1.getUrl().toString());
        Assert.assertEquals("Null References: The Billion Dollar Mistake", article1.getTitle());
        Assert.assertEquals(false, article1.isFavorite());
        Assert.assertEquals(false, article1.isArchived());
        Assert.assertEquals("Key Takeaways Null references have historically been a bad idea Early compilers provided opt-out switches for run-time checks, at the expense of correctness Programming language designers should be responsible for the errors in programs written in that language Customer requests and markets may not", article1.getPreview());
        Assert.assertEquals(true, article1.isArticle());
        Assert.assertEquals(196, article1.getWordsAmmo());
        Assert.assertEquals("7/12/2017", article1.getSavedDate("d/MM/yyyy"));
        Assert.assertEquals(1512683143, article1.getSavedDate());

        Assert.assertEquals(1223561067, article2.getId());
        Assert.assertEquals("https://blog.egorand.me/testing-runtime-permissions-lessons-learned/", article2.getUrl().toString());
        Assert.assertEquals("Testing Runtime Permissions: Lessons Learned", article2.getTitle());
        Assert.assertEquals(true, article2.isFavorite());
        Assert.assertEquals(true, article2.isArchived());
        Assert.assertEquals("Recently I've been working on adding the Android M Runtime Permissions support to the project I'm on at the moment. As an Android user, I find runtime permissions awesome.", article2.getPreview());
        Assert.assertEquals(true, article2.isArticle());
        Assert.assertEquals(2144, article2.getWordsAmmo());
        Assert.assertEquals("9/12/2017", article2.getSavedDate("d/MM/yyyy"));
        Assert.assertEquals(1512844920, article2.getSavedDate());

    }

    @Test
    public void afterSaveResponseParser() throws Exception {
        IParser parser = new Parser();
        StringBuffer buffer = new StringBuffer();
        buffer.append("{\"item\":{\"item_id\":\"1985328398\",\"normal_url\":\"http:\\/\\/geeksforgeeks.org\\/check-if-url-is-valid-or-not-in-java\",\"resolved_id\":\"1985328398\",\"extended_item_id\":\"1985328398\",\"resolved_url\":\"http:\\/\\/www.geeksforgeeks.org\\/check-if-url-is-valid-or-not-in-java\\/\",\"domain_id\":\"95867\",\"origin_domain_id\":\"95867\",\"response_code\":\"200\",\"mime_type\":\"text\\/html\",\"content_length\":\"17523\",\"encoding\":\"utf-8\",\"date_resolved\":\"2017-12-07 08:51:37\",\"date_published\":\"2017-10-06 16:15:54\",\"title\":\"Check if URL is valid or not in Java\",\"excerpt\":\"Using Apache commons validator  In apache commons validator package we can use urlvalidator class to validate the url by checking the scheme, authority, path, query, and fragment  \\/\\/ Java program to check if a URL is valid using Apache \\/\\/ common validator. import org.apache.commons.validator.\",\"word_count\":\"528\",\"innerdomain_redirect\":\"1\",\"login_required\":\"0\",\"has_image\":\"0\",\"has_video\":\"0\",\"is_index\":\"0\",\"is_article\":\"1\",\"used_fallback\":\"0\",\"lang\":\"en\",\"time_first_parsed\":\"1512658297\",\"authors\":[],\"images\":[],\"videos\":[],\"top_image_url\":\"http:\\/\\/www.geeksforgeeks.org\\/wp-content\\/uploads\\/gfg_200X200.png\",\"resolved_normal_url\":\"http:\\/\\/geeksforgeeks.org\\/check-if-url-is-valid-or-not-in-java\",\"given_url\":\"http:\\/\\/www.geeksforgeeks.org\\/check-if-url-is-valid-or-not-in-java\\/\"},\"status\":1}");
        URL articleURL = parser.afterSaveResponseParser(buffer);
        Assert.assertEquals("http://www.geeksforgeeks.org/check-if-url-is-valid-or-not-in-java/", articleURL.toString());
    }

    @Test
    public void parseJsonResponse() throws Exception {
        IParser parser = new Parser();
        StringBuffer buffer = new StringBuffer();
        buffer.append("{\"status\":1,\"complete\":1,\"list\":{\"1906532286\":{\"item_id\":\"1906532286\",\"resolved_id\":\"1906532286\",\"given_url\":\"https:\\/\\/okdiario.com\\/curiosidades\\/2017\\/09\\/28\\/caracteristicas-destacadas-edad-contemporanea-1360156\",\"given_title\":\"Edad Contempor\\u00e1nea: Etapas, caracter\\u00edsticas y hechos importantes\",\"favorite\":\"0\",\"status\":\"0\",\"time_added\":\"1512073578\",\"time_updated\":\"1512073596\",\"time_read\":\"0\",\"time_favorited\":\"0\",\"sort_id\":0,\"resolved_title\":\"Las caracter\\u00edsticas m\\u00e1s destacadas de la Edad Contempor\\u00e1nea\",\"resolved_url\":\"https:\\/\\/okdiario.com\\/curiosidades\\/2017\\/09\\/28\\/caracteristicas-destacadas-edad-contemporanea-1360156\",\"excerpt\":\"La Edad Contempor\\u00e1nea puede definirse como el periodo hist\\u00f3rico m\\u00e1s reciente. Una etapa posterior a la Edad Moderna que comprende desde finales del siglo XVIII con el inicio de la Revoluci\\u00f3n Francesa y contin\\u00faa hasta la actualidad.\",\"is_article\":\"1\",\"is_index\":\"0\",\"has_video\":\"0\",\"has_image\":\"1\",\"word_count\":\"498\",\"amp_url\":\"https:\\/\\/okdiario.com\\/curiosidades\\/2017\\/09\\/28\\/caracteristicas-destacadas-edad-contemporanea-1360156\\/amp\"},\"1975275836\":{\"item_id\":\"1975275836\",\"resolved_id\":\"1975275836\",\"given_url\":\"https:\\/\\/www.codeproject.com\\/Articles\\/1217654\\/Building-SPA-with-Angular-and-Redux\",\"given_title\":\"https:\\/\\/www.codeproject.com\\/Articles\\/1217654\\/Building-SPA-with-Angular-and-\",\"favorite\":\"0\",\"status\":\"0\",\"time_added\":\"1511996540\",\"time_updated\":\"1511996544\",\"time_read\":\"0\",\"time_favorited\":\"0\",\"sort_id\":1,\"resolved_title\":\"Building SPA with Angular 4 and Redux\",\"resolved_url\":\"https:\\/\\/www.codeproject.com\\/Articles\\/1217654\\/Building-SPA-with-Angular-and-Redux\",\"excerpt\":\"In the current application development era, Single Page Application(SPA) is a great feature to develop the modern web based application.\",\"is_article\":\"1\",\"is_index\":\"0\",\"has_video\":\"0\",\"has_image\":\"1\",\"word_count\":\"5693\"},\"1976834834\":{\"item_id\":\"1976834834\",\"resolved_id\":\"1974049818\",\"given_url\":\"https:\\/\\/out.reddit.com\\/t3_7fxz1v?url=https%3A%2F%2Fwww.hillelwayne.com%2Fpost%2Fcontracts%2F&token=AQAAwDQfWqbfRetjhiBZfe7McWmjrZL9-4i59zdiaqR2BnRj3c53&app_name=mweb2x\",\"given_title\":\"https:\\/\\/out.reddit.com\\/t3_7fxz1v?url=https:\\/\\/www.hillelwayne.com\\/post\\/contr\",\"favorite\":\"0\",\"status\":\"0\",\"time_added\":\"1511992076\",\"time_updated\":\"1511992096\",\"time_read\":\"0\",\"time_favorited\":\"0\",\"sort_id\":2,\"resolved_title\":\"Introduction to Contract Programming\",\"resolved_url\":\"https:\\/\\/www.hillelwayne.com\\/post\\/contracts\\/\",\"excerpt\":\"I\\u2019ve namedropped contracts enough here that I think it\\u2019s finally time to go talk about them. A lot of people conflate them with class interfaces \\/ dynamic typing \\/ \\u201cyour unit tests are your contract\\u201d, which muddies the discussion and makes it hard to show their benefits.\",\"is_article\":\"1\",\"is_index\":\"1\",\"has_video\":\"0\",\"has_image\":\"0\",\"word_count\":\"1992\"}},\"error\":null,\"search_meta\":{\"search_type\":\"normal\"},\"since\":1512154683}");
        ArrayList<String> list = parser.parseApiResponse(buffer);
        Assert.assertEquals(3, list.size());
        Assert.assertEquals("{\"item_id\":\"1906532286\",\"resolved_id\":\"1906532286\",\"given_url\":\"https://okdiario.com/curiosidades/2017/09/28/caracteristicas-destacadas-edad-contemporanea-1360156\",\"given_title\":\"Edad Contemporánea: Etapas, características y hechos importantes\",\"favorite\":\"0\",\"status\":\"0\",\"time_added\":\"1512073578\",\"time_updated\":\"1512073596\",\"time_read\":\"0\",\"time_favorited\":\"0\",\"sort_id\":0,\"resolved_title\":\"Las características más destacadas de la Edad Contemporánea\",\"resolved_url\":\"https://okdiario.com/curiosidades/2017/09/28/caracteristicas-destacadas-edad-contemporanea-1360156\",\"excerpt\":\"La Edad Contemporánea puede definirse como el periodo histórico más reciente. Una etapa posterior a la Edad Moderna que comprende desde finales del siglo XVIII con el inicio de la Revolución Francesa y continúa hasta la actualidad.\",\"is_article\":\"1\",\"is_index\":\"0\",\"has_video\":\"0\",\"has_image\":\"1\",\"word_count\":\"498\",\"amp_url\":\"https://okdiario.com/curiosidades/2017/09/28/caracteristicas-destacadas-edad-contemporanea-1360156/amp\"}", list.get(0));
    }

    @Test
    public void parse() throws Exception {
        IParser parser = new Parser();

        int id = 846254930;
        String url = "http://blog.buildpath.de/javafx-testrunner/";
        String title = "JavaFX JUnit Testrunner";
        boolean isFavorite = false;
        boolean isArchived = false;
        String preview = "I deployed a small JUnit TestRunner based on http://awhite.blogspot.de/2013/04/javafx-junit-testing.html into the maven central repository.  Some JavaFX components does need the JavaFX-Application to be initialized.";
        boolean isArticle = true;
        int wordAmmo = 135;
        int tagsAmmo = 3;


        Article art1 = parser.parse("{\"item_id\":\"id\",\"resolved_id\":\"846254930\",\"given_url\":\"http://blog.buildpath.de/javafx-testrunner/\",\"given_title\":\"JavaFX JUnit Testrunner | buildpath.de\",\"favorite\":\"0\",\"status\":\"0\",\"time_added\":\"1512253142\",\"time_updated\":\"1512253145\",\"time_read\":\"0\",\"time_favorited\":\"0\",\"sort_id\":0,\"resolved_title\":\"JavaFX JUnit Testrunner\",\"resolved_url\":\"http://blog.buildpath.de/javafx-testrunner/\",\"excerpt\":\"I deployed a small JUnit TestRunner based on http://awhite.blogspot.de/2013/04/javafx-junit-testing.html into the maven central repository.  Some JavaFX components does need the JavaFX-Application to be initialized.\",\"is_article\":\"1\",\"is_index\":\"0\",\"has_video\":\"0\",\"has_image\":\"0\",\"word_count\":\"135\",\"tags\":{\"javafx\":{\"item_id\":\"846254930\",\"tag\":\"javafx\"},\"programacion\":{\"item_id\":\"846254930\",\"tag\":\"programacion\"},\"unit testing\":{\"item_id\":\"846254930\",\"tag\":\"unit testing\"}}}");
        Assert.assertEquals(id, art1.getId());
        Assert.assertEquals(url, art1.getUrl().toString());
        Assert.assertEquals(title, art1.getTitle());
        Assert.assertEquals(isFavorite, art1.isFavorite());
        Assert.assertEquals(isArchived, art1.isArchived());
        Assert.assertEquals("I deployed a small JUnit TestRunner based on http://awhite.blogspot.de/2013/04/javafx-junit-testing.html into the maven central repository.  Some JavaFX components does need the JavaFX-Application to be initialized.", art1.getPreview());
        Assert.assertEquals(isArticle, art1.isArticle());
        Assert.assertEquals(wordAmmo, art1.getWordsAmmo());
        Assert.assertEquals(tagsAmmo, art1.getTags().size());
        Assert.assertEquals("2/12/2017", art1.getSavedDate("d/MM/yyyy"));

        Article art2 = parser.parse("{\"item_id\":\"1976834834\",\"resolved_id\":\"1974049818\",\"given_url\":\"https:\\/\\/out.reddit.com\\/t3_7fxz1v?url=https%3A%2F%2Fwww.hillelwayne.com%2Fpost%2Fcontracts%2F&token=AQAAwDQfWqbfRetjhiBZfe7McWmjrZL9-4i59zdiaqR2BnRj3c53&app_name=mweb2x\",\"given_title\":\"https:\\/\\/out.reddit.com\\/t3_7fxz1v?url=https:\\/\\/www.hillelwayne.com\\/post\\/contr\",\"favorite\":\"1\",\"status\":\"1\",\"time_added\":\"1511992076\",\"time_updated\":\"1511992096\",\"time_read\":\"0\",\"time_favorited\":\"0\",\"sort_id\":2,\"resolved_title\":\"Introduction to Contract Programming\",\"resolved_url\":\"https:\\/\\/www.hillelwayne.com\\/post\\/contracts\\/\",\"excerpt\":\"I\\u2019ve namedropped contracts enough here that I think it\\u2019s finally time to go talk about them. A lot of people conflate them with class interfaces \\/ dynamic typing \\/ \\u201cyour unit tests are your contract\\u201d, which muddies the discussion and makes it hard to show their benefits.\",\"is_article\":\"0\",\"is_index\":\"1\",\"has_video\":\"0\",\"has_image\":\"0\",\"word_count\":\"1992\"}");

        Assert.assertEquals(1974049818, art2.getId());
        Assert.assertEquals("https://www.hillelwayne.com/post/contracts/", art2.getUrl().toString());
        Assert.assertEquals("Introduction to Contract Programming", art2.getTitle());
        Assert.assertEquals("It should mark as favorite", true, art2.isFavorite());
        Assert.assertEquals("it should be marked as archived", true, art2.isArchived());
        Assert.assertEquals("I’ve namedropped contracts enough here that I think it’s finally time to go talk about them. A lot of people conflate them with class interfaces / dynamic typing / “your unit tests are your contract”, which muddies the discussion and makes it hard to show their benefits.", art2.getPreview());
        Assert.assertEquals("it should not be an article", false, art2.isArticle());
        Assert.assertEquals(1992, art2.getWordsAmmo());
        Assert.assertEquals(0, art2.getTags().size());
        Assert.assertEquals(1511992076, art2.getSavedDate());


        Article art3 = parser.parse("{\n" +
                "            \"item_id\": \"1976834834\",\n" +
                "            \"resolved_id\": \"1974049818\",\n" +
                "            \"given_url\": \"https://out.reddit.com/t3_7fxz1v?url=https%3A%2F%2Fwww.hillelwayne.com%2Fpost%2Fcontracts%2F&token=AQAAwDQfWqbfRetjhiBZfe7McWmjrZL9-4i59zdiaqR2BnRj3c53&app_name=mweb2x\",\n" +
                "            \"given_title\": \"https://out.reddit.com/t3_7fxz1v?url=https://www.hillelwayne.com/post/contr\",\n" +
                "            \"favorite\": \"1\",\n" +
                "            \"status\": \"1\",\n" +
                "            \"time_added\": \"1511992076\",\n" +
                "            \"time_updated\": \"1511992096\",\n" +
                "            \"time_read\": \"0\",\n" +
                "            \"time_favorited\": \"1\",\n" +
                "            \"sort_id\": 2,\n" +
                "            \"resolved_title\": \"Introduction to Contract Programming\",\n" +
                "            \"resolved_url\": \"https://www.hillelwayne.com/post/contracts/\",\n" +
                "            \"excerpt\": \"I’ve namedropped contracts enough here that I think it’s finally time to go talk about them. A lot of people conflate them with class interfaces / dynamic typing / “your unit tests are your contract”, which muddies the discussion and makes it hard to show their benefits.\",\n" +
                "            \"is_article\": \"0\",\n" +
                "            \"is_index\": \"1\",\n" +
                "            \"has_video\": \"0\",\n" +
                "            \"has_image\": \"0\",\n" +
                "            \"word_count\": \"1992\"\n" +
                "        }");

        Assert.assertEquals(1974049818, art3.getId());
        Assert.assertEquals("https://www.hillelwayne.com/post/contracts/", art3.getUrl().toString());
        Assert.assertEquals("Introduction to Contract Programming", art3.getTitle());
        Assert.assertEquals("It should mark as favorite", true, art3.isFavorite());
        Assert.assertEquals("it should be marked as archived", true, art3.isArchived());
        Assert.assertEquals("I’ve namedropped contracts enough here that I think it’s finally time to go talk about them. A lot of people conflate them with class interfaces / dynamic typing / “your unit tests are your contract”, which muddies the discussion and makes it hard to show their benefits.", art3.getPreview());
        Assert.assertEquals("it should not be an article", false, art3.isArticle());
        Assert.assertEquals(1992, art3.getWordsAmmo());
        Assert.assertEquals(1511992076, art3.getSavedDate());

    }


}