package de.mesing.pilzkartierung.domain

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.osmdroid.util.GeoPoint

class FungusDiscoveryRegistryTest {

    @Before
    fun setUp() {
        FungusDiscoveryRegistry.clearDiscoveries()
    }

    @Test
    fun readEmptyDiscoveryList() {
        val result = FungusDiscoveryRegistry.getDiscoveries()
        Assert.assertEquals(0, result.size)
    }


    @Test
    fun registerAndReadSingleDiscovery() {
        FungusDiscoveryRegistry.registerDiscovery(
                Fungus("Agaricus", "arvensis"),
                3,
                GeoPoint(54.0833, 12.133)

        )
        val result = FungusDiscoveryRegistry.getDiscoveries()
        Assert.assertEquals(1, result.size)
        Assert.assertEquals(result[0].fungus, Fungus("Agaricus", "arvensis"))
        Assert.assertEquals(result[0].count, 3)
    }

    @Test
    fun registerAndReadMultipleDiscoveries() {
        FungusDiscoveryRegistry.registerDiscovery(
                Fungus("Agaricus", "arvensis"),
                3,
                GeoPoint(54.0833, 12.133)

        )
        FungusDiscoveryRegistry.registerDiscovery(
                Fungus("Agaricus", "bitorquis"),
                1,
                GeoPoint(55.0833, 11.133)

        )

        val result = FungusDiscoveryRegistry.getDiscoveries()
        Assert.assertEquals(2, result.size)
        Assert.assertEquals(result[0].count, 3)
        Assert.assertEquals(result[1].count, 1)
    }

    @Test
    fun clearList() {
        FungusDiscoveryRegistry.registerDiscovery(
                Fungus("Agaricus", "arvensis"),
                3,
                GeoPoint(54.0833, 12.133)

        )
        FungusDiscoveryRegistry.clearDiscoveries()
        val result = FungusDiscoveryRegistry.getDiscoveries()
        Assert.assertEquals(0, result.size)
    }


}