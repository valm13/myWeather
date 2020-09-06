package org.isen.weather.view.impl

import org.apache.logging.log4j.kotlin.Logging
import org.isen.weather.ctrl.MyWeatherDefaultCtrl
import org.isen.weather.model.data.ConsolidatedWeatherData
import org.isen.weather.model.data.LocationData
import org.isen.weather.model.data.LocationSearchData
import org.isen.weather.view.IMyWeatherView
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.net.URL
import javax.imageio.ImageIO
import javax.swing.*
import javax.swing.border.Border

class MySimpleView : IMyWeatherView, ActionListener {
    companion object : Logging

    private val labelCity: JLabel
    private val labelType: JLabel
    private val labelWoeid: JLabel
    private val labelLat: JLabel
    private val labelLong: JLabel

    private var weatherInfo: JPanel

    private val frame: JFrame
    private val fieldCity: JTextField

    private val controller: MyWeatherDefaultCtrl

    constructor(ctrl: MyWeatherDefaultCtrl, title: String) {
        this.weatherInfo = JPanel(BorderLayout())



        this.labelCity = JLabel()
        this.labelLat = JLabel()
        this.labelLong = JLabel()
        this.labelType = JLabel()
        this.labelWoeid = JLabel()

        this.fieldCity = JTextField()
        this.frame = JFrame()
        this.frame.contentPane = makeUI()
        this.frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        this.frame.preferredSize = Dimension(500, 500)
        this.frame.title = title
        this.frame.pack()

        this.controller = ctrl
        this.controller.registerView(this)
    }

    private fun makeUI(): JPanel {
        val contentPane = JPanel()

        contentPane.layout = BorderLayout()
        contentPane.add(createUIforEnterCity(), BorderLayout.NORTH)
        contentPane.add(createUICityInfo(), BorderLayout.WEST)
        contentPane.add(createUIWeatherInfo(), BorderLayout.CENTER)

        return contentPane
    }

    private fun createUIforEnterCity(): JPanel {
        val panelDataEnter = JPanel()
        panelDataEnter.layout = BorderLayout()
        panelDataEnter.add(fieldCity, BorderLayout.CENTER)

        val button = JButton("Get Information")
        button.addActionListener(this)
        panelDataEnter.add(button, BorderLayout.EAST)

        panelDataEnter.add(JLabel("please enter the city"), BorderLayout.WEST)

        return panelDataEnter
    }

    private fun createUICityInfo(): JPanel {
        val result = JPanel(GridLayout(5,2))
        //result.preferredSize = Dimension(110, 100)
        result.add(JLabel("Title : "))
        result.add(this.labelCity)
        result.add(JLabel("Type : "))
        result.add(this.labelType)
        result.add(JLabel("Woeid : "))
        result.add(this.labelWoeid)
        result.add(JLabel("Latitude : "))
        result.add(this.labelLat)
        result.add(JLabel("Longitude : "))
        result.add(this.labelLong)

        return result
    }

    private fun createUIWeatherInfo(): JPanel {
        return weatherInfo
    }

    private fun createItemOfWeatherInfo(data: ConsolidatedWeatherData): JPanel {
        val result = JPanel(FlowLayout())

        val url = URL("http://www.metaweather.com/static/img/weather/png/64/${data.weather_state_abbr}.png")
        val label = JLabel(ImageIcon(ImageIO.read(url)))
        result.add(label)

        val gridinfo = JPanel(GridLayout(5,1))
        gridinfo.add(JLabel("applicable_date : ${data.applicable_date}"))
        gridinfo.add(JLabel("weather_state_name : ${data.weather_state_name}"))
        gridinfo.add(JLabel("min temp : ${data.min_temp}"))
        gridinfo.add(JLabel("max temp : ${data.max_temp}"))
        gridinfo.add(JLabel("wind speed  : ${data.wind_speed}"))
        result.add(gridinfo)
        return result
    }

    private fun updateWeatherInfo(data:LocationData)
    {
        val paneAllItem = JPanel(GridLayout(data.consolidated_weather.size,1))

        for(item:ConsolidatedWeatherData in data.consolidated_weather){
            logger.debug("updateWeatherInfo $item")
            paneAllItem.add(createItemOfWeatherInfo(item))
        }

        val scrollPane = JScrollPane(paneAllItem)
        weatherInfo.add(scrollPane, BorderLayout.CENTER)
        weatherInfo.revalidate()
    }

    override fun actionPerformed(e: ActionEvent?) {
        controller.findCities(fieldCity.text)
    }

    override fun display() {
        this.frame.isVisible = true
    }

    override fun close() {
        this.frame.isVisible = false
        this.frame.dispose()
    }

    override fun updateWeather(data: Any) {
        if (data is LocationData) {
            logger.info("receive location data")
            updateWeatherInfo(data)
        } else if (data is LocationSearchData) {
            logger.info("receive location search data")
            this.labelCity.text = data.title
            this.labelType.text = data.location_type
            this.labelWoeid.text = data.woeid.toString()
            this.labelLat.text = data.lat.toString()
            this.labelLong.text = data.lon.toString()
            this.controller.findWeather(data.woeid)
        }
    }

}