package org.isen.weather.model.data

import com.github.kittinunf.fuel.core.isSuccessful
import com.github.kittinunf.fuel.httpGet
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class TestLocationData {
    @Test
    fun locationLondon(){
        val (request, response, result) = "https://www.metaweather.com/api/location/44418".httpGet().responseObject(LocationData.Deserializer())

        assertTrue(response.isSuccessful)
        result.component1().also { it }
        result.component1().also {
            assertNotNull(it).also { println(it) }
        } ?.let {
            assertEquals("London",it.title)
            assertEquals(6,it.consolidated_weather.size)
            assertEquals(51.506321f,it.lat)
        }
    }
}