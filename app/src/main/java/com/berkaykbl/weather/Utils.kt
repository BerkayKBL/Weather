package com.berkaykbl.weather

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import org.json.JSONArray
import java.io.IOException
import java.nio.charset.Charset
import java.util.Locale

private var defaultLanguage = "en"
private var conditionJSON: JsonArray = JsonArray()

fun getDefaultLanguage() : String { return defaultLanguage}
fun setConditionJSON(context: Context) {
    var conditionJSONData: String? = null
    try {
        val inputStream = context.assets.open("conditions.json")
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
    conditionJSON = jsonElement.asJsonArray
}

fun getConditionText(code: Int, language: String, isDay: Boolean) : String {
    var conditionText = ""
    val condition = conditionJSON.find {
        it.asJsonObject.get("code").asInt == code
    }


    if (condition != null) {



        val languages = condition.asJsonObject.get("languages").asJsonArray

        val languageJson = languages.find {
            it.asJsonObject.get("lang_iso").asString.equals(language)
        }

        if (languageJson != null) {
            conditionText = if (isDay) languageJson.asJsonObject.get("day_text").asString
            else languageJson.asJsonObject.get("night_text").asString
        } else {
            conditionText = if (isDay) condition.asJsonObject.get("day").asString
            else condition.asJsonObject.get("night").asString
        }

    }

    return conditionText
}


@Composable
fun getStringResourceByName(name: String): String {
    val context = LocalContext.current
    val resId = context.resources.getIdentifier(
        name.lowercase(Locale.getDefault()),
        "string",
        context.packageName
    )
    return if (resId != 0) {
        stringResource(id = resId)
    } else {
        "String resource not found"
    }
}
