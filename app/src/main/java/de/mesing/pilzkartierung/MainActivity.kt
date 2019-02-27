package de.mesing.pilzkartierung

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.mesing.pilzkartierung.modules.fungi_discovery.FungiDiscoveryFragment
import de.mesing.pilzkartierung.modules.mapview.MapViewFragment

class MainActivity : AppCompatActivity(), MapViewFragment.Listener {
    override fun onShowFungiDiscoveryFragment() {
        startFungiDiscoveryFragment()
    }

    // region lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startMapViewFragment()
    }

    private fun startMapViewFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MapViewFragment.newInstance())
                .addToBackStack(null)
                .commit()
    }


    private fun startFungiDiscoveryFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FungiDiscoveryFragment.newInstance())
                .addToBackStack(null)
                .commit()
    }



}
