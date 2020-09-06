package org.isen.weather.model.data

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import java.util.*

data class LocationData (
        val title: String,
        val location_type : String,
        val woeid: Int,
        val latt_long :String,
        val time : Date,
        val sun_rise : Date,
        val sun_set : Date,
        val timezone_name : String,
        val timezone : String,
        val consolidated_weather : List<ConsolidatedWeatherData>,
        val parent : LocationSearchData,
        val sources: List<SourcesData>
){
    constructor():this("","",0,"",Date(),Date(),Date(),"","", emptyList(),LocationSearchData(), emptyList())
    class Deserializer : ResponseDeserializable<LocationData> {
        override fun deserialize(content : String): LocationData
                = Gson().fromJson(content, LocationData::class.java)
    }

    val lon:Float
        get(){
            val _data = latt_long.split(",".toRegex())
            return _data.last().toFloat()
        }

    val lat:Float
        get(){
            val _data = latt_long.split(",".toRegex())
            return _data.first().toFloat()
        }
}