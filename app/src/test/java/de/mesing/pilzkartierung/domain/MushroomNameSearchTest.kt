package de.mesing.pilzkartierung.domain

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized



@RunWith(Parameterized::class)
class MushroomNameSearchTest (
        val searchString : String,
        val expected: Array<String>
)
{

    val data = """
    Agaricus;arvensis;Schafegerling
    Agaricus;augustus;Riesen-Champignon, Braunschuppiger R.
    Agaricus;benesii;Weißschuppiger Blutegerling
    Agaricus;bernardii;Salzwiesen-Egerling
    Agaricus;bisporus;Zuchtchampignon, Zweisporiger Egerling
    Agaricus;bisporus var. albidus;Zuchtchampignon, weiße Form
    Agaricus;bitorquis;Stadtegerling, Stadtchampignon
    """


    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun primeNumbers(): Collection<*> {
            return listOf(
                    arrayOf("", emptyArray<String>()),
                    arrayOf("atus", emptyArray<String>()),
                    arrayOf("v", arrayOf("Agaricus arvensis", "Agaricus;bisporus var. albidus")),
                    //arrayOf("aa", arrayOf("Agaricus arvensis", "Agaricus augustus")),
                    //arrayOf("aau", arrayOf("Agaricus augustus")),
                    arrayOf("bis", arrayOf("Agaricus bisporus","Agaricus;bisporus var. albidus")),
                    arrayOf("Aga", arrayOf("Agaricus arvensis", "Agaricus augustus", "Agaricus benesii",
                                            "Agaricus bisporus","Agaricus;bisporus var. albidus",
                                            "Agaricus bernardii", "Agaricus;bitorquis")
                    )
            )
        }
    }

    @Before
    fun loadNames() {
        MushroomNameSearch.loadNameList(data)
    }


    @Test
    fun findBySearchString() {
        Assert.assertEquals(expected, MushroomNameSearch.findBySearchString(searchString))
    }
}