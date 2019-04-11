package de.mesing.pilzkartierung.domain

object FungusDiscoveryCsvSerializer {

    fun toCsv(discoveries: List<FungusDiscoveryRegistry.FungusDiscovery>) : String {
        val result = StringBuilder()
        discoveries.forEach {
            result.append(toCsv(it))
        }
        return result.toString()
    }

    fun toCsv(discovery: FungusDiscoveryRegistry.FungusDiscovery) : String {
        return ""
    }
}