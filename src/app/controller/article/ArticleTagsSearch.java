package app.controller.article;

import app.entities.Article.Article.Article;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.*;

/**
 * Created by Leonardo on 03/12/2017.
 */
public class ArticleTagsSearch extends AnchorPane{

    @FXML
    private HBox tagsHBox;

    @FXML
    AnchorPane centerPane;

    public ObservableList<Article> articlesListObservable = FXCollections.observableArrayList();
    public ArrayList<Article> articlesListObservableInmutable = new ArrayList<>();
    ObservableList<Node> articlesNodeListObservable = FXCollections.observableArrayList();
    ObservableList<Node> tagsNodeListObservable = FXCollections.observableArrayList();
    HashSet<Article> articlesAddedToList = new HashSet();
    HashSet<String> tagsAddedToList = new HashSet();

    ArticleList _articleList = new ArticleList();

    private HashSet<String> tags = new HashSet<>();
    private Map<String, HashSet<Article>> tagArticleMap = new HashMap<String, HashSet<Article>>();

    public Set<String> getTags(){
        return this.tagArticleMap.keySet();
    }


    public ArticleTagsSearch(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../view/ArticleTagSearch.fxml"));
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
        centerPane.getChildren().setAll(_articleList);
        Bindings.bindContentBidirectional(tagsNodeListObservable, tagsHBox.getChildren());
    }

    public void setArticles(Collection<Article> collection){
        articlesListObservable.addAll(collection);
        _articleList.setArticles(collection);
        articlesListObservableInmutable.addAll(collection);

        for (Article a: articlesListObservable) {
            a.getTags().forEach(tag -> {
                tags.add(tag);
                if (tagArticleMap.get(tag) == null) {
                    HashSet list = new HashSet();
                    list.add(a);
                    tagArticleMap.put(tag, list);
                } else {
                    HashSet list = tagArticleMap.get(tag);
                    list.add(a);
                    tagArticleMap.put(tag, list);
                }
            });

            tagArticleMap.forEach((key, value) -> {
                if (tagsAddedToList.contains(key) == false){
                    TagController tag = new TagController();
                    tag.onClick( (tagController) ->{
                        get(tagController.getText());
                        for (Node tagItem : tagsNodeListObservable) {
                            if (tagItem != tag){
                                ((TagController)tagItem).desactivate();
                            }
                        }
                    });
                    tag.onCloseMark( (tagController) ->{
                        _articleList.setArticles(articlesListObservableInmutable);
                        for (Node tagItem : tagsNodeListObservable) {
                            if (tagItem != tag){
                                ((TagController)tagItem).activate();
                            }
                        }
                    });
                    tag.setText(key);
                    tagsNodeListObservable.add(tag);
                    tagsAddedToList.add(key);
                }
            });
        }
    }

    public HashSet get(String tagName){
        articlesNodeListObservable.clear();
        HashSet<Article> list = tagArticleMap.get(tagName);
        _articleList.setArticles(list);
        centerPane.getChildren().setAll(_articleList);
        /*for (Article a: list) {
            ArticleController artCon = new ArticleController();
            articlesNodeListObservable.add(artCon);
            artCon.setTitle(a.getTitle());
        }*/
        return list;
    }
}
