package com.ddx.compose.model

import android.util.Log
import com.ddx.compose.GlobalApp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

class DataHelper {
    /**
     * Read the data from terms.json and parse it into Term object list to return.
     */
    suspend fun getTermList() = withContext(Dispatchers.Default) {
        var terms: List<Term> = ArrayList()
        try {
            val assetsManager = GlobalApp.context.assets
            val inputReader = InputStreamReader(assetsManager.open("terms.json"))
            val jsonString = BufferedReader(inputReader).readText()
            val typeOf = object : TypeToken<List<Term>>() {}.type
            terms = Gson().fromJson(jsonString, typeOf)
        } catch (e: Exception) {
            Log.d(
                "TermModel", e.message + ""
            )
        }
        terms
    }
}