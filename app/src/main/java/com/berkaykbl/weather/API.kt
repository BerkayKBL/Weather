package com.berkaykbl.weather

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.nio.charset.Charset


private var weatherJSON = JsonObject()

data class WeatherResponse(
    val current: WeatherCurrent,
    val forecast: WeatherForecast,
)

data class WeatherCurrent(
    val temp_c: Float,
    val temp_f: Float,
    val wind_mph: Float,
    val wind_kph: Float,
    val wind_degree: Int,
    val wind_dir: String,
    val humidity: Int,
    val uv: Float,
    val vis_km: Float,
    val vis_miles: Float,
    val condition: JsonObject
)

data class WeatherForecast(
    val forecastday: JsonArray,
)

fun setWeather(context: Context) {
    var conditionJSONData: String? = null
    try {
        val inputStream = context.assets.open("weather.json")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        conditionJSONData = String(buffer, Charset.defaultCharset())

    } catch (ex: IOException) {
        ex.printStackTrace()
    }

    val parser = JsonParser()
    val jsonElement = parser.parse(conditionJSONData)
    weatherJSON = jsonElement.asJsonObject
}

/*


interface ApiService {
    @GET
    suspend fun getWeather(@Url url: String): WeatherResponse
}

object RetrofitInstance {
    private val retrofit by lazy {
        Log.d("here", "here is")
        Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

}*/


class MainViewModel : ViewModel() {
    private val _data = MutableStateFlow<Map<String, WeatherResponse>>(emptyMap())
    val data: StateFlow<Map<String, WeatherResponse>> get() = _data

    fun fetchData(locates: List<String>) {
        viewModelScope.launch {
            val result = mutableMapOf<String, WeatherResponse>()
            locates.forEach { locate ->
                try {

                    val currentJSON = weatherJSON.getAsJsonObject("current")
                    val current = WeatherCurrent(
                        currentJSON.get("temp_c").asFloat,
                        currentJSON.get("temp_f").asFloat,
                        currentJSON.get("wind_mph").asFloat,
                        currentJSON.get("wind_kph").asFloat,
                        currentJSON.get("wind_degree").asInt,
                        currentJSON.get("wind_dir").asString,
                        currentJSON.get("humidity").asInt,
                        currentJSON.get("uv").asFloat,
                        currentJSON.get("vis_km").asFloat,
                        currentJSON.get("vis_miles").asFloat,
                        currentJSON.get("condition").asJsonObject
                    )
                    val forecast =
                        WeatherForecast(weatherJSON.get("forecast").asJsonObject.getAsJsonArray("forecastday"))
                    val response = WeatherResponse(current, forecast)

                    result[locate] = response
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            _data.value = result
        }
    }
}