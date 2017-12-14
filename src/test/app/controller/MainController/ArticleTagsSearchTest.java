package app.controller.MainController;

import app.controller.article.ArticleTagsSearch;
import app.entities.Article.Article.Article;
import de.saxsys.javafx.test.TestInJfxThread;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * Created by Leonardo on 03/12/2017.
 */
public class ArticleTagsSearchTest {
    @Test
    @TestInJfxThread
    public void getTags() throws Exception {
        JFXPanel fxPanel = new JFXPanel();
        ArticleTagsSearch articleTagsSearch = new ArticleTagsSearch();
        Article art1 = mock(Article.class);
        Article art2 = mock(Article.class);
        Article art3 = mock(Article.class);

        ArrayList<String> list1 = new ArrayList<>();
        list1.add("music");
        list1.add("movies");

        ArrayList<String> list2 = new ArrayList<>();
        list1.add("games");
        list1.add("movies");

        ArrayList<String> list3 = new ArrayList<>();
        list1.add("games");
        list1.add("books");

        doReturn(list1).when(art1).getTags();
        doReturn(list2).when(art2).getTags();
        doReturn(list3).when(art3).getTags();

        articleTagsSearch.articlesListObservable.add(art1);
        articleTagsSearch.articlesListObservable.add(art2);
        articleTagsSearch.articlesListObservable.add(art3);

        Assert.assertEquals(4 , articleTagsSearch.getTags().size());
    }

    @Test
    @TestInJfxThread
    public void get() throws Exception {
        JFXPanel fxPanel = new JFXPanel();
        ArticleTagsSearch articleTagsSearch = new ArticleTagsSearch();
        Article art1 = mock(Article.class);
        Article art2 = mock(Article.class);
        Article art3 = mock(Article.class);

        ArrayList<String> list1 = new ArrayList<>();
        list1.add("music");
        list1.add("movies");

        ArrayList<String> list2 = new ArrayList<>();
        list2.add("games");
        list2.add("movies");

        ArrayList<String> list3 = new ArrayList<>();
        list3.add("games");
        list3.add("books");

        doReturn(list1).when(art1).getTags();
        doReturn(list2).when(art2).getTags();
        doReturn(list3).when(art3).getTags();

        articleTagsSearch.articlesListObservable.add(art1);
        articleTagsSearch.articlesListObservable.add(art2);
        articleTagsSearch.articlesListObservable.add(art3);

        Assert.assertEquals(2 , articleTagsSearch.get("games").size());
        Assert.assertEquals(2 , articleTagsSearch.get("movies").size());
        Assert.assertEquals(1 , articleTagsSearch.get("music").size());
        Assert.assertEquals(1 , articleTagsSearch.get("books").size());
    }

}