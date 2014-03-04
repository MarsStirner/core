package ru.korus.lis.bak;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Dmitriy E. Nosov <br>
 * @description: <br>
 */
public class BakClient {
    private static final Logger logger = LoggerFactory.getLogger(BakClient.class);

    private String serverUrl;


    public BakClient(String serverUrl) {
        this.serverUrl = serverUrl;
    }


    public void send() {
        try {
            post(1);
        } catch (Exception e) {

        }
    }

    /**
     * Отправить http get запрос
     *
     * @param getParam параметры get запроса
     * @return ответ серверной части
     */
    public String get(final String getParam) {
        final StringBuilder sb = new StringBuilder();
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(serverUrl + getParam);
            HttpResponse response = client.execute(request);

            // Get the response
            BufferedReader rd = new BufferedReader
                    (new InputStreamReader(response.getEntity().getContent()));

            String line = "";
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            System.out.println("e = " + e);
        }
        return sb.toString();
    }

    private void post(int name) {
        // set the connection timeout value to 30 seconds (30000 milliseconds)
        final HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 60000);

        HttpClient client = new DefaultHttpClient(httpParams);
        HttpPost post = new HttpPost(serverUrl);

        try {

            // System.out.println("nameValuePairs = " + nameValuePairs);
         //   post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = rd.readLine()) != null) {
                //  System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private  void sendd(){
       /*
        StringBuilder sb=new StringBuilder();
        sb.Append("<soapenv:Envelope");
        sb.Append("xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"");
        sb.Append("xmlns:sas=\"http://sas.elluminate.com/\">");
        sb.Append("<soapenv:Header>");
        sb.Append("<sas:BasicAuth>");
        sb.Append("<sas:Name>cccc-admin1</sas:Name>");
        sb.Append("<sas:Password>!cccConfer*</sas:Password>");
        sb.Append("</sas:BasicAuth>");
        sb.Append("</soapenv:Header>");
        sb.Append("<soapenv:Body>");
        sb.Append("<tns:getServerConfiguration>");
        sb.Append("<tns:fullResponse>true</tns:fullResponse>");
        sb.Append("</tns:getServerConfiguration>");
        sb.Append("</soapenv:Body>");
        sb.Append("</soapenv:Envelope>");

        //запрос
        WebRequest request = HttpWebRequest.Create("http://sas-int.elluminate.com/site/external/adapter/default/v1/webservice.event");
        //все эти настройки можешь взять со страницы описания веб сервиса
        request.Method = "POST";
        request.ContentType = "text/xml; charset=utf-8";
        request.ContentLength = sb.Length;
        //пишем тело
        StreamWriter streamWriter = new StreamWriter(request.GetRequestStream());
        streamWriter.Write(sb.ToString());
        streamWriter.Close();
        //читаем тело
        WebResponse response = request.GetResponse();
        StreamReader streamReader = new StreamReader(response.GetResponseStream());
        string result = streamReader.ReadToEnd();
        //result - ответ
        tbResponseBody.Text=result;
        */
    }

}
