package de.mesing.pilzkartierung.domain

import androidx.annotation.VisibleForTesting
import de.mesing.pilzkartierung.FungusApplication
import de.mesing.pilzkartierung.util.gson.GsonUtils
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

    fun registerDiscovery(fungus: Fungus, count: Int, position: GeoPoint): FungusDiscovery {
        val prefs = SharedPreferencesAccess.getAppSharedPrefs(FungusApplication.context)
        val oldString = prefs.getString(DISCOVERY_LIST_KEY, "")
        val fungusDiscovery = FungusDiscovery(fungus, count, position, LocalDate.now())
        val entry = createFungusListEntry(fungusDiscovery)
        val newString = if (!oldString.isNullOrBlank()) "$oldString;$entry" else entry
        prefs.edit()
                .putString(DISCOVERY_LIST_KEY, newString)
                .apply()
        return fungusDiscovery
    }

    fun getDiscoveries() : List<FungusDiscovery> {
        val prefs = SharedPreferencesAccess.getAppSharedPrefs(FungusApplication.context)
        val sharedPrefsEntry = prefs.getString(DISCOVERY_LIST_KEY, null) ?: return emptyList()
        return sharedPrefsEntry.split(";").filterNot{ it.isBlank() }.mapNotNull(this::toFungusDiscovery)
    }

    @VisibleForTesting
    fun createFungusListEntry(fungusDiscovery: FungusDiscovery) : String {

        return GsonUtils.gson.toJson(fungusDiscovery)
    }

    private fun toFungusDiscovery(json: String?) : FungusDiscovery? {
        if (json.isNullOrBlank())
                return null
        return GsonUtils.gson.fromJson(json, FungusDiscovery::class.java)
    }

    fun clearDiscoveries() {
        val prefs = SharedPreferencesAccess.getAppSharedPrefs(FungusApplication.context)
        prefs.edit().putString(DISCOVERY_LIST_KEY, null).apply()
    }
}