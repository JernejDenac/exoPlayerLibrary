package com.example.smartgasmetering


import android.app.Application
import android.content.Context
import com.example.lib.SmartGasMeter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import java.io.File
import java.util.UUID

var DATA_FILE = "data.json"

class MyApplication : Application() {
    private var _dataFile: File? = null
    val dataFile: File
        get() = _dataFile ?: File(filesDir, DATA_FILE)

    private var appUUID: String? = null


    override fun onCreate() {
        super.onCreate()
        appUUID = getOrCreateUUID()
    }

    fun saveData(smartGasMeters: List<SmartGasMeter>) {
        val gson = Gson()
        val jsonString = gson.toJson(smartGasMeters)
        dataFile.writeText(jsonString)//prepiše vsebino
    }

    fun loadData(): MutableList<SmartGasMeter> {
        if (!dataFile.exists()) {
            return mutableListOf()
        }
        val gson = Gson()
        val type = object : TypeToken<List<SmartGasMeter>>() {}.type
        return gson.fromJson(dataFile.readText(), type) ?: mutableListOf()
    }

    private fun getOrCreateUUID(): String {
        val sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE)
        var uuid = sharedPreferences.getString("app_uuid", null)

        if (uuid == null) {
            uuid = UUID.randomUUID().toString()
            sharedPreferences.edit().putString("app_uuid", uuid).apply()
        }
        return uuid
    }
}