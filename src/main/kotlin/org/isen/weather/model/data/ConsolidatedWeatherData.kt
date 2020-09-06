package org.isen.weather.model.data

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import java.util.*

data class ConsolidatedWeatherData (
        val id : Long,
        val weather_state_name : String,
        val weather_state_abbr : String,

        val applicable_date : Date,
        val min_temp : Float,
        val max_temp : Float,
        val the_temp : Float,
        val wind_speed : Float,
        val wind_direction : Float,
        val wind_direction_compass : String,
        val air_pressure : Float,
        val humidity : Float,
        val visibility : Float,
        val predictability : Int
){
    constructor():this(1,"","",Date(),0f,0f,0f,0f,0f,"",0f,0f,0f,0)
}

data class SourcesData(
        val title: String,
        val url : String
){
    constructor():this("","")
}
