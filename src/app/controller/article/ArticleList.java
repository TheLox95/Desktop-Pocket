package app.controller.article;

import app.entities.Article.API.ApiService;
import app.entities.Article.Article.Article;
import app.entities.Article.Article.DaoArticle;
import app.entities.Article.Article.Parser;
import app.entities.Article.Http.HttpModule;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

/**
 * Created by Leonardo on 04/12/2017.
 */
public class ArticleList extends AnchorPane {

    @FXML
    private VBox articleVBox;

    @FXML
    ScrollPane scrollPane;

    ObservableList<Article> articlesList = FXCollections.observableArrayList();
    ObservableList<Node> articlesNodeList = FXCollections.observableArrayList();
    private Consumer _onScrollBottom;

    public ArticleList() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../view/ArticleList.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        scrollPane.vvalueProperty().addListener(
                (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    if (newValue.doubleValue() == scrollPane.getVmax()) {
                        _onScrollBottom.accept(true);
                    }
                }
        );
    }

    public int getArticlesAmmo(){
        return articlesNodeList.size();
    }

    public ArticleList setArticles(Collection<Article> articles){
        LinkedList<Node> list = new LinkedList<>();
        articles.forEach((art) ->{
            ArticleController articleComponent = new ArticleController();
            articleComponent.setTitle(art.getTitle());
            list.addLast(articleComponent);
        });
        articlesNodeList.setAll(list);
        articlesList.setAll(articles);
        return this;
    }

    public ArticleList add(Article article){
        ArticleController articleComponent = new ArticleController();
        articleComponent.setTitle(article.getTitle());
        articlesNodeList.add(articlesNodeList.size(), articleComponent);
        this.articlesList.add(articlesList.size(), article);
        return this;
    }

    public ArticleList add(int position, Article article){
        ArticleController articleComponent = new ArticleController();
        articleComponent.setTitle(article.getTitle());
        articlesNodeList.add(position, articleComponent);
        this.articlesList.add(position, article);
        return this;
    }

    public void setOnScrollBottom(Consumer fun){
        this._onScrollBottom = fun;
    }

    @FXML
    private void initialize() {
        Bindings.bindContentBidirectional(articlesNodeList, articleVBox.getChildren());
    }
}
