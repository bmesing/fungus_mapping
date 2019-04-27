package de.mesing.pilzkartierung.util

import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized


@RunWith(Parameterized::class)
class StringUtilsTest(private val expected : String,
                      private val input1 : String,
                      private val input2 : String) {


    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<*> {
            return listOf(
                    arrayOf("", "", ""),
                    arrayOf("", "agaricus", "macro lepiota"),
                    arrayOf("a", "agaricus", "avensis"),
                    arrayOf("agari", "agarixxx", "agaricus"),
                    arrayOf("bisporus", "bisporus var. albidus", "bisporus")
            )
        }
    }


    @Test
    fun testGetCommonStartSingleChar() {
        assertEquals(expected, StringUtils.getCommonStartIgnoringSpace(input1, input2))
    }
}