package com.berkaykbl.weather

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.berkaykbl.weather.ui.theme.WeatherTheme
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.Locale

private var hourlyWeatherList = listOf<JSONObject>()
private var dailyWeatherList = listOf<JSONObject>()
private var tempNumber = 0
private var language = getDefaultLanguage()

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)/*hourlyWeatherList =
            hourlyWeatherList + JSONObject().put("hour", "19:00").put("degree", "26")
                .put("isNight", "false")
        hourlyWeatherList =
            hourlyWeatherList + JSONObject().put("hour", "19:00").put("degree", "26")
                .put("isNight", "false")
        hourlyWeatherList =
            hourlyWeatherList + JSONObject().put("hour", "19:00").put("degree", "26")
                .put("isNight", "false")
        hourlyWeatherList =
            hourlyWeatherList + JSONObject().put("hour", "19:00").put("degree", "26")
                .put("isNight", "false")
        hourlyWeatherList =
            hourlyWeatherList + JSONObject().put("hour", "19:00").put("degree", "26")
                .put("isNight", "false")
        hourlyWeatherList =
            hourlyWeatherList + JSONObject().put("hour", "19:00").put("degree", "26")
                .put("isNight", "false")

        hourlyWeatherList =
            hourlyWeatherList + JSONObject().put("hour", "20:00").put("degree", "27")
                .put("isNight", true)
        hourlyWeatherList =
            hourlyWeatherList + JSONObject().put("hour", "20:00").put("degree", "27")
                .put("isNight", true)
        hourlyWeatherList =
            hourlyWeatherList + JSONObject().put("hour", "20:00").put("degree", "27")
                .put("isNight", true)
        hourlyWeatherList =
            hourlyWeatherList + JSONObject().put("hour", "20:00").put("degree", "27")
                .put("isNight", true)
        hourlyWeatherList =
            hourlyWeatherList + JSONObject().put("hour", "20:00").put("degree", "27")
                .put("isNight", true)
        hourlyWeatherList =
            hourlyWeatherList + JSONObject().put("hour", "20:00").put("degree", "27")
                .put("isNight", true)

        dailyWeatherList =
            dailyWeatherList + JSONObject().put("date", "08-06-2024").put("maxDegree", "26")
                .put("minDegree", "19").put("isSunny", true).put("text", "Sunny")
        dailyWeatherList =
            dailyWeatherList + JSONObject().put("date", "08-06-2024").put("maxDegree", "26")
                .put("minDegree", "19").put("isSunny", true).put("text", "Mustly Sunny")
        dailyWeatherList =
            dailyWeatherList + JSONObject().put("date", "08-06-2024").put("maxDegree", "26")
                .put("minDegree", "19").put("isSunny", true).put("text", "Sunny")
        dailyWeatherList =
            dailyWeatherList + JSONObject().put("date", "08-06-2024").put("maxDegree", "26")
                .put("minDegree", "19").put("isSunny", true).put("text", "Sunny")
        dailyWeatherList =
            dailyWeatherList + JSONObject().put("date", "08-06-2024").put("maxDegree", "26")
                .put("minDegree", "19").put("isSunny", true).put("text", "Sunny")
        dailyWeatherList =
            dailyWeatherList + JSONObject().put("date", "08-06-2024").put("maxDegree", "26")
                .put("minDegree", "19").put("isSunny", true).put("text", "Sunny")
        dailyWeatherList =
            dailyWeatherList + JSONObject().put("date", "08-06-2024").put("maxDegree", "26")
                .put("minDegree", "19").put("isSunny", true).put("text", "Sunny")
*/

        val default = Locale.getDefault().toLanguageTag().split("-")[0]
        language = default
        setWeather(this)
        setConditionJSON(this)
        setContent {
            WeatherTheme {
                Greeting()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Greeting2(mainViewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val data = mainViewModel.data.collectAsState().value
    var colorScheme = MaterialTheme.colorScheme

    val locates =
        listOf("Esenyurt")
    remember {
        mainViewModel.fetchData(locates)
    }

    val pagerState = rememberPagerState(pageCount = { locates.size }, initialPage = 0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.End,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.city),
                contentDescription = "Locates",
                modifier = Modifier.size(30.dp),
                tint = colorScheme.secondary
            )
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                painter = painterResource(id = R.drawable.three_dot),
                contentDescription = "Settings",
                modifier = Modifier.size(30.dp),
                tint = colorScheme.secondary
            )

        }

        Column (modifier = Modifier.fillMaxSize()) {
            HorizontalPager(
                pagerState,
                modifier = Modifier.fillMaxSize()
            ) { index ->
                val locateName = locates[index]

                println("qwewqe")

                data.let {
                    val locate = data[locateName]
                    if (locate != null) {
                        val current = locate.current
                        val forecast = locate.forecast

                        val forecastDay = forecast.forecastday
                        val hourlyWeather = ArrayList<WeatherHourly>()
                        val dailyWeather = ArrayList<WeatherDaily>()

                        val hours = forecastDay[0].asJsonObject.getAsJsonArray("hour")

                        var i = 0
                        while (i < hours.size()) {
                            val hour = hours[i].asJsonObject
                            var time = hour.get("time").asString
                            time = time.split(" ")[1]
                            if (System.currentTimeMillis() < hour.get("time_epoch").asLong * 1000) {
                                hourlyWeather.add(
                                    WeatherHourly(
                                        time,
                                        hour.get("temp_c").asFloat,
                                        hour.get("temp_f").asFloat,
                                        ""
                                    )
                                )
                            }

                            i++
                        }

                        i = 0
                        while (i < forecastDay.size()) {
                            val day = forecastDay[i].asJsonObject
                            val date = day.get("date").asString
                            val dayInfo = day.get("day").asJsonObject
                            dailyWeather.add(
                                WeatherDaily(
                                    date,
                                    getConditionText(
                                        dayInfo.get("condition").asJsonObject.get("code").asInt,
                                        language,
                                        true
                                    ),
                                    dayInfo.get("maxtemp_c").asFloat,
                                    dayInfo.get("mintemp_c").asFloat,
                                    dayInfo.get("maxtemp_f").asFloat,
                                    dayInfo.get("mintemp_f").asFloat,
                                    ""
                                )
                            )

                            i++
                        }

                        val weatherLocate =
                            WeatherLocate(
                                locateName,
                                getConditionText(
                                    current.condition.get("code").asInt,
                                    language,
                                    true
                                ),
                                current.temp_c,
                                current.wind_kph,
                                current.wind_dir,
                                current.vis_km,
                                current.humidity,
                                current.uv,
                                hourlyWeather,
                                dailyWeather
                            )
                        LazyColumn(
                            modifier = Modifier
                                .padding(5.dp, 0.dp)
                        ) {
                            item {
                                LocatePage(weatherLocate)
                            }
                            item {
                                LocatePageHourly(weatherLocate)
                            }
                        }
                    }

                }
            }
        }

        /*Text(
            text = locateName.toString(),
            fontSize = 40.sp,
            modifier = Modifier.padding(0.dp, 5.dp),
            color = colorScheme.secondary,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp, 0.dp),
            verticalAlignment = Alignment.Bottom,
        ) {

            Text(
                text = "$temp°",
                fontSize = 130.sp,
                color = colorScheme.secondary,
            )
            Text(
                text = "Sunny",
                fontSize = 40.sp,
                color = colorScheme.secondary,
                modifier = Modifier
                    .padding(0.dp, 25.dp)
                    .offset((-35).dp),
            )
        }

        LazyRow(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(colorScheme.tertiary)
                .padding(0.dp, 15.dp)
        ) {
            items(hourlyWeather) { currentHour ->

                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = currentHour.getString("hour"),
                        color = colorScheme.secondary,
                        fontSize = 18.sp
                    )
                    Image(
                        painter = painterResource(id = R.drawable.sunny_day),
                        contentDescription = "isNight",
                        modifier = Modifier
                            .size(40.dp)
                            .padding(0.dp, 10.dp),
                        contentScale = ContentScale.Crop,

                        )
                    Text(
                        text = currentHour.getString("degree"),
                        color = colorScheme.secondary,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        LazyColumn(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(colorScheme.tertiary)
                .padding(0.dp, 5.dp)
                .fillMaxWidth()
        ) {
            items(dailyWeather) { currentDay ->
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(10.dp, 0.dp)
                ) {
                    Text(
                        text = currentDay.getString("date"),
                        color = colorScheme.secondary,
                        fontSize = 18.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.sunny_day),
                        contentDescription = "isNight",
                        modifier = Modifier
                            .size(40.dp)
                            .padding(0.dp, 10.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = currentDay.getString("text"),
                        color = colorScheme.secondary,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(3.dp, 0.dp)
                            .width(100.dp),
                        softWrap = false,
                        overflow = TextOverflow.Clip
                    )
                    Text(
                        text = currentDay.getString("maxDegree"),
                        color = colorScheme.secondary,
                        fontSize = 18.sp
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
        }*/
    }
}