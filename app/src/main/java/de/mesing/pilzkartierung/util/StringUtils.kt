package de.mesing.pilzkartierung.util

import kotlin.math.min

object StringUtils {
    fun getCommonStartIgnoringSpace(s1: String, s2: String): String {
        var result = ""
        for (i in 0 until min(s1.length, s2.length)) {
            if (s1[i].toLowerCase() != s2[i].toLowerCase()) {
                return result
            } else {
                result += s1[i]
            }
        }
        return result
    }
}