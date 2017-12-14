package app.controller.article;

import app.entities.Article.Article.Article;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.ExtractedResult;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Leonardo on 05/12/2017.
 */
public class ArticleSearch extends AnchorPane {

    @FXML
    AnchorPane container;

    @FXML
    TextField searchTextBox;

    ArticleList articleListComponent;
    ArrayList<Article> _articlesList = new ArrayList<Article>();
    Map<String, Article> _articlesMap = new HashMap<String, Article>();

    public ArticleSearch() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../view/ArticleNameSearch.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void initialize() {
        articleListComponent = new ArticleList();
        container.getChildren().add(0, articleListComponent);
        searchTextBox.setOnKeyPressed((e) ->{
            String textToSearch = ((TextField)e.getSource()).getText();
            List<String> list = _articlesList.stream().map((item) -> item.getTitle()).collect(Collectors.toList());
            List<ExtractedResult> searchResult = FuzzySearch.extractSorted(textToSearch, list);

            ArrayList<Article> ordenedArticles = new ArrayList<>();
            for (ExtractedResult itemResult :searchResult) {
                Article art = _articlesMap.get(itemResult.getString());
                ordenedArticles.add(art);
            }
            articleListComponent.setArticles(ordenedArticles);
        });
    }

    public void setArticles(Collection<Article> collection){
        for (Article a: collection) {
            _articlesMap.put(a.getTitle(), a);
        }
        _articlesList.addAll(collection);
        articleListComponent.setArticles(collection);
    }
}
