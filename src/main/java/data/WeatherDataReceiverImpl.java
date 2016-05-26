package data;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by liviu on 5/26/2016.
 */
public class WeatherDataReceiverImpl implements WeatherDataReceiver {

    private static final String weatherDataLink = "https://api.forecast.io/forecast/e5b06b0fb38a204d65b8905b34f68fc1/";

    protected String getWeatherData(String latitude, String longitude) throws IOException {

        URL url = new URL(weatherDataLink + latitude + "," + longitude);
        URLConnection conn = url.openConnection();
        InputStream is = conn.getInputStream();

        System.out.println(is.toString());

        return null;
    }

    @Override
    public String getWeatherData(Double latitude, Double longitude) throws IOException {
        return getWeatherData(latitude.toString(), longitude.toString());
    }
}
