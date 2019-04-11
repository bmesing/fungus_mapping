package de.mesing.pilzkartierung.domain

import org.junit.Test
import org.osmdroid.util.GeoPoint
import java.util.*

class FungusDiscoveryCsvSerializerTest {

    @Test
    fun toCsv() {
        val date = Date()
        val discovery = FungusDiscoveryRegistry.FungusDiscovery(Fungus("Agaricus", species = "arvensis"), 2, GeoPoint(54.0833, 12.133), date)
    }

    @Test
    fun toCsvList() {
    }
}