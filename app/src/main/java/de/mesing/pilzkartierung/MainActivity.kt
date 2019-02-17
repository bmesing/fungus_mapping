package de.mesing.pilzkartierung

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.mesing.pilzkartierung.modules.mapview.MapViewFragment

class MainActivity : AppCompatActivity() {

    // region lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startMapViewFragment()
    }

    private fun startMapViewFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MapViewFragment.newInstance())
                .commit()
    }

}
