package de.mesing.pilzkartierung.domain

import de.mesing.pilzkartierung.util.StringUtils
import org.apache.commons.csv.CSVFormat
import java.io.Reader
import java.io.StringReader


object FungusNameSearch {

    private val fungi : MutableList<Fungus> = ArrayList()

    fun getFungusForLatinName(latinName : String) : Fungus? {
        return fungi.firstOrNull{it.latinName() == latinName}
    }

    fun findBySearchString(pattern: String) : List<String> {
        return fungi.filter{fungus -> matches(fungus, pattern)}
                .map(Fungus::latinName)
    }

    private fun matches(fungus : Fungus, pattern : String) : Boolean {
        if (pattern.isEmpty()) return false
        return matchesFullPattern(fungus, pattern) || matchesStartingChars(fungus, pattern)
    }

    private fun matchesFullPattern(fungus : Fungus, pattern : String) : Boolean {
        return fungus.genus.contains(pattern, true) || fungus.species.contains(pattern, true)

    }

    private fun matchesStartingChars(fungus : Fungus, pattern : String) : Boolean {
        val commonStart = StringUtils.getCommonStartIgnoringSpace(fungus.genus, pattern)
        val remainingPattern = pattern.substring(commonStart.length)
        // TODO: inefficient zweites contains
        return fungus.species.startsWith(remainingPattern, true)
    }

    fun loadNameList(input: String) {
        fungi.clear()
        loadNameList(StringReader(input))
    }

    fun loadNameList( reader: Reader) {
        val records = CSVFormat.EXCEL.withDelimiter(';').withIgnoreEmptyLines().parse(reader)
        for (record in records) {
            fungi.add(Fungus(record[0], record[1]))
        }
    }
}