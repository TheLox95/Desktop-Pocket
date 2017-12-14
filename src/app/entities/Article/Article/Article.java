package app.entities.Article.Article;

import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Leonardo on 30/11/2017.
 */
public class Article implements Comparable<Article>{
    private int _id;
    private URL _url;
    private String _title;
    private boolean _isFavorite;
    private boolean _isArchived;
    private String _preview;
    private boolean _isArticle;
    private enum _hasImage {NOT_HAS, HAS, IS}
    private enum _hasVideo {NOT_HAS, HAS, IS}
    private int _wordsAmmo;
    private ArrayList<String> _tags;
    private int _savedDate;

    public Article(int _id, URL _url, String _title, boolean _isFavorite, boolean _isArchived, String _preview, boolean _isArticle, int _wordsAmmo, int stamp) {
        this._id = _id;
        this._url = _url;
        this._title = _title;
        this._isFavorite = _isFavorite;
        this._isArchived = _isArchived;
        this._preview = _preview;
        this._isArticle = _isArticle;
        this._wordsAmmo = _wordsAmmo;
        this._savedDate = stamp;
    }

    public ArrayList<String> getTags() {
        return _tags;
    }

    public Article setTags(ArrayList<String> tags) {
        _tags = tags;
        return this;
    }

    public int getId() {
        return _id;
    }

    public URL getUrl() {
        return _url;
    }

    public String getTitle() {
        return _title;
    }

    public boolean isFavorite() {
        return _isFavorite;
    }

    public boolean isArchived() {
        return _isArchived;
    }

    public String getPreview() {
        return _preview;
    }

    public boolean isArticle() {
        return _isArticle;
    }

    public int getWordsAmmo() {
        return _wordsAmmo;
    }


    /**
     * Return the saved Date of the article acording to the given format
     * */
    public String getSavedDate(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date((long)_savedDate*1000));
    }

    /**
     * Return the saved date of the article
     * */
    public int getSavedDate() {
        return _savedDate;
    }


    @Override
    public int compareTo(Article other) {
        return Integer.compare(other.getSavedDate(), this._savedDate);
    }
}
