package com.toliveira.simplecurrency

import android.content.Context
import java.util.Properties

object GetApiKey {

    fun getApiKey(context: Context): String {
        val properties = Properties()
        val rawResource = context.resources.openRawResource(R.raw.api)
        properties.load(rawResource)
        return properties.getProperty("api_key", "No_API")
    }

}