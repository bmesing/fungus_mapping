package de.mesing.pilzkartierung.domain

import java.time.format.DateTimeFormatter

object FungusDiscoveryCsvSerializer {

    fun toCsv(discoveries: List<FungusDiscoveryRegistry.FungusDiscovery>) : String {
        val result = StringBuilder()
        discoveries.forEach {
            result.append(toCsv(it)).append("\n")
        }
        return result.toString()
    }

    fun toCsv(discovery: FungusDiscoveryRegistry.FungusDiscovery) : String {
        val fields = Array(5)  { "" }
        var index = 0
        fields[index++] = "${discovery.fungus.genus} ${discovery.fungus.species}"
        fields[index++] = discovery.count.toString()
        fields[index++] = discovery.position.latitude.toString()
        fields[index++] = discovery.position.longitude.toString()
        fields[index++] = if (discovery.time != null) { DateTimeFormatter.ISO_LOCAL_DATE.format(discovery.time) } else ""
        return fields.joinToString(separator = ",")
    }
}