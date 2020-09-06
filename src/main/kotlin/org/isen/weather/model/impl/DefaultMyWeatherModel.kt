package org.isen.weather.model.impl


import com.github.kittinunf.fuel.httpGet
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.apache.logging.log4j.kotlin.Logging
import org.isen.weather.model.IMyWeatherModel
import org.isen.weather.model.IMyWeatherModelObservable
import org.isen.weather.model.data.LocationData
import org.isen.weather.model.data.LocationSearchData
import kotlin.properties.Delegates

class DefaultMyWeatherModel : IMyWeatherModel{
    companion object : Logging

    private var listeners: ArrayList<IMyWeatherModelObservable>

    init {
        listeners = ArrayList()
    }

    public var location : LocationSearchData by Delegates.observable(LocationSearchData()){
        property, oldValue, newValue ->
            logger.info("Location property change, notify observer")
            for(listener in listeners){
                listener.updateWeather(newValue)
            }
    }

    public var weatherData : LocationData by Delegates.observable(LocationData()){
        property, oldValue, newValue ->
            logger.info("WeatherData property change, notify observer")
            for(listener in listeners){
                listener.updateWeather(newValue)
            }
    }

    override fun register(listener: IMyWeatherModelObservable) {
        listeners.add(listener)
    }

    override fun unregister(listener: IMyWeatherModelObservable) {
        listeners.remove(listener)
    }

    private suspend fun getLocationFor(city:String){
        "https://www.metaweather.com/api/location/search/?query=$city".httpGet().responseObject(LocationSearchData.Deserializer()){
                    request, response, result ->
                    logger.info("StatusCode : ${response.statusCode}")
                    result.component1()?.let {
                        location = it.first()
                    }
                }
    }

    public override fun findLocationFor(city:String){
        logger.info("get location for $city")
        GlobalScope.launch { getLocationFor(city) }
    }

    private suspend fun getWeatherFor(woeid:Int){
        "https://www.metaweather.com/api/location/$woeid".httpGet().responseObject(LocationData.Deserializer()) {
                    request, response, result ->
                    logger.info("StatusCode : ${response.statusCode}")
                    result.component1()?.let {
                        weatherData = it
                    }
                }
    }

    public override fun findWeatherFor(woeid:Int){
        logger.info("get weather for $woeid")
        GlobalScope.launch { getWeatherFor(woeid) }
    }

}