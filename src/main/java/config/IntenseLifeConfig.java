package config;

import controllers.IntenseLifeController;
import data.DataStoreImpl;
import data.WeatherDataReceiverImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IntenseLifeConfig {

    @Bean
    public WeatherDataReceiverImpl weatherDataReceiver() {
        return new WeatherDataReceiverImpl();
    }

    @Bean
    public DataStoreImpl dataStore() {
        return new DataStoreImpl(weatherDataReceiver());
    }

    @Bean
    public IntenseLifeController intenseLifController() {
        return new IntenseLifeController(dataStore());
    }
}
