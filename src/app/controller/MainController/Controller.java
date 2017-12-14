package app.controller.MainController;

import app.controller.ConnectivityController;
import app.controller.article.*;
import app.controller.helpers.NoConnectionController;
import app.entities.Article.Article.Article;
import app.entities.Article.Article.DaoArticle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import de.jensd.fx.glyphs.GlyphsDude;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class Controller extends AnchorPane{

    @FXML
    private BorderPane view;

    @FXML
    private AnchorPane centerPane;

    @FXML
            StackPane searchButton;
    @FXML
    StackPane tagsButton;

    @FXML
    StackPane favoriteButton;

    @FXML
    StackPane addButton;

    @FXML
    StackPane updateButton;

    @FXML
    StackPane listButton;

    @FXML
    VBox sideMenu;

    ObservableList<Node> articlesControllerList = FXCollections.observableArrayList();
    public Map<String, Article> articlesArray = new HashMap<String, Article>();

    DaoArticle _dao;
    ArticleList articleListComponent;

    NoConnectionController noConnectionView = new NoConnectionController();
    ConnectivityController _connectivityController;

    public Controller(ConnectivityController connectivityController, DaoArticle dao){
        _connectivityController = connectivityController;
        _dao = dao;


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../view/sample2.fxml"));
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
        this._buildSideMenu();
        articleListComponent = new ArticleList();
        centerPane.getChildren().add(articleListComponent);
        _dao.getArticleEnqueue((urlList) -> {
            for (String urlInQueue :urlList) {
                _dao.postArticleToApi(urlInQueue, (art) -> {
                    articleListComponent.add(0, art);
                    _dao.deleteArticleFromQueue(urlInQueue, (isDone) -> System.out.println("Article retrieved from table and post it to the api"));
                });
            }
        });

        _dao.getArticlesFromDB(this::onLoadArticlesFromDB);

        articleListComponent.setOnScrollBottom((is) ->
                _dao.fetchArticlesFromIndex(articleListComponent.getArticlesAmmo(),this::onLoadArticleFromApi));

        noConnectionView.onButtonClick((is) -> _dao.getArticlesFromDB(this::onLoadArticlesFromDB));


    }

    private void onLoadArticlesFromDB(ArrayList<Article> list){

        if (list.size() == 0 && _connectivityController.thereIsInternetConnection() == false){
            this.centerPane.getChildren().setAll(noConnectionView);
            return;
        }

        if (list.size() > 0){
            sideMenu.setDisable(false);

            for (Article art:list) {
                this.articlesArray.put(art.getTitle(), art);
                ArticleController artCon = new ArticleController(art.getUrl().toString());
                articlesControllerList.add(artCon);
                artCon.setTitle(art.getTitle());
                articleListComponent.add(art);
            }
        }

        if (_connectivityController.thereIsInternetConnection() == false){
            Notifications.create()
                    .title("Desktop Pocket")
                    .text("We could not load new articles.\nThere is no internet connection")
                    .showWarning();

            return;
        }

        _dao.fetchArticles(this::onLoadArticleFromApi);
    }

    private void onLoadArticleFromApi(ArrayList<Article> art){
        if (art.size() > 0){
            sideMenu.setDisable(false);
        }
        for (Article a: art) {
            if (this.articlesArray.containsKey(a.getTitle()) == false){
                articlesArray.put(a.getTitle(), a);
                ArticleController artCon = new ArticleController(a.getUrl().toString());
                articlesControllerList.add(artCon);
                artCon.setTitle(a.getTitle());
                if (articlesControllerList.size() == 0){
                    articleListComponent.add(a);
                }else{
                    articleListComponent.add(0, a);
                }
                _dao.saveArticleToDB(a, (isSAved) -> System.out.println("Aticle SAved!"));
            }else{
                System.out.println("This article is already on the list");
            }
        }
    }

    @FXML
    public void showPopup(javafx.scene.input.MouseEvent e){
        final Popup popup = new Popup();
        popup.setAutoHide(true);
        AddArticleController form = new AddArticleController(_dao, _connectivityController);
        popup.getContent().addAll(form);
        popup.show(((StackPane) e.getSource()).getParent(), e.getScreenX(), e.getScreenY());
        form.onSending((res) -> popup.hide());
        form.onResponse((Article article) -> {
            articleListComponent.add(0, article);
        });
    }

    public void showList() {
        articleListComponent = new ArticleList();
        articleListComponent.setArticles(this.articlesArray.values());
        centerPane.getChildren().add(articleListComponent);
    }

    public void showFavorites() {
        FavoriteArticlesController controller = new FavoriteArticlesController();
        controller.setArticles(this.articlesArray.values());
        centerPane.getChildren().setAll(controller);
    }


    public void showByTags() {
        ArticleTagsSearch tagView =  new ArticleTagsSearch();
        tagView.setArticles(this.articlesArray.values());
        centerPane.getChildren().setAll(tagView);
    }

    public void searchByName(){
        ArticleSearch articleSearch = new ArticleSearch();
        articleSearch.setArticles(this.articlesArray.values());
        centerPane.getChildren().setAll(articleSearch);
    }

    public void update(){
        _dao.fetchArticles(this::onLoadArticleFromApi);
    }

    private void _buildSideMenu(){
        String iconSize = "50";
        Text searchIcon = GlyphsDude.createIcon(FontAwesomeIcon.SEARCH, iconSize);
        Text addIcon = GlyphsDude.createIcon(FontAwesomeIcon.PLUS_CIRCLE, iconSize);
        Text favoriteIcon = GlyphsDude.createIcon(FontAwesomeIcon.HEART_ALT, iconSize);
        Text tagsIcon = GlyphsDude.createIcon(FontAwesomeIcon.TAGS, iconSize);
        Text updateIcon = GlyphsDude.createIcon(FontAwesomeIcon.REFRESH, iconSize);
        Text listIcon = GlyphsDude.createIcon(FontAwesomeIcon.LIST, iconSize);

        addIcon.getStyleClass().add("icon");
        searchIcon.getStyleClass().add("icon");
        favoriteIcon.getStyleClass().add("icon");
        tagsIcon.getStyleClass().add("icon");
        updateIcon.getStyleClass().add("icon");
        listIcon.getStyleClass().add("icon");

        searchButton.getChildren().setAll(searchIcon);
        addButton.getChildren().setAll(addIcon);
        favoriteButton.getChildren().setAll(favoriteIcon);
        tagsButton.getChildren().setAll(tagsIcon);
        updateButton.getChildren().setAll(updateIcon);
        listButton.getChildren().setAll(listIcon);
    }

}

