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
    fun toCsvListEmpty() {
        Assert.assertEquals(FungusDiscoveryCsvSerializer.toCsv(emptyList()), "")
    }

    @Test
    fun toCsvListSingleEntry() {
        val discovery = FungusDiscoveryRegistry.FungusDiscovery(Fungus("Agaricus", species = "arvensis"), 2, GeoPoint(54.0833, 12.133), null)
        Assert.assertEquals("Agaricus arvensis,2,54.0833,12.133,\n", FungusDiscoveryCsvSerializer.toCsv(listOf(discovery)))
    }

    @Test
    fun toCsvListMultipleEntries() {
        val discovery1 = FungusDiscoveryRegistry.FungusDiscovery(Fungus("Agaricus", species = "arvensis"), 2, GeoPoint(54.0833, 12.133), null)
        val discovery2 = FungusDiscoveryRegistry.FungusDiscovery(Fungus("Agaricus", species = "bernardii"), 1, GeoPoint(54.0833, 12.233), null)
        Assert.assertEquals(
                "Agaricus arvensis,2,54.0833,12.133,\nAgaricus bernardii,1,54.0833,12.233,\n",
                FungusDiscoveryCsvSerializer.toCsv(listOf(discovery1, discovery2)))
    }

}