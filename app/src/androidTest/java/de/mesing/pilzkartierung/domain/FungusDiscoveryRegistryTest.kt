package de.mesing.pilzkartierung.domain

import android.content.Context
import android.support.test.InstrumentationRegistry
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.osmdroid.util.GeoPoint

class FungusDiscoveryRegistryTest {

    lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getContext()
        FungusDiscoveryRegistry.clearDiscoveries(context)
    }

    @Test
    fun readEmptyDiscoveryList() {
        val result = FungusDiscoveryRegistry.getDiscoveries(context)
        Assert.assertEquals(0, result.size)
    }


    @Test
    fun registerAndReadSingleDiscovery() {
        FungusDiscoveryRegistry.registerDiscovery(
                context,
                Fungus("Agaricus", "arvensis"),
                3,
                GeoPoint(54.0833, 12.133)

        )
        val result = FungusDiscoveryRegistry.getDiscoveries(context)
        Assert.assertEquals(1, result.size)
        Assert.assertEquals(result[0].fungus, Fungus("Agaricus", "arvensis"))
        Assert.assertEquals(result[0].count, 3)
    }

    @Test
    fun registerAndReadMultipleDiscoveries() {
        FungusDiscoveryRegistry.registerDiscovery(
                context,
                Fungus("Agaricus", "arvensis"),
                3,
                GeoPoint(54.0833, 12.133)

        )
        FungusDiscoveryRegistry.registerDiscovery(
                context,
                Fungus("Agaricus", "bitorquis"),
                1,
                GeoPoint(55.0833, 11.133)

        )

        val result = FungusDiscoveryRegistry.getDiscoveries(context)
        Assert.assertEquals(2, result.size)
        Assert.assertEquals(result[0].count, 3)
        Assert.assertEquals(result[1].count, 1)
    }

    @Test
    fun clearList() {
        FungusDiscoveryRegistry.registerDiscovery(
                context,
                Fungus("Agaricus", "arvensis"),
                3,
                GeoPoint(54.0833, 12.133)

        )
        FungusDiscoveryRegistry.clearDiscoveries(context)
        val result = FungusDiscoveryRegistry.getDiscoveries(context)
        Assert.assertEquals(0, result.size)
    }


}