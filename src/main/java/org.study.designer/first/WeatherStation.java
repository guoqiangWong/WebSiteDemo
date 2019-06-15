package org.study.designer.first;

public class WeatherStation {
    public static  void main(String[] args){
        WeatherData weatherData = new WeatherData();

        CurrentConditionDisplay display = new CurrentConditionDisplay(weatherData);

        weatherData.setMeasurements(81,65,30.4f);
        weatherData.setMeasurements(82,70,29.2f);
        weatherData.setMeasurements(78,90,29.2f);
    }
}
