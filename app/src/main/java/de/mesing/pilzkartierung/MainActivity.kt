package de.mesing.pilzkartierung

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.AutoCompleteTextView
import de.mesing.pilzkartierung.domain.FungusNameSearch
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import java.io.InputStreamReader


class MainActivity : AppCompatActivity() {

    companion object {
        const val PERMISSION_REQUEST_CODE = 42
    }

    lateinit var map: MapView

     class SearchTextChangedWatcher : TextWatcher {
         override fun afterTextChanged(s: Editable?) {

         }

         override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
         }

         override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
             for (entry in FungusNameSearch.findBySearchString(s.toString()))
                  Log.d("TAG", entry)
         }

     }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadSearchData()
        initMap()
        initNameInputField()
    }

    private fun initNameInputField() {
        val view = findViewById<AutoCompleteTextView>(R.id.fungus_input)
        view.setAdapter(FungiSearchListAdapter(this, android.R.layout.simple_dropdown_item_1line))
    }

    private fun initMap() {
        //load/initialize the osmdroid configuration, this can be done
        val ctx = applicationContext
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))
        //setting this before the layout is inflated is a good idea
        //it 'should' ensure that the map has a writable location for the map cache, even without permissions
        //if no tiles are displayed, you can try overriding the cache path using Configuration.getInstance().setCachePath
        //see also StorageUtils
        //note, the load method also sets the HTTP User Agent to your application's package name, abusing osm's tile servers will get you banned based on this string

        //inflate and create the map
        setContentView(R.layout.activity_main)

        map = findViewById(R.id.map)
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        //fungus_input.addTextChangedListener(SearchTextChangedWatcher())

        val mapController = map.controller
        mapController.setZoom(12.0)
        // start at Rostock
        val startPoint = GeoPoint(54.0833, 12.133)
        mapController.setCenter(startPoint)
    }

    private fun loadSearchData() {
        // TODO: in background
        val inputStream = resources.openRawResource(R.raw.mushroom_names)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_CODE)
            }
        }
        FungusNameSearch.loadNameList(InputStreamReader(inputStream))
    }

    public override fun onResume() {
        super.onResume()
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map.onResume() //needed for compass, my location overlays, v6.0.0 and up
    }

    public override fun onPause() {
        super.onPause()
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        map.onPause()  //needed for compass, my location overlays, v6.0.0 and up
    }
}
