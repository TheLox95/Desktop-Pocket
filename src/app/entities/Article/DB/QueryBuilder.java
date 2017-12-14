package app.entities.Article.DB;

import java.sql.PreparedStatement;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Leonardo on 08/12/2017.
 */
public class QueryBuilder {

    private String _quoteMark = "\'";

    public String buildSelectQuery(String table, LinkedHashMap<String,String> columnValueMap){
        StringBuilder sqlSavequery = new StringBuilder();
        sqlSavequery.append("SELECT * from `" + table + "`");

        if (columnValueMap.size() > 0){
            sqlSavequery.append(" WHERE ");
            for (Map.Entry entry :columnValueMap.entrySet()) {
                sqlSavequery.append("`" + entry.getKey() + "`='" + entry.getValue() + "' &");
            }
            sqlSavequery = sqlSavequery.delete(sqlSavequery.length()-2, sqlSavequery.length());
        }


        return sqlSavequery.toString();
    }

    public String buildDeleteQuery(String table, LinkedHashMap<String,String> columnValueMap){
        StringBuilder sqlSavequery = new StringBuilder();
        sqlSavequery.append("DELETE FROM `"+ table +"` WHERE " );

        for (Map.Entry<String, String> entry : columnValueMap.entrySet()) {
            sqlSavequery.append(entry.getKey() + "='" + entry.getValue() + "'");
        }

        return sqlSavequery.toString();
    }

    public String buildSaveQuery(String table, LinkedHashMap<String,String> columnValueMap){
        StringBuilder sqlSavequery = new StringBuilder();
        sqlSavequery.append("INSERT INTO `" + table + "` (`");

        for (Map.Entry<String, String> entry : columnValueMap.entrySet()) {
            sqlSavequery.append(entry.getKey() + "`, `");
        }

        sqlSavequery = sqlSavequery.delete(sqlSavequery.length() - 3, sqlSavequery.length());

        sqlSavequery.append(") VALUES (");

        for (Map.Entry<String, String> entry : columnValueMap.entrySet()) {
            sqlSavequery.append("?,");
        }

        sqlSavequery = sqlSavequery.delete(sqlSavequery.length()-1, sqlSavequery.length());

        sqlSavequery.append(")");

        return sqlSavequery.toString();
    }
}
