package org.isen.weather.ctrl

import org.isen.weather.model.IMyWeatherModel
import org.isen.weather.view.IMyWeatherView

class MyWeatherDefaultCtrl(var model:IMyWeatherModel) {
    private var views: ArrayList<IMyWeatherView>

    init {
        this.views = ArrayList()
    }

    fun registerView(v: IMyWeatherView){
        this.views.add(v)
        this.model.register(v)
    }

    fun displayView(){
        views.forEach{
            it.display()
        }
    }

    fun closeView(){
        views.forEach {
            it.close()
        }
    }

    fun findCities(pattern:String){
        this.model.findLocationFor(pattern)
    }

    fun findWeather(woeid:Int){
        this.model.findWeatherFor(woeid)
    }
}