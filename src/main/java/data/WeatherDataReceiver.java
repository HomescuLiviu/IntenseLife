package data;

/**
 * Created by liviu on 5/26/2016.
 */
public interface WeatherDataReceiver {

    String getWeatherData(String latitide, String longitude);

    String getWeatherData();
}
