package de.mesing.pilzkartierung.domain

import org.junit.Assert
import org.junit.Test
import org.osmdroid.util.GeoPoint
import java.time.LocalDate

class FungusDiscoveryCsvSerializerTest {

    @Test
    fun toCsv() {
        val date = LocalDate.of(2019, 4, 21)
        val discovery = FungusDiscoveryRegistry.FungusDiscovery(Fungus("Agaricus", species = "arvensis"), 2, GeoPoint(54.0833, 12.133), date)
        val result = FungusDiscoveryCsvSerializer.toCsv(discovery)
        Assert.assertEquals("Agaricus arvensis,2,54.0833,12.133,2019-04-21", result)
    }

    @Test
    fun toCsvEmptyYear() {
        val discovery = FungusDiscoveryRegistry.FungusDiscovery(Fungus("Agaricus", species = "arvensis"), 2, GeoPoint(54.0833, 12.133), null)
        val result = FungusDiscoveryCsvSerializer.toCsv(discovery)
        Assert.assertEquals("Agaricus arvensis,2,54.0833,12.133,", result)
    }


    @Test
    fun toCsvList() {
    }
}