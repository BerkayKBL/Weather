package com.berkaykbl.weather

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.width
import kotlinx.coroutines.withContext
import java.util.Locale
import kotlin.coroutines.CoroutineContext

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LocatePage(locate: WeatherLocate) {


    Spacer(modifier = Modifier.height(5.dp))

    Text(
        text = "${locate.temp_c}°",
        fontSize = 55.sp,
        color = colorScheme.secondary,
        modifier = Modifier.padding(10.dp, 30.dp, 0.dp, 0.dp),
        fontWeight = FontWeight.Bold
    )
    Text(
        text = locate.text,
        fontSize = 25.sp,
        color = colorScheme.secondary,
        modifier = Modifier
            .padding(10.dp, 0.dp, 0.dp, 0.dp)
            .basicMarquee(
                animationMode = MarqueeAnimationMode.Immediately,
                delayMillis = 1
            ),
    )

    Spacer(modifier = Modifier.height(15.dp))


}

@Composable
fun LocatePageHourly(weatherHourly: ArrayList<WeatherHourly>) {

    LazyRow(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(colorScheme.tertiary)
            .padding(0.dp, 10.dp)
    ) {
        items(weatherHourly, key = {
            it.id
        }) { currentHour ->
            Spacer(modifier = Modifier.width(5.dp))
            Column {

                Text(
                    text = currentHour.hour,
                    color = colorScheme.secondary,
                    fontSize = 15.sp
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
                    text = currentHour.temp_c.toString(),
                    color = colorScheme.secondary,
                    fontSize = 18.sp
                )

            }
            Spacer(modifier = Modifier.width(5.dp))
        }
    }
}

@Composable
fun LocatePageDaily(weatherDaily: ArrayList<WeatherDaily>) {
    var day = 0
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(colorScheme.tertiary)
            .padding(0.dp, 10.dp)
            .fillMaxWidth(),
    ) {

        while (day < weatherDaily.size) {
            val currentDay = weatherDaily[day]
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(5.dp, 0.dp)
            ) {

                Text(
                    text = currentDay.date,
                    modifier = Modifier.weight(1f),
                    color = colorScheme.secondary,
                    fontSize = 14.sp,
                    softWrap = false,
                    overflow = TextOverflow.Ellipsis
                )
                Image(painter = painterResource(id = R.drawable.sunny_day), contentDescription = "",
                    modifier = Modifier.size(30.dp))
                Text(
                    text = "Çoğunlukla Rü",
                    color = colorScheme.secondary,
                    modifier = Modifier
                        .padding(5.dp, 0.dp)
                        .width(80.dp),
                    fontSize = 14.sp,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = false
                )
                Text(
                    text = "${currentDay.maxtemp_c}/${currentDay.mintemp_c}",
                    color = colorScheme.secondary,
                    fontSize = 14.sp
                )
            }
            day++
        }

    }
}

@Composable
fun LocatePageDetails(locate: WeatherLocate) {
    val detailsItems = ArrayList<WeatherDetails>()

    detailsItems.add(
        WeatherDetails(
            Icons.Rounded.Info,
            getStringResourceByName(name = locate.wind_dir),
            locate.wind_kph.toString(),
            "km/h",
            true
        )
    )
    detailsItems.add(WeatherDetails(Icons.Rounded.Info, "UV", locate.uv.toString(), "Low", true))
    detailsItems.add(
        WeatherDetails(
            Icons.Rounded.Info,
            "Vision Distance",
            locate.vis_km.toString(),
            "km",
            true
        )
    )
    detailsItems.add(
        WeatherDetails(
            Icons.Rounded.Info,
            "Humidity",
            locate.humidity.toString(),
            "%",
            true
        )
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .wrapContentSize(unbounded = false)
            .height(220.dp)
            .fillMaxWidth(),
    ) {
        items(detailsItems.size, key = {
            detailsItems[it].id
        }) { index ->
            val item = detailsItems[index]
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(colorScheme.tertiary)
                    .padding(15.dp, 5.dp)
            ) {
                Icon(item.icon, contentDescription = item.content, Modifier.size(18.dp))
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = item.title, color = colorScheme.secondary)

                Spacer(modifier = Modifier.height(5.dp))
                LazyRow(
                    verticalAlignment = Alignment.Bottom
                ) {
                    item {

                        Text(
                            text = item.content,
                            fontSize = 25.sp,
                            color = colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = item.contentDetail,
                            fontSize = 18.sp,
                            color = colorScheme.secondary
                        )
                    }
                }

            }

        }
    }

}