package de.mesing.pilzkartierung.util.gson

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.time.LocalDate

object GsonUtils {
    val gson : Gson = GsonBuilder().registerTypeAdapter(LocalDate::class.java, LocalDateAdapter()).create()

}