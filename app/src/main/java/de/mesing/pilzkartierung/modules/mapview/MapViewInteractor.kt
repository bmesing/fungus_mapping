package de.mesing.pilzkartierung.modules.mapview

import android.content.Context
import de.mesing.pilzkartierung.domain.FungusDiscoveryRegistry
import de.mesing.pilzkartierung.domain.FungusNameSearch
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.OverlayItem
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class MapViewInteractor {

    class DiscoveryRegistrationResult(
            val state : State,
            val discovery : FungusDiscoveryRegistry.FungusDiscovery? = null
    ) {

        enum class State {
            Success,
            NoPositionFix,
            NoFungusFound
        }
    }

    fun registerDiscovery(fungusName: String, countText: String, locationProvider: MyLocationNewOverlay, context: Context) : DiscoveryRegistrationResult {
        val discoveredFungus = FungusNameSearch.getFungusForLatinName(fungusName)
                ?: return DiscoveryRegistrationResult(DiscoveryRegistrationResult.State.NoFungusFound)
        val count = getDiscoveryCount(countText)
        val location = getDiscoveryLocation(locationProvider)
                ?: return DiscoveryRegistrationResult(DiscoveryRegistrationResult.State.NoPositionFix)
        val discovery = FungusDiscoveryRegistry.registerDiscovery(discoveredFungus, count, location)
        return DiscoveryRegistrationResult(DiscoveryRegistrationResult.State.Success, discovery)
    }

    private fun getDiscoveryCount(countText: String) : Int {
        return countText.toIntOrNull() ?: 1
    }

    private fun getDiscoveryLocation(locationProvider: MyLocationNewOverlay) : GeoPoint? {
        val location = locationProvider.lastFix
        return GeoPoint(location.latitude, location.longitude)
    }

    fun getDiscoveryOverlayItems() : List<OverlayItem> {
        return FungusDiscoveryRegistry.getDiscoveries().map(this::mapToOverlayItem)
    }

    fun mapToOverlayItem(discovery: FungusDiscoveryRegistry.FungusDiscovery) : OverlayItem {
        return OverlayItem(
                discovery.fungus.latinName(),
                null,
                discovery.position
        )
    }
}