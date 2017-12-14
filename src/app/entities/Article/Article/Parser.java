package app.entities.Article.Article;

import app.entities.Article.Interfaces.Parser.IParser;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by Leonardo on 01/12/2017.
 */
public class Parser implements IParser {
    private Gson _gson = new Gson();


    @Override
    public Article parse(String data) {
        JsonObject blankObj = this._gson.fromJson(data, JsonObject.class);

        boolean isFavorite = (blankObj.get("favorite").getAsInt() == 1) ? true: false;
        boolean isArchived = (blankObj.get("status").getAsInt() == 1) ? true: false;
        boolean isArticle = (blankObj.get("is_article").getAsInt() == 1) ? true: false;
        int wordsAmmo = blankObj.get("word_count").getAsInt();
        String preview = blankObj.get("excerpt").getAsString();
        JsonElement tagsElement = blankObj.get("tags");

        ArrayList<String> tags = new ArrayList<>();
        if (tagsElement != null){
            JsonObject tagsObj = tagsElement.getAsJsonObject();
            tagsObj.entrySet().forEach(e -> tags.add(e.getKey()));
        }

        URL url;
        try {
            url = new URL(blankObj.get("resolved_url").getAsString());
            Article a = new Article(blankObj.get("resolved_id").getAsInt(), url, blankObj.get("resolved_title").getAsString(), isFavorite, isArchived, preview, isArticle, wordsAmmo, blankObj.get("time_added").getAsInt());
            a.setTags(tags);
            return a;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> parseApiResponse(StringBuffer data){
        ArrayList<String> list = new ArrayList<String>();

        JsonObject json = this._gson.fromJson(data.toString(), JsonObject.class);
        JsonElement articlesList = json.get("list");
        JsonObject articlesJson = this._gson.fromJson(articlesList, JsonObject.class);

        articlesJson.entrySet().forEach((item) -> {
            list.add(item.getValue().toString());
        });

        return list;
    }

    @Override
    public URL afterSaveResponseParser(StringBuffer data) {
        JsonObject blankObj = this._gson.fromJson(data.toString(), JsonObject.class);
        JsonObject jsonObject = blankObj.get("item").getAsJsonObject();
        try {
            return new URL(jsonObject.get("resolved_url").getAsString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Article> dbResult(ArrayList<ArrayList<String>> data){
        ArrayList<Article> list = new ArrayList<>();
        for (ArrayList<String> arr: data) {
            boolean isFavorite = arr.get(3) == "1" ? true: false;
            boolean isArchived = arr.get(4) == "1" ? true: false;
            boolean isArticle = arr.get(6) == "1" ? true: false;
            int timeStamp = Integer.parseInt(arr.get(8));

            try {
                Article article = new Article(Integer.parseInt(arr.get(0)), new URL(arr.get(1)), arr.get(2), isFavorite, isArchived, arr.get(5), isArticle, Integer.parseInt(arr.get(7)),  timeStamp);
                list.add(article);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
