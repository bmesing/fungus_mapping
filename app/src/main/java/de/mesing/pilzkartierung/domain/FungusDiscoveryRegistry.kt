package de.mesing.pilzkartierung.domain

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.google.gson.Gson
import de.mesing.pilzkartierung.FungusApplication
import org.osmdroid.util.GeoPoint
import java.time.LocalDate

object FungusDiscoveryRegistry {

    data class FungusDiscovery (
            val fungus: Fungus,
            val count: Int,
            val position: GeoPoint,
            val time: LocalDate? = null
    )

    private const val DISCOVERY_LIST_KEY = "DISCOVERY_LIST_KEY"

    fun registerDiscovery(context: Context, fungus: Fungus, count: Int, position: GeoPoint): FungusDiscovery {
        val prefs = SharedPreferencesAccess.getAppSharedPrefs(context)
        val oldString = prefs.getString(DISCOVERY_LIST_KEY, "")
        val fungusDiscovery = FungusDiscovery(fungus, count, position, LocalDate.now())
        val entry = createFungusListEntry(fungusDiscovery)
        val newString = oldString?.let { "$oldString;$entry" } ?: entry
        prefs.edit()
                .putString(DISCOVERY_LIST_KEY, newString)
                .apply()
        return fungusDiscovery
    }

    fun getDiscoveries() : List<FungusDiscovery> {
        val prefs = SharedPreferencesAccess.getAppSharedPrefs(FungusApplication.context)
        val sharedPrefsEntry = prefs.getString(DISCOVERY_LIST_KEY, null) ?: return emptyList()
        return sharedPrefsEntry.split(";").mapNotNull(this::toFungusDiscovery)
    }

    @VisibleForTesting
    fun createFungusListEntry(fungusDiscovery: FungusDiscovery) : String {

        return Gson().toJson(fungusDiscovery)
    }

    private fun toFungusDiscovery(json: String?) : FungusDiscovery? {
        return Gson().fromJson(json, FungusDiscovery::class.java)
    }

    fun clearDiscoveries(context: Context) {
        val prefs = SharedPreferencesAccess.getAppSharedPrefs(context)
        prefs.edit().putString(DISCOVERY_LIST_KEY, null).apply()
    }


}