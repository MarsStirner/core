package ru.korus.tmis.ws.impl

import ru.korus.tmis.ws.webmis.rest.TestRESTClient
import java.net.{MalformedURLException, HttpURLConnection, URL}
import java.io.{IOException, BufferedReader, InputStreamReader}

class TestRESTClientImpl extends TestRESTClient{

  def makeTestRESTData: String = {

    //TODO: Тестовая реализация
    try {
      val url = new URL("http://localhost:8080/tmis-ws-medipad/rest/tms-registry/dir/departments")
      val conn: HttpURLConnection = url.openConnection().asInstanceOf[HttpURLConnection]
      conn.setRequestMethod("GET")
      conn.setRequestProperty("Accept", "application/json")

      if (conn.getResponseCode != 200) {
        throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode)
      }

      val br = new BufferedReader(new InputStreamReader((conn.getInputStream)))

      var output: String = br.readLine()
      var line: String = ""
      /*while ((line = br.readLine()) != null) {
        output = output + line;
      } */
      conn.disconnect()
      output
    } catch {
      case e: MalformedURLException => {throw e}
      case e: IOException => {throw e}
    }
  }
}
