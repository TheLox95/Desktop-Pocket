package app.entities.Article.Http;

import app.entities.Article.API.ApiService;
import app.entities.Article.Article.Parser;
import app.entities.Article.Interfaces.Http.HttpMethod;
import app.entities.Article.Interfaces.Http.IApiEndPoint;
import app.entities.Article.Interfaces.Http.IHttpModule;
import app.entities.Article.Interfaces.Parser.IParser;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.when;

/**
 * Created by Leonardo on 02/12/2017.
 */
public class ApiServiceTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test()
    public void fetchArticles() throws Exception {
        expectedEx.expect(ServerResponseException.class);
        expectedEx.expectMessage("Server response with: Unexpected error different from 200 status code");

        IHttpModule module = Mockito.mock(IHttpModule.class);

        when(module.makeRequest(Matchers.anyString(), Matchers.<HttpMethod>any(), (HashMap)notNull(), (HashMap)notNull()))
                .thenThrow(new Exception(""));

        IApiEndPoint cli = new ApiService(module);

        cli.fetchArticles(0);
    }

    @Test()
    public void returnOneArticle() throws Exception {
        IHttpModule module = Mockito.mock(IHttpModule.class);
        IApiEndPoint cli = new ApiService(module);
        when(module.makeRequest(Matchers.anyString(), Matchers.<HttpMethod>any(), (HashMap)notNull(), (HashMap)notNull()))
                .thenReturn(new StringBuffer().append("{\n")
                        .append("    \"status\": 1,")
                        .append("    \"complete\": 1,")
                        .append("    \"list\": {")
                        .append("        \"1906532286\": {")
                        .append("            \"item_id\": \"1906532286\",")
                        .append("            \"resolved_id\": \"1906532286\",")
                        .append("            \"given_url\": \"https://okdiario.com/curiosidades/2017/09/28/caracteristicas-destacadas-edad-contemporanea-1360156\",")
                        .append("            \"given_title\": \"Edad Contemporánea: Etapas, características y hechos importantes\",")
                        .append("            \"favorite\": \"0\",")
                        .append("            \"status\": \"0\",")
                        .append("            \"time_added\": \"1512073578\",")
                        .append("            \"time_updated\": \"1512073596\",")
                        .append("            \"time_read\": \"0\",")
                        .append("            \"time_favorited\": \"0\",")
                        .append("            \"sort_id\": 0,")
                        .append("            \"resolved_title\": \"Las características más destacadas de la Edad Contemporánea\",")
                        .append("            \"resolved_url\": \"https://okdiario.com/curiosidades/2017/09/28/caracteristicas-destacadas-edad-contemporanea-1360156\",")
                        .append("            \"excerpt\": \"La Edad Contemporánea puede definirse como el periodo histórico más reciente. Una etapa posterior a la Edad Moderna que comprende desde finales del siglo XVIII con el inicio de la Revolución Francesa y continúa hasta la actualidad.\",")
                        .append("            \"is_article\": \"1\",")
                        .append("            \"is_index\": \"0\",")
                        .append("            \"has_video\": \"0\",")
                        .append("            \"has_image\": \"1\",")
                        .append("            \"word_count\": \"498\",")
                        .append("            \"amp_url\": \"https://okdiario.com/curiosidades/2017/09/28/caracteristicas-destacadas-edad-contemporanea-1360156/amp\"")
                        .append("        },")
                        .append("        \"1975275836\": {")
                        .append("            \"item_id\": \"1975275836\",")
                        .append("            \"resolved_id\": \"1975275836\",")
                        .append("            \"given_url\": \"https://www.codeproject.com/Articles/1217654/Building-SPA-with-Angular-and-Redux\",")
                        .append("            \"given_title\": \"https://www.codeproject.com/Articles/1217654/Building-SPA-with-Angular-and-\",")
                        .append("            \"favorite\": \"0\",")
                        .append("            \"status\": \"0\",")
                        .append("            \"time_added\": \"1511996540\",")
                        .append("            \"time_updated\": \"1511996544\",")
                        .append("            \"time_read\": \"0\",")
                        .append("            \"time_favorited\": \"0\",")
                        .append("            \"sort_id\": 1,")
                        .append("            \"resolved_title\": \"Building SPA with Angular 4 and Redux\",")
                        .append("            \"resolved_url\": \"https://www.codeproject.com/Articles/1217654/Building-SPA-with-Angular-and-Redux\",")
                        .append("            \"excerpt\": \"In the current application development era, Single Page Application(SPA) is a great feature to develop the modern web based application.\",")
                        .append("            \"is_article\": \"1\",")
                        .append("            \"is_index\": \"0\",")
                        .append("            \"has_video\": \"0\",")
                        .append("            \"has_image\": \"1\",")
                        .append("            \"word_count\": \"5693\"")
                        .append("        },")
                        .append("        \"1976834834\": {")
                        .append("            \"item_id\": \"1976834834\",")
                        .append("            \"resolved_id\": \"1974049818\",")
                        .append("            \"given_url\": \"https://out.reddit.com/t3_7fxz1v?url=https%3A%2F%2Fwww.hillelwayne.com%2Fpost%2Fcontracts%2F&token=AQAAwDQfWqbfRetjhiBZfe7McWmjrZL9-4i59zdiaqR2BnRj3c53&app_name=mweb2x\",")
                        .append("            \"given_title\": \"https://out.reddit.com/t3_7fxz1v?url=https://www.hillelwayne.com/post/contr\",")
                        .append("            \"favorite\": \"0\",")
                        .append("            \"status\": \"0\",")
                        .append("            \"time_added\": \"1511992076\",")
                        .append("            \"time_updated\": \"1511992096\",")
                        .append("            \"time_read\": \"0\",")
                        .append("            \"time_favorited\": \"0\",")
                        .append("            \"sort_id\": 2,")
                        .append("            \"resolved_title\": \"Introduction to Contract Programming\",")
                        .append("            \"resolved_url\": \"https://www.hillelwayne.com/post/contracts/\",")
                        .append("            \"excerpt\": \"I’ve namedropped contracts enough here that I think it’s finally time to go talk about them. A lot of people conflate them with class interfaces / dynamic typing / “your unit tests are your contract”, which muddies the discussion and makes it hard to show their benefits.\",")
                        .append("            \"is_article\": \"1\",")
                        .append("            \"is_index\": \"1\",")
                        .append("            \"has_video\": \"0\",")
                        .append("            \"has_image\": \"0\",")
                        .append("            \"word_count\": \"1992\"")
                        .append("        }")
                        .append("    },")
                        .append("    \"error\": null,")
                        .append("    \"search_meta\": {")
                        .append("        \"search_type\": \"normal\"")
                        .append("    },")
                        .append("    \"since\": 1512246143")
                        .append("}"));
        StringBuffer buffer = cli.fetchArticles(0);
        assertEquals("{\n" +
                "    \"status\": 1,    \"complete\": 1,    \"list\": {        \"1906532286\": {            \"item_id\": \"1906532286\",            \"resolved_id\": \"1906532286\",            \"given_url\": \"https://okdiario.com/curiosidades/2017/09/28/caracteristicas-destacadas-edad-contemporanea-1360156\",            \"given_title\": \"Edad Contemporánea: Etapas, características y hechos importantes\",            \"favorite\": \"0\",            \"status\": \"0\",            \"time_added\": \"1512073578\",            \"time_updated\": \"1512073596\",            \"time_read\": \"0\",            \"time_favorited\": \"0\",            \"sort_id\": 0,            \"resolved_title\": \"Las características más destacadas de la Edad Contemporánea\",            \"resolved_url\": \"https://okdiario.com/curiosidades/2017/09/28/caracteristicas-destacadas-edad-contemporanea-1360156\",            \"excerpt\": \"La Edad Contemporánea puede definirse como el periodo histórico más reciente. Una etapa posterior a la Edad Moderna que comprende desde finales del siglo XVIII con el inicio de la Revolución Francesa y continúa hasta la actualidad.\",            \"is_article\": \"1\",            \"is_index\": \"0\",            \"has_video\": \"0\",            \"has_image\": \"1\",            \"word_count\": \"498\",            \"amp_url\": \"https://okdiario.com/curiosidades/2017/09/28/caracteristicas-destacadas-edad-contemporanea-1360156/amp\"        },        \"1975275836\": {            \"item_id\": \"1975275836\",            \"resolved_id\": \"1975275836\",            \"given_url\": \"https://www.codeproject.com/Articles/1217654/Building-SPA-with-Angular-and-Redux\",            \"given_title\": \"https://www.codeproject.com/Articles/1217654/Building-SPA-with-Angular-and-\",            \"favorite\": \"0\",            \"status\": \"0\",            \"time_added\": \"1511996540\",            \"time_updated\": \"1511996544\",            \"time_read\": \"0\",            \"time_favorited\": \"0\",            \"sort_id\": 1,            \"resolved_title\": \"Building SPA with Angular 4 and Redux\",            \"resolved_url\": \"https://www.codeproject.com/Articles/1217654/Building-SPA-with-Angular-and-Redux\",            \"excerpt\": \"In the current application development era, Single Page Application(SPA) is a great feature to develop the modern web based application.\",            \"is_article\": \"1\",            \"is_index\": \"0\",            \"has_video\": \"0\",            \"has_image\": \"1\",            \"word_count\": \"5693\"        },        \"1976834834\": {            \"item_id\": \"1976834834\",            \"resolved_id\": \"1974049818\",            \"given_url\": \"https://out.reddit.com/t3_7fxz1v?url=https%3A%2F%2Fwww.hillelwayne.com%2Fpost%2Fcontracts%2F&token=AQAAwDQfWqbfRetjhiBZfe7McWmjrZL9-4i59zdiaqR2BnRj3c53&app_name=mweb2x\",            \"given_title\": \"https://out.reddit.com/t3_7fxz1v?url=https://www.hillelwayne.com/post/contr\",            \"favorite\": \"0\",            \"status\": \"0\",            \"time_added\": \"1511992076\",            \"time_updated\": \"1511992096\",            \"time_read\": \"0\",            \"time_favorited\": \"0\",            \"sort_id\": 2,            \"resolved_title\": \"Introduction to Contract Programming\",            \"resolved_url\": \"https://www.hillelwayne.com/post/contracts/\",            \"excerpt\": \"I’ve namedropped contracts enough here that I think it’s finally time to go talk about them. A lot of people conflate them with class interfaces / dynamic typing / “your unit tests are your contract”, which muddies the discussion and makes it hard to show their benefits.\",            \"is_article\": \"1\",            \"is_index\": \"1\",            \"has_video\": \"0\",            \"has_image\": \"0\",            \"word_count\": \"1992\"        }    },    \"error\": null,    \"search_meta\": {        \"search_type\": \"normal\"    },    \"since\": 1512246143}", buffer.toString());
    }

}