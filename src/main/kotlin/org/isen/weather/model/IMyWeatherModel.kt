package org.isen.weather.model;

import com.github.kittinunf.fuel.httpGet
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.isen.weather.model.data.LocationData
import org.isen.weather.model.impl.DefaultMyWeatherModel

public interface IMyWeatherModel {
    abstract fun register(listener:IMyWeatherModelObservable)
    abstract fun unregister(listener:IMyWeatherModelObservable)

    // API
    public fun findLocationFor(city:String)
    public fun findWeatherFor(woeid:Int)
}
