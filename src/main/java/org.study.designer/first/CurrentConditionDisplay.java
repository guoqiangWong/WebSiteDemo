package org.study.designer.first;

import org.study.designer.DisplayElement;

import java.util.Observable;
import java.util.Observer;

/**
 * @author ExpanseWong
 */
@SuppressWarnings("unused")
public class CurrentConditionDisplay implements Observer, DisplayElement {

    private Observable observable;
    private float temperature;
    private float humidity;

    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof WeatherData) {
            WeatherData weatherData = (WeatherData) o;
            this.temperature = weatherData.getTemperature();
            this.humidity = weatherData.getHumidity();
            display();
        }
    }

    public CurrentConditionDisplay(Observable observable) {
        this.observable = observable;
        observable.addObserver(this);
    }

    @Override
    public void display() {
        System.out.println("Second::Current conditions: " + temperature + "F degrees and " + humidity + "% humidity");

    }
}
