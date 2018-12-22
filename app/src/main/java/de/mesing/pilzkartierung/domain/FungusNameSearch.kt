package de.mesing.pilzkartierung.domain

import de.mesing.pilzkartierung.util.StringUtils
import org.apache.commons.csv.CSVFormat
import java.io.Reader
import java.io.StringReader


object FungusNameSearch {

    val fungi : MutableList<Fungus> = ArrayList()

    fun findBySearchString(pattern: String) : ArrayList<String> {
        val result = ArrayList<String>()
        if (pattern.isBlank()) {
            return result
        }
        for (fungus in fungi) {
            if (matches(fungus, pattern)) {
                result.add(fungus.latinName())
            }
        }
        return result
    }

    private fun matches(fungus : Fungus, pattern : String) : Boolean {
        return matchesFullPattern(fungus, pattern) || matchesStartingChars(fungus, pattern)
    }

    internal fun matchesFullPattern(fungus : Fungus, pattern : String) : Boolean {
        return fungus.genus.contains(pattern, true) || fungus.species.contains(pattern, true)

    }

    internal fun matchesStartingChars(fungus : Fungus, pattern : String) : Boolean {
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