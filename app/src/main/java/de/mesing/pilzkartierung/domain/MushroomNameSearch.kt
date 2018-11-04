package de.mesing.pilzkartierung.domain

import org.apache.commons.csv.CSVRecord
import org.apache.commons.csv.CSVFormat
import java.io.FileReader
import java.io.StringReader


object MushroomNameSearch {

    val fungi : MutableList<Mushroom> = ArrayList()

    fun findBySearchString(pattern: String) : Array<String> {
        return emptyArray()
    }


    fun loadNameList(input: String) {
        val inputReader = StringReader(input)
        val records = CSVFormat.DEFAULT.parse(inputReader)
        for (record in records) {
            fungi.add(Mushroom(record[0], record[1]))
        }
    }
}