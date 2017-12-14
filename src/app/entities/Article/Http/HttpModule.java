package app.entities.Article.Http;

import app.entities.Article.Interfaces.Http.HttpMethod;
import app.entities.Article.Interfaces.Http.IHttpModule;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import static java.util.stream.Collectors.joining;

/**
 * Created by Leonardo on 02/12/2017.
 */
public class HttpModule implements IHttpModule {
    @Override
    public StringBuffer makeRequest(String url, HttpMethod method, Map<String, String> headers, Map<String, String> body) throws Exception{
        try{
            URL urlObj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

            con.setRequestMethod(method.getValue());
            headers.forEach((key, value) -> {
                con.setRequestProperty(key,value);
            });

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            StringBuilder str = new StringBuilder();
            body.forEach((key, value) ->{
                str.append(key + "=" + value + "&");
            });
            wr.writeBytes(str.substring(0, str.length() - 1));
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("nSending 'POST' request to URL : " + urlObj.toString());
            System.out.println("Post Data : " + str.toString());
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String output;
            StringBuffer response = new StringBuffer();

            while ((output = in.readLine()) != null) {
                response.append(output);
            }

            in.close();

            return response;
        } catch (Exception e){
            throw e;
        }
    }
}
