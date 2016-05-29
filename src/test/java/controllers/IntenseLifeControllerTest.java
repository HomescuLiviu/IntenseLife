package controllers;

import data.DataStore;
import data.DataStoreImpl;
import data.WeatherDataReceiverImpl;
import entity.UserInfo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.StringUtils;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IntenseLifeControllerTest {

    private static final WeatherDataReceiverImpl weatherDataReceiverImplMock = mock(WeatherDataReceiverImpl.class);

    private static DataStore dataStore =  new DataStoreImpl(weatherDataReceiverImplMock);

    private static IntenseLifeController intenseLifeController = new IntenseLifeController(dataStore);

    @Before
    public void setUp(){
        dataStore =  new DataStoreImpl(weatherDataReceiverImplMock);
        intenseLifeController = new IntenseLifeController(dataStore);
    }
    @Test
    public void testSearchingForHistoryReturnsNullWhenThereIsNoData() throws Exception {
        when(weatherDataReceiverImplMock.getWeatherData(anyDouble(), anyDouble())).thenReturn("{}");
        assertEquals("Did not return empty list for empty data", "[]", intenseLifeController.history());
    }

    @Test
    public void testSearchingForHistoryReturnsCorrectData() throws Exception {

        when(weatherDataReceiverImplMock.getWeatherData(44.43, 26.1)).thenReturn("{\"windBearing\":38,\"apparentTemperature\":60.16}");
        when(weatherDataReceiverImplMock.getWeatherData(54.43, 36.1)).thenReturn("{\"windBearing\":43,\"apparentTemperature\":63.16}");

        UserInfo userInfo  = new UserInfo();
        userInfo .setUserId(1);
        userInfo .setLatitude(44.43);
        userInfo .setLongitude(26.1);
        userInfo .setSteps(32);

        UserInfo secondUserInfo  = new UserInfo();
        secondUserInfo .setUserId(32);
        secondUserInfo .setLatitude(54.43);
        secondUserInfo .setLongitude(36.1);
        secondUserInfo .setSteps(40);

        intenseLifeController.addUserEvent(userInfo);
        intenseLifeController.addUserEvent(secondUserInfo);

        String historicalData = intenseLifeController.history();
        String expectedHistoricalData = "[{\"userId\":1,\"latitude\":44.43,\"longitude\":26.1,\"time\":\"2016-05-28T22:08:23.588\",\"steps\":32,\"apparentTemperature\":60.16,\"windBearing\":38.0},{\"userId\":32,\"latitude\":54.43,\"longitude\":36.1,\"time\":\"2016-05-28T22:08:23.594\",\"steps\":40,\"apparentTemperature\":63.16,\"windBearing\":43.0}]";
        assertEquals("Result was too long ", expectedHistoricalData.length(), historicalData.length());
        assertEquals("Result does not contain time entry", 2, StringUtils.countOccurrencesOf(historicalData, "time"));

        assertEquals("Can not store first user id", 1, StringUtils.countOccurrencesOf(historicalData, "\"userId\":1"));
        assertEquals("Can not store second user id", 1, StringUtils.countOccurrencesOf(historicalData, "\"userId\":32"));
        assertEquals("Can not store first latitude", 1, StringUtils.countOccurrencesOf(historicalData, "\"latitude\":44.43"));
        assertEquals("Can not store second latitude", 1, StringUtils.countOccurrencesOf(historicalData, "\"latitude\":54.43"));
        assertEquals("Can not store first longitude", 1, StringUtils.countOccurrencesOf(historicalData, "\"longitude\":26.1"));
        assertEquals("Can not store second longitude", 1, StringUtils.countOccurrencesOf(historicalData, "\"longitude\":36.1"));
        assertEquals("Can not store first apparentTemperature", 1, StringUtils.countOccurrencesOf(historicalData, "\"apparentTemperature\":60.16"));
        assertEquals("Can not store second apparentTemperature", 1, StringUtils.countOccurrencesOf(historicalData, "\"apparentTemperature\":63.16"));
        assertEquals("Can not store first windBearing", 1, StringUtils.countOccurrencesOf(historicalData, "\"windBearing\":38"));
        assertEquals("Can not store second windBearing", 1, StringUtils.countOccurrencesOf(historicalData, "\"windBearing\":43"));
        assertEquals("Can not store first steps", 1, StringUtils.countOccurrencesOf(historicalData, "\"steps\":32"));
        assertEquals("Can not store second steps", 1, StringUtils.countOccurrencesOf(historicalData, "\"steps\":40"));

  }

}