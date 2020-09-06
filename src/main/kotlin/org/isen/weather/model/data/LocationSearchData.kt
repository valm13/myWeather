package org.isen.weather.model.data

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class LocationSearchData (
    val title : String,
    val location_type: String,
    val woeid : Int,
    val latt_long : String
){

    constructor():this("","",0,"")
    class Deserializer : ResponseDeserializable<Array<LocationSearchData>>{
        override fun deserialize(content : String): Array<LocationSearchData>
            = Gson().fromJson(content, Array<LocationSearchData>::class.java)
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