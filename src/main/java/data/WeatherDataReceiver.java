package data;

import java.io.IOException;

/**
 * Created by liviu on 5/26/2016.
 */
public interface WeatherDataReceiver {

    String getWeatherData(Double latitude, Double longitude) throws IOException;

}
