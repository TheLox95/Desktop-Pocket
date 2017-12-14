package app.entities.Article.DB;

import app.entities.Article.Interfaces.DB.IDB;

import java.sql.*;
import java.util.*;

/**
 * Created by Leonardo on 08/12/2017.
 */
public class Database implements IDB{

    private Connection _con;
    private QueryBuilder _builder;

    public Database(QueryBuilder builder){
        _builder = builder;
        try
        {
            Class.forName("org.h2.Driver");
            insertWithPreparedStatement();
        }
        catch( Exception e )
        {
            System.out.println( e.getMessage() );
        }
    }

    private void insertWithPreparedStatement() throws SQLException {
        PreparedStatement CreateTableArticlePreparedStatement = null;
        PreparedStatement CreateTableTagPreparedStatement = null;
        PreparedStatement CreateRelationArticleTagPreparedStatement = null;
        PreparedStatement CreateTableQueuePreparedStatement = null;
        PreparedStatement CreateTableConfigStatement = null;

        String CreateTableArticleQuery = "CREATE TABLE IF NOT EXISTS article (\n" +
                "  id int(11) NOT NULL,\n" +
                "  url varchar(200) NOT NULL,\n" +
                "  title varchar(200) NOT NULL,\n" +
                "  isFavorite tinyint(1) NOT NULL,\n" +
                "  isArchived tinyint(1) NOT NULL,\n" +
                "  preview text NOT NULL,\n" +
                "  isArticle tinyint(1) NOT NULL,\n" +
                "  wordsAmmo int(11) NOT NULL,\n" +
                "  savedDate int(11) NOT NULL,\n" +
                "  PRIMARY KEY (id)\n" +
                ");";
        String CreateTableTageQuery = "CREATE TABLE IF NOT EXISTS tag (\n" +
                "  article int(11) NOT NULL,\n" +
                "  title varchar(30) NOT NULL,\n" +
                "  PRIMARY KEY (article,title)\n" +
                ")";

        String CreateTableArticleQueue = "CREATE TABLE IF NOT EXISTS articlequeue (\n" +
                "  url varchar(200) NOT NULL,\n" +
                "  PRIMARY KEY (url)\n" +
                ")";

        String CreateTableConfig = "CREATE TABLE IF NOT EXISTS config (\n" +
                "  code varchar(50) NOT NULL\n" +
                ")";

        String CreateArticleTagRelationQuery = "ALTER TABLE tag\n" +
                "  ADD CONSTRAINT tag_ibfk_1 FOREIGN KEY (article) REFERENCES article (id) ON DELETE CASCADE ON UPDATE CASCADE";

        try {
            _con = DriverManager.getConnection("jdbc:h2:~/test", "test", "" );
            _con.setAutoCommit(false);

            CreateTableArticlePreparedStatement = _con.prepareStatement(CreateTableArticleQuery);
            CreateTableArticlePreparedStatement.executeUpdate();
            CreateTableArticlePreparedStatement.close();

            CreateTableTagPreparedStatement = _con.prepareStatement(CreateTableTageQuery);
            CreateTableTagPreparedStatement.executeUpdate();
            CreateTableTagPreparedStatement.close();

            CreateTableQueuePreparedStatement = _con.prepareStatement(CreateTableArticleQueue);
            CreateTableQueuePreparedStatement.executeUpdate();
            CreateTableQueuePreparedStatement.close();

            CreateTableConfigStatement = _con.prepareStatement(CreateTableConfig);
            CreateTableConfigStatement.executeUpdate();
            CreateTableConfigStatement.close();

            CreateRelationArticleTagPreparedStatement = _con.prepareStatement(CreateArticleTagRelationQuery);
            CreateRelationArticleTagPreparedStatement.executeUpdate();
            CreateRelationArticleTagPreparedStatement.close();

            _con.commit();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //_con.close();
        }
    }

    @Override
    public String saveRecord(String table, LinkedHashMap<String,String> columnValueMap){
        String query = _builder.buildSaveQuery(table, columnValueMap);
        _doInsertQuery(query, new ArrayList(columnValueMap.values()));
        return "saved";
    }

    public ArrayList<ArrayList<String>> getRecord(String table, LinkedHashMap<String,String> map){
        ArrayList<ArrayList<String>> resultColum = new ArrayList<>();
        String query = _builder.buildSelectQuery(table, map);

        try{
            PreparedStatement selectPreparedStatement = _con.prepareStatement(query);
            ResultSet rs = selectPreparedStatement.executeQuery();
            while (rs.next()) {
                ArrayList<String> resultRow = new ArrayList<String>();
                for (int i = 1; i < 19; i++) {
                    try {
                        resultRow.add(rs.getString(i));
                    }catch (Exception e){
                        //System.out.println(e.getMessage());
                    }
                }
                resultColum.add(resultRow);
            }
            selectPreparedStatement.close();
            return resultColum;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public boolean deleteRecord(String table, LinkedHashMap<String,String> map){
        String query = _builder.buildDeleteQuery(table, map);

        try{
            _con = DriverManager.getConnection("jdbc:h2:~/test", "test", "" );
            PreparedStatement selectPreparedStatement = _con.prepareStatement(query);
            selectPreparedStatement.executeUpdate();
            selectPreparedStatement.close();

            return true;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private void _doUpdateQuery(String sqlQuery){
        try{
            Statement stmt = _con.createStatement();
            stmt.executeUpdate( sqlQuery );

            stmt.close();
            _con.close();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private boolean _doInsertQuery(String sqlQuery, ArrayList<String> valuesToSave){
        try{
            _con = DriverManager.getConnection("jdbc:h2:~/test", "test", "" );
            PreparedStatement stmt = _con.prepareStatement(sqlQuery);

            for (int i = 0; i < valuesToSave.size(); i++) {
                stmt.setString(i+1, valuesToSave.get(i));
            }

            stmt.executeUpdate();
            stmt.close();
            _con.close();
            return true;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
