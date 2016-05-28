package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import data.DataStore;
import entity.UserInfo;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class IntenseLifeController {

    @Autowired
    private DataStore dataStore;

    public IntenseLifeController(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET, produces = "application/json" )
    public String history() throws IOException, JSONException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(dataStore.getHistoricalData());
        return mapper.writeValueAsString(dataStore.getHistoricalData());
    }

    @RequestMapping(value = "/addUserEvent", method = RequestMethod.POST, produces = "application/json" )
    public void addUserEvent(@ModelAttribute("userInfo") UserInfo userInfo) throws IOException, JSONException {
        dataStore.storeUserData(userInfo);
    }
}
