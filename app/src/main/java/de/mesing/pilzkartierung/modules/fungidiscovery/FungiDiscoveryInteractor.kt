package de.mesing.pilzkartierung.modules.fungidiscovery

import android.content.Context
import android.content.Intent
import de.mesing.pilzkartierung.domain.FungusDiscoveryCsvSerializer
import de.mesing.pilzkartierung.domain.FungusDiscoveryRegistry

class FungiDiscoveryInteractor {
    fun shareDiscoveryList(context: Context) {
        val csv = FungusDiscoveryCsvSerializer.toCsv(FungusDiscoveryRegistry.getDiscoveries())

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, csv)
            type = "text/plain"
        }
        context.startActivity(sendIntent)
    }

    fun deleteDiscoveryList() {
        FungusDiscoveryRegistry.clearDiscoveries()
    }
}