package de.mesing.pilzkartierung.modules.fungi_discovery

import android.content.Context
import de.mesing.pilzkartierung.domain.FungusDiscoveryRegistry

class FungiDiscoveryInteractor {
    fun shareDiscoveryList(context: Context) {
        FungusDiscoveryRegistry.getDiscoveries()
    }
}