package app.entities.Article.Article;

import app.entities.Article.API.ApiService;
import app.entities.Article.Http.HttpModule;
import app.entities.Article.Interfaces.DB.IDB;
import app.entities.Article.Interfaces.Http.IApiEndPoint;
import app.entities.Article.Interfaces.Http.IOnHttpResponse;
import de.saxsys.javafx.test.JfxRunner;
import de.saxsys.javafx.test.TestInJfxThread;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import org.junit.Test;
import static org.mockito.Mockito.*;

import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.Assert.*;

/**
 * Created by Leonardo on 01/12/2017.
 */
@RunWith(JfxRunner.class)
public class DaoArticleTest {

    @Test
    @TestInJfxThread
    public void getAllArticles() throws Exception {
        ApiService mockApi = Mockito.mock(ApiService.class);
        IDB mockDB = Mockito.mock(IDB.class);

        ArrayList<Article> articlesList = new ArrayList<>();
        articlesList.add(new Article(6546, new URL("http://www.vogella.com/tutorials/Mockito/article.html"), "eXAMPLE", true, false, "This is an example", true, 465, 1512658297));
        // TODO: Fix this test
        when(mockApi.fetchArticles(0)).thenReturn(new StringBuffer(articlesList.toString()));

        DaoArticle dap = new DaoArticle(mockApi, mockDB, new Parser());

        dap.fetchArticles((t) -> assertEquals(1, t.size()));

        Thread.sleep(1000);

    }

}