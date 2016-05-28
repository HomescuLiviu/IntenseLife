package data;

import entity.UserInfo;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.time.LocalDateTime.now;

@Component
public class DataStoreImpl implements DataStore{

    private List<UserInfo> store = new CopyOnWriteArrayList<>();

    @Autowired
    private WeatherDataReceiver weatherDataReceiver;

    public DataStoreImpl(WeatherDataReceiver weatherDataReceiver) {
        this.weatherDataReceiver = weatherDataReceiver;
    }

    @Override
    public void storeUserData(UserInfo userInfo) throws IOException, JSONException {
        if (userInfo.getUserId() == null) {
            throw new IllegalArgumentException("UserInfo does not have an id");
        }
        String weatherData = weatherDataReceiver.getWeatherData(userInfo.getLatitude(), userInfo.getLongitude());

        JSONObject weatherDataJSON = new JSONObject(weatherData);
        userInfo.setTime(now().toString());
        if (weatherDataJSON.length() !=0) userInfo.setWindBearing(weatherDataJSON.getDouble("windBearing"));
        if (weatherDataJSON.length() !=0) userInfo.setApparentTemperature(weatherDataJSON.getDouble("apparentTemperature"));

        store.add(userInfo);
    }

    @Override
    public List<UserInfo> getHistoricalData() throws JSONException, IOException {
        return store;
    }
}
