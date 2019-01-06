package de.mesing.pilzkartierung.domain

import android.content.Context
import android.support.annotation.VisibleForTesting
import com.google.gson.Gson
import org.osmdroid.util.GeoPoint
import java.util.*

object FungusDiscoveryRegistry {

    internal data class FungusData(val fungus: Fungus, val count: Int, val position: GeoPoint, val time: Date)

    private const val DISCOVERY_LIST_KEY = "DISCOVERY_LIST_KEY"

    fun registerDiscovery(context: Context, fungus: Fungus, count: Int, position: GeoPoint) {
        val prefs = SharedPreferencesAccess.getAppSharedPrefs(context)
        val oldString = prefs.getString(DISCOVERY_LIST_KEY, "")
        val entry = createFungusListEntry(fungus, count, position, Date())
        val newString = oldString?.let { "$oldString,$entry" } ?: entry
        prefs.edit()
                .putString(DISCOVERY_LIST_KEY, newString)
                .apply()
    }

    @VisibleForTesting
    fun createFungusListEntry(fungus: Fungus, count: Int, position: GeoPoint, time: Date) : String {
        val fungusData = FungusData(fungus, count, position, time)
        val gson = Gson()
        return gson.toJson(fungusData)
    }
}