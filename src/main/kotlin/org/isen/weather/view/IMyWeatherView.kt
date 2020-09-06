package org.isen.weather.view;

import org.isen.weather.model.IMyWeatherModelObservable

public interface IMyWeatherView : IMyWeatherModelObservable{
    abstract fun display()
    abstract fun close()

}
