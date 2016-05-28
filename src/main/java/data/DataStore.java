package data;


import entity.UserInfo;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public interface DataStore {

    void storeUserData(UserInfo user) throws IOException, JSONException;

    List<UserInfo> getHistoricalData() throws JSONException, IOException;
}
