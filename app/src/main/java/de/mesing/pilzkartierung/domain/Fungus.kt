package de.mesing.pilzkartierung.domain

data class Fungus (
        val genus: String,
        val species: String
) {
    fun latinName() : String {
        return "$genus $species"
    }
}
