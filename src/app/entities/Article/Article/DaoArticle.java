package app.entities.Article.Article;

import app.entities.Article.Interfaces.DB.IDB;
import app.entities.Article.Interfaces.Http.IApiEndPoint;
import app.entities.Article.Interfaces.Http.IOnHttpResponse;
import app.entities.Article.Interfaces.Parser.IParser;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.function.Consumer;

/**
 * Created by Leonardo on 30/11/2017.
 */
public class DaoArticle {
    private IDB _dbService;
    private IApiEndPoint _apiConsumer;
    private IParser _parser;
    private ArrayList<Article> _articles = new ArrayList<>();

    public DaoArticle(IApiEndPoint service, IDB dbService, IParser parser) {
        this._dbService = dbService;
        this._apiConsumer = service;
        this._parser = parser;
    }

    public void fetchArticles(IOnHttpResponse fun) {
        Service<ArrayList<Article>> service = new Service<ArrayList<Article>>() {
            @Override
            protected Task<ArrayList<Article>> createTask() {
                return new Task<ArrayList<Article>>() {
                    @Override
                    protected ArrayList<Article> call() throws Exception {
                        ArrayList<Article> list = new ArrayList<Article>();
                        StringBuffer res = _apiConsumer.fetchArticles(0);
                        ArrayList<String> lines = _parser.parseApiResponse(res);

                        lines.forEach((line) -> list.add(_parser.parse(line)));

                        return list;
                    }

                    ;
                };
            }
        };

        service.start();

        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

            @Override
            public void handle(WorkerStateEvent t) {
                fun.handleResponse(((ArrayList<Article>) t.getSource().getValue()));
            }
        });
    }

    public void fetchArticlesFromIndex(int index, IOnHttpResponse fun) {
        Service<ArrayList<Article>> service = new Service<ArrayList<Article>>() {
            @Override
            protected Task<ArrayList<Article>> createTask() {
                return new Task<ArrayList<Article>>() {
                    @Override
                    protected ArrayList<Article> call() throws Exception {
                        ArrayList<Article> list = new ArrayList<Article>();
                        StringBuffer res = _apiConsumer.fetchArticles(index);
                        ArrayList<String> lines = _parser.parseApiResponse(res);

                        lines.forEach((line) -> list.add(_parser.parse(line)));

                        return list;
                    }

                    ;
                };
            }
        };

        service.start();

        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

            @Override
            public void handle(WorkerStateEvent t) {
                fun.handleResponse(((ArrayList<Article>) t.getSource().getValue()));
            }
        });
    }

    public void postArticleToApi(String url, Consumer<Article> fun) {
        Service<Article> service = new Service<Article>() {
            @Override
            protected Task<Article> createTask() {
                return new Task<Article>() {
                    @Override
                    protected Article call() throws Exception {

                        StringBuffer buffer = _apiConsumer.postArticle(url);
                        URL url = _parser.afterSaveResponseParser(buffer);
                        StringBuffer articleBuffer = _apiConsumer.fetchArticleByUrl(url.toString());
                        return _parser.parse(_parser.parseApiResponse(articleBuffer).get(0));
                    }

                    ;
                };
            }
        };

        service.start();

        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

            @Override
            public void handle(WorkerStateEvent t) {
                fun.accept(((Article) t.getSource().getValue()));
            }
        });

    }

    public void saveArticleToDB(Article article, Consumer<Boolean> fun) {
        Service<Boolean> service = new Service<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        System.out.println(article.getId());
                        LinkedHashMap<String, String> map = new LinkedHashMap();
                        map.put("id", String.valueOf(article.getId()));
                        map.put("url", article.getUrl().toString());
                        map.put("title", article.getTitle());
                        map.put("isFavorite", (article.isFavorite() == true) ? "1" : "0");
                        map.put("isArchived", (article.isArchived() == true) ? "1" : "0");
                        map.put("preview", article.getPreview());
                        map.put("isArticle", (article.isArticle() == true) ? "1" : "0");
                        map.put("wordsAmmo", String.valueOf(article.getWordsAmmo()));
                        map.put("savedDate", String.valueOf(article.getSavedDate()));

                        _dbService.saveRecord("article", map);

                        article.getTags().forEach((tag) -> {
                            LinkedHashMap<String, String> map2 = new LinkedHashMap();
                            map2.put("article", String.valueOf(article.getId()));
                            map2.put("title", tag);
                            _dbService.saveRecord("tag", map2);
                        });

                        return true;
                    }

                    ;
                };
            }
        };

        service.start();

        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

            @Override
            public void handle(WorkerStateEvent t) {
                fun.accept(((Boolean) t.getSource().getValue()));
            }
        });

        service.setOnFailed((err) -> {
            System.out.println(err.getSource().getException());
        });


    }

    public void getArticlesFromDB(Consumer<ArrayList<Article>> fun) {
        Service<ArrayList<Article>> service = new Service<ArrayList<Article>>() {
            @Override
            protected Task<ArrayList<Article>> createTask() {
                return new Task<ArrayList<Article>>() {
                    @Override
                    protected ArrayList<Article> call() throws Exception {
                        LinkedHashMap<String, String> map = new LinkedHashMap();

                        ArrayList<Article> list = _parser.dbResult(_dbService.getRecord("article", map));
                        Collections.sort(list);
                        System.out.println(list.size());

                        for (int i = 0; i < list.size(); i++) {
                            LinkedHashMap<String, String> map2 = new LinkedHashMap();
                            map2.put("article", String.valueOf(list.get(i).getId()));
                            ArrayList<ArrayList<String>> tags = _dbService.getRecord("tag", map2);
                            ArrayList tagsToAdd = new ArrayList();
                            for (ArrayList<String> tag:tags) {
                                tagsToAdd.add(tag.get(1));
                            }

                            list.get(i).setTags(tagsToAdd);
                        }

                        return list;
                    }

                    ;
                };
            }
        };

        service.start();

        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

            @Override
            public void handle(WorkerStateEvent t) {
                fun.accept(((ArrayList<Article>) t.getSource().getValue()));
            }
        });
    }

    public void queuePostArticle(String url, Consumer<ArrayList<String>> fun) {
        Service<Boolean> service = new Service<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        LinkedHashMap<String, String> map = new LinkedHashMap();
                        map.put("url", url);

                        return _dbService.saveRecord("articleQueue", map) instanceof String;
                    };
                };
            }
        };

        service.start();

        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

            @Override
            public void handle(WorkerStateEvent t) {
                fun.accept(((ArrayList<String>) t.getSource().getValue()));
            }
        });
    }

    public void getArticleEnqueue(Consumer<ArrayList<String>> fun) {

        Service<ArrayList<String>> service = new Service<ArrayList<String>>() {
            @Override
            protected Task<ArrayList<String>> createTask() {
                return new Task<ArrayList<String>>() {
                    @Override
                    protected ArrayList<String> call() throws Exception {
                        LinkedHashMap<String, String> map = new LinkedHashMap();
                        ArrayList<ArrayList<String>> res = _dbService.getRecord("articlequeue", map);
                        ArrayList<String> list = new ArrayList<String>();

                        for (ArrayList<String> line : res) {
                            list.add(line.get(0));
                        }

                        return list;
                    }

                    ;
                };
            }
        };

        service.start();

        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

            @Override
            public void handle(WorkerStateEvent t) {
                fun.accept(((ArrayList<String>) t.getSource().getValue()));
            }
        });

    }

    public void deleteArticleFromQueue(String url, Consumer<Boolean> fun){
        Service<Boolean> service = new Service<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        LinkedHashMap<String, String> map = new LinkedHashMap();
                        map.put("url", url);
                        return _dbService.deleteRecord("articlequeue", map);
                    };
                };
            }
        };

        service.start();

        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

            @Override
            public void handle(WorkerStateEvent t) {
                fun.accept(((boolean) t.getSource().getValue()));
            }
        });

    }


}
