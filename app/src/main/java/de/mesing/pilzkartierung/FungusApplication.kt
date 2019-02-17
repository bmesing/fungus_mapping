package de.mesing.pilzkartierung

import android.app.Application
import android.content.Context

class FungusApplication : Application() {

    lateinit var context: Context

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}