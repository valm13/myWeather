package org.isen.weather.model

import org.apache.logging.log4j.kotlin.Logging
import org.isen.weather.model.data.LocationData
import org.isen.weather.model.data.LocationSearchData
import org.isen.weather.model.impl.DefaultMyWeatherModel
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestDefaultMyWeatherModel {
    companion object : Logging{
        val WOEID_LONDON:Int = 44418
        val LAT_LONDON = 51.506321f
        val LON_LONDON = -0.12714f
    }

    @Test
    fun testObserverForLocation() {
        var passObserver: Boolean = false
        val model:DefaultMyWeatherModel = DefaultMyWeatherModel()

        val myObserver = object : IMyWeatherModelObservable{
            override fun updateWeather(data:Any){
                passObserver = true
                logger.info("updateWeather with : $data")
                assertEquals(LocationSearchData::class.java, data::class.java)
            }
        }

        model.register(myObserver)

        model.location = LocationSearchData()
        assertTrue(passObserver)
    }

    @Test
    fun testObserverForWeatherData() {
        var passObserver: Boolean = false
        val model:DefaultMyWeatherModel = DefaultMyWeatherModel()

        val myObserver = object : IMyWeatherModelObservable{
            override fun updateWeather(data:Any){
                passObserver = true
                logger.info("updateWeather with : $data")
                assertEquals(LocationSearchData::class.java, data::class.java)
            }
        }

        model.register(myObserver)

        model.weatherData = LocationData()
        assertTrue(passObserver)
    }

    @Test
    fun testFindLocationFor(){
        var passObserver: Boolean = false
        val model:DefaultMyWeatherModel = DefaultMyWeatherModel()

        val myObserver = object : IMyWeatherModelObservable{
            override fun updateWeather(data:Any){
                passObserver = true
                logger.info("updateWeather with : $data")
                assertEquals(LocationSearchData::class.java, data::class.java)
            }
        }

        model.register(myObserver)

        model.findLocationFor("london")
        logger.info("wait data ...")
        Thread.sleep(10000)

        assertTrue(passObserver)
        assertEquals(WOEID_LONDON,model.location.woeid)
        assertEquals(LAT_LONDON,model.location.lat)
        assertEquals(LON_LONDON,model.location.lon)
    }

    @Test
    fun testFindWeatherFor(){
        var passObserver: Boolean = false
        val model:DefaultMyWeatherModel = DefaultMyWeatherModel()

        val myObserver = object : IMyWeatherModelObservable{
            override fun updateWeather(data:Any){
                passObserver = true
                logger.info("updateWeather with : $data")
                assertEquals(LocationSearchData::class.java, data::class.java)
            }
        }

        model.register(myObserver)

        model.findWeatherFor(WOEID_LONDON)
        logger.info("wait data ...")
        Thread.sleep(10000)

        assertTrue(passObserver)
        assertEquals(WOEID_LONDON,model.weatherData.woeid)
        assertEquals(LAT_LONDON,model.weatherData.lat)
        assertEquals(LON_LONDON,model.weatherData.lon)
    }
}