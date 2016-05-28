package data;

import entity.UserInfo;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DataStoreImplTest {

    private static final WeatherDataReceiverImpl weatherDataReceiverImpl = mock(WeatherDataReceiverImpl.class);

    private DataStoreImpl dataStore = new DataStoreImpl(weatherDataReceiverImpl);

    @Test()
    public void testCanAddMultipleDataEntriesForSameUserId() throws Exception{

        when(weatherDataReceiverImpl.getWeatherData(anyDouble(),anyDouble())).thenReturn("{\"windBearing\":38,\"apparentTemperature\":60.16}");

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(1);
        userInfo.setLatitude(44.43);
        userInfo.setLongitude(26.1);

        dataStore.storeUserData(userInfo);

        UserInfo secondUser = new UserInfo();
        secondUser.setUserId(1);
        secondUser.setLatitude(54.43);
        secondUser.setLongitude(36.1);

        dataStore.storeUserData(secondUser);

    }

    @Test(expected=IllegalArgumentException.class)
    public void testThrowsExceptionWhenAddingAUserWithoutAnId() throws Exception{

        UserInfo userInfo  = new UserInfo();
        userInfo .setLatitude(44.43);
        userInfo .setLongitude(26.1);

        dataStore.storeUserData(userInfo);
    }

    @Test
    public void testCanStoreSingleUserDataUsingLatitudeAndLongitude() throws Exception {

        when(weatherDataReceiverImpl.getWeatherData(anyDouble(),anyDouble())).thenReturn("{\"windBearing\":38,\"apparentTemperature\":60.16}");

        UserInfo userInfo  = new UserInfo();
        userInfo .setUserId(1);
        userInfo .setLatitude(44.43);
        userInfo .setLongitude(26.1);
        userInfo .setSteps(32);

        dataStore.storeUserData(userInfo );
        List<UserInfo> historicalData = dataStore.getHistoricalData();

        assertNotNull("Can not store data using latitude and longitude", historicalData);
        assertEquals("Incorrect number of stored entries", 1, historicalData.size());
        assertNotNull("Can not store time", historicalData.get(0).getTime());
        assertEquals("Can not store temperature using latitude and longitude",60.16, historicalData.get(0).getApparentTemperature());
        assertEquals("Can not store wind bearing using latitude and longitude",38.0, historicalData.get(0).getWindBearing());
        assertEquals("Can not store longitude",26.1, historicalData.get(0).getLongitude());
        assertEquals("Can not store latitude",44.43, historicalData.get(0).getLatitude());
        assertEquals("Can not store id",new Integer(1), historicalData.get(0).getUserId());
        assertEquals("Can not store steps",new Integer(32), historicalData.get(0).getSteps());

    }

    @Test
    public void testCanStoreMultipleUserDataUsingLatitudeAndLongitude() throws Exception {

        when(weatherDataReceiverImpl.getWeatherData(44.43, 26.1)).thenReturn("{\"windBearing\":38,\"apparentTemperature\":60.16}");
        when(weatherDataReceiverImpl.getWeatherData(54.43, 36.1)).thenReturn("{\"windBearing\":43,\"apparentTemperature\":63.16}");

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

        dataStore.storeUserData(userInfo );
        dataStore.storeUserData(secondUserInfo);
        List<UserInfo> historicalData = dataStore.getHistoricalData();

        assertNotNull("Can not store data using latitude and longitude", historicalData);
        assertEquals("Incorrect number of stored entries", 2, historicalData.size());
        assertNotNull("Can not store time", historicalData.get(0).getTime());
        assertEquals("Can not store temperature using latitude and longitude",60.16, historicalData.get(0).getApparentTemperature());
        assertEquals("Can not store wind bearing using latitude and longitude",38.0, historicalData.get(0).getWindBearing());
        assertEquals("Can not store longitude",26.1, historicalData.get(0).getLongitude());
        assertEquals("Can not store latitude",44.43, historicalData.get(0).getLatitude());
        assertEquals("Can not store id",new Integer(1), historicalData.get(0).getUserId());
        assertEquals("Can not store steps",new Integer(32), historicalData.get(0).getSteps());


        assertNotNull("Can not store time", historicalData.get(1).getTime());
        assertEquals("Can not store temperature using latitude and longitude",63.16, historicalData.get(1).getApparentTemperature());
        assertEquals("Can not store wind bearing using latitude and longitude",43.0, historicalData.get(1).getWindBearing());
        assertEquals("Can not store longitude",36.1, historicalData.get(1).getLongitude());
        assertEquals("Can not store latitude",54.43, historicalData.get(1).getLatitude());
        assertEquals("Can not store id",new Integer(32), historicalData.get(1).getUserId());
        assertEquals("Can not store steps",new Integer(40), historicalData.get(1).getSteps());

    }

    @Test
    public void testCanStoreDataUsingLatitudeAndLongitudeWhenWeatherdataDoesNotExist() throws Exception {

        when(weatherDataReceiverImpl.getWeatherData(anyDouble(),anyDouble())).thenReturn("{}");

        UserInfo userInfo  = new UserInfo();
        userInfo .setUserId(1);
        userInfo .setLatitude(44.43);
        userInfo .setLongitude(26.1);
        userInfo .setSteps(32);

        dataStore.storeUserData(userInfo );
        List<UserInfo> historicalData = dataStore.getHistoricalData();

        assertNotNull("Can not store data using latitude and longitude", historicalData);
        assertEquals("Incorrect number of stored entries", 1, historicalData.size());
        assertNotNull("Can not store time", historicalData.get(0).getTime());
        assertNull("Stored temperature when there should not be any value", historicalData.get(0).getApparentTemperature());
        assertNull("Stored wind bearing when there should not be any value", historicalData.get(0).getWindBearing());
        assertEquals("Can not store longitude",26.1, historicalData.get(0).getLongitude());
        assertEquals("Can not store latitude",44.43, historicalData.get(0).getLatitude());
        assertEquals("Can not store id",new Integer(1), historicalData.get(0).getUserId());
        assertEquals("Can not store steps",new Integer(32), historicalData.get(0).getSteps());

    }
}