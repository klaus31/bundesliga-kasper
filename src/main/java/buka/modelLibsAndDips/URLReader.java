package buka.modelLibsAndDips;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class URLReader {

  private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

  private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

  public static JSONObject getJSONObjectFromUrl(String url) {
    return getJSONObjectFromUrl(url, DEFAULT_CHARSET);
  }

  public static JSONArray getJSONArrayFromUrl(String url) {
    return getJSONArrayFromUrl(url, DEFAULT_CHARSET);
  }

  public static JSONObject getJSONObjectFromUrl(String url, Charset charset) {
    try {
      JSONObject json = new JSONObject(getStringFromUrl(url, charset));
      return json;
    } catch (JSONException e) {
      System.err.println(url + " is kacke");
      System.exit(1504061226);
      return null;
    }
  }

  public static String getStringFromUrl(String url, Charset charset) {
    try {
      InputStream is = new URL(url).openStream();
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, charset));
      String jsonText = readAll(rd);
      return jsonText;
    } catch (IOException e) {
      System.err.println(url + " is kacke");
      System.exit(1504061224);
      return null;
    }
  }

  public static JSONArray getJSONArrayFromUrl(String url, Charset charset) {
    try {
      JSONArray json = new JSONArray(getStringFromUrl(url, charset));
      return json;
    } catch (JSONException e) {
      System.err.println(url + " is kacke");
      System.exit(1504061225);
      return null;
    }
  }
}