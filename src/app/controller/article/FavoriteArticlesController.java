package app.controller.article;

import app.entities.Article.Article.Article;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Leonardo on 06/12/2017.
 */
public class FavoriteArticlesController extends AnchorPane{

    @FXML
    AnchorPane pane;

    private ArticleList _articleList;

    private ObservableList<Article> _articles = FXCollections.observableArrayList();

    public FavoriteArticlesController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../view/FavoriteArticles.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    public void initialize(){
        _articleList = new ArticleList();
        pane.getChildren().add(_articleList);
    }

    public void setArticles(Collection<Article> coll){
        this._articles.setAll(coll);

        ArrayList<Article> favoriteList = new ArrayList<>();
        for (Article a:this._articles) {
            if (a.isFavorite() == true){
                favoriteList.add(a);
            }
        }
        this._articleList.setArticles(favoriteList);
    }
}
