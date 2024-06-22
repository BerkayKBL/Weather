package com.berkaykbl.weather

import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID

@Immutable
data class WeatherLocate (
    val title: String,
    val text: String,
    val temp_c: Float,
    val wind_kph: Float,
    val wind_dir: String,
    val vis_km: Float,
    val humidity: Int,
    val uv: Float,
    /*val temp_f: Float,
    val wind_mph: Float,
    val wind_kph: Float,
    val wind_degree: Int,
    val wind_dir: String,
    val humidity: Int,
    val uv: Float,
    val vis_km: Float,
    val vis_miles: Float,*/
    val weatherHourly: ArrayList<WeatherHourly>,
    val weatherDaily: ArrayList<WeatherDaily>,
    val id: String = UUID.randomUUID().toString()
)

@Immutable
data class WeatherHourly(
    val hour: String,
    val temp_c: Float,
    val temp_f: Float,
    val icon: String,
    val id: String = UUID.randomUUID().toString()
)

@Immutable
data class WeatherDaily(
    val date: String,
    val text: String,
    val maxtemp_c: Float,
    val mintemp_c: Float,
    val maxtemp_f: Float,
    val mintemp_f: Float,
    val icon: String,
    val id: String = UUID.randomUUID().toString()
)

@Immutable
data class WeatherDetails(
    val icon: ImageVector,
    val title: String,
    val content: String,
    val contentDetail: String,
    val isBottom: Boolean,
    val id: String = UUID.randomUUID().toString()
)

val locates = ArrayList<WeatherLocate>()