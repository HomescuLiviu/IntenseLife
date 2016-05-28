package data;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by liviu on 5/26/2016.
 */
public class WeatherDataReceiverImpl implements WeatherDataReceiver {

    private static final String weatherDataLink = "https://api.forecast.io/forecast/e5b06b0fb38a204d65b8905b34f68fc1/";

    protected String getWeatherData(String latitude, String longitude) throws IOException, JSONException {

        URL url = new URL(weatherDataLink + latitude + "," + longitude);
        HttpURLConnection  conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();

        StringBuffer response = parseResponse(conn, responseCode);

        return response.toString();
    }

    private String removeUnneededFields(String responseString) throws JSONException {
        if (responseString == null) {
            return null;
        }
        JSONObject object = new JSONObject(responseString);
        JSONObject returnObject = new JSONObject();

        if (object.names() == null) {
            return returnObject.toString();
        }
        for ( int i = 0; i < object.names().length(); i++){
            String name = object.names().getString(i);
            returnObject.put(name, "{}");
        }
        returnObject.put("currently", object.get("currently"));
        return returnObject.toString();
    }

    private StringBuffer parseResponse(HttpURLConnection conn, int responseCode) throws IOException {
        StringBuffer response = new StringBuffer();
        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String inputLine;


            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("POST request not worked");
        }

        return response;
    }

    @Override
    public String getWeatherData(Double latitude, Double longitude) throws IOException, JSONException {
        return removeUnneededFields(getWeatherData(latitude.toString(), longitude.toString()));
    }
}
