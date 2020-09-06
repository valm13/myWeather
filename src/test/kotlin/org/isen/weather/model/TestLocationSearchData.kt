package org.isen.weather.model

import com.github.kittinunf.fuel.core.isSuccessful
import com.github.kittinunf.fuel.httpGet
import org.isen.weather.model.data.LocationSearchData
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue


class TestLocationSearchData {
    @Test fun searchLondon(){
        val (request, response, result) = "https://www.metaweather.com/api/location/search/?query=london".httpGet().responseObject(LocationSearchData.Deserializer())

        assertTrue(response.isSuccessful)

        result.component1().also {
            assertNotNull(it).also { println(it) }
        } ?.let {
            assertEquals(1,it.size)
            val data: LocationSearchData = it.first()
            println(data)
            assertEquals(44418,data.woeid)
        }
    }

}