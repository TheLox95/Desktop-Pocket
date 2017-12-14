package app.entities.Article.Interfaces.DB;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/**
 * Created by Leonardo on 08/12/2017.
 */
public interface IDB {
    String saveRecord(String table, LinkedHashMap<String,String> columnValueMap);
    ArrayList<ArrayList<String>> getRecord(String table, LinkedHashMap<String,String> map);
    boolean deleteRecord(String table, LinkedHashMap<String,String> map);
}
