package de.mesing.pilzkartierung.domain

import android.content.Context
import android.content.SharedPreferences
import de.mesing.pilzkartierung.BuildConfig

object SharedPreferencesAccess {
    fun getAppSharedPrefs(context: Context) : SharedPreferences {
        val appName = BuildConfig.APPLICATION_ID
        return context.getSharedPreferences(appName, Context.MODE_PRIVATE)
    }

    fun deleteSharedPreferences(context: Context) {

        TODO("enable when API level is set to 24, and use in FungusDiscoveryRegistryTest.setUp")
        //context.deleteSharedPreferences(BuildConfig.APPLICATION_ID)
    }
}