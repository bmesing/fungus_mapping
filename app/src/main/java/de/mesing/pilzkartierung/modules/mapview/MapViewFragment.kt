package de.mesing.pilzkartierung.modules.mapview

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import de.mesing.pilzkartierung.FungiSearchListAdapter
import de.mesing.pilzkartierung.R
import de.mesing.pilzkartierung.domain.Fungus
import de.mesing.pilzkartierung.domain.FungusDiscoveryRegistry
import de.mesing.pilzkartierung.domain.FungusNameSearch
import kotlinx.android.synthetic.main.fragment_map_view.*
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import java.io.InputStreamReader


class MapViewFragment : Fragment() {
    companion object {
        const val PERMISSION_REQUEST_CODE = 42

        fun newInstance() : MapViewFragment {
            return MapViewFragment()
        }
    }

    lateinit var map: MapView
    lateinit var myLocationOverlay: MyLocationNewOverlay

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadSearchData()
        initMap()
        initNameInputField()
        initRegisterFungi()
    }

    override fun onResume() {
        super.onResume()
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map.onResume() //needed for compass, my location overlays, v6.0.0 and up
    }

    override fun onPause() {
        super.onPause()
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        map.onPause()  //needed for compass, my location overlays, v6.0.0 and up
    }
    // endregion

    // region initActivity
    private fun initNameInputField() {
        fungus_input.setAdapter(FungiSearchListAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line))
    }

    private fun initMap() {
        //load/initialize the osmdroid configuration, this can be done
        val ctx = requireActivity().applicationContext
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))
        //setting this before the layout is inflated is a good idea
        //it 'should' ensure that the map has a writable location for the map cache, even without permissions
        //if no tiles are displayed, you can try overriding the cache path using Configuration.getInstance().setCachePath
        //see also StorageUtils
        //note, the load method also sets the HTTP User Agent to your application's package name, abusing osm's tile servers will get you banned based on this string

        map = map_view
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.zoomController.setZoomInEnabled(true)
        map.zoomController.setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT)
        map.setMultiTouchControls(true)


        val mapController = map.controller
        mapController.setZoom(12.0)
        // start at Rostock
        val startPoint = GeoPoint(54.1833, 12.133)
        mapController.setCenter(startPoint)

        myLocationOverlay = MyLocationNewOverlay(map)
        map.overlays.add(myLocationOverlay)
        map.postInvalidate()
        myLocationOverlay.enableFollowLocation()

        move_to_current_position_button.setOnClickListener { toggleFollowPosition() }
    }

    fun toggleFollowPosition() {
        if (myLocationOverlay.isFollowLocationEnabled) {
            myLocationOverlay.disableFollowLocation()
            Toast.makeText(activity, "Folgemodus pausiert", Toast.LENGTH_SHORT).show()
        }
        else {
            myLocationOverlay.enableFollowLocation()
            Toast.makeText(activity, "Folge aktueller Position", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initRegisterFungi() {
        register_fungus_button.setOnClickListener { registerDiscovery() }
    }

    private fun loadSearchData() {
        // TODO: in background
        val inputStream = resources.openRawResource(R.raw.mushroom_names)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_CODE)
            }
        }
        FungusNameSearch.loadNameList(InputStreamReader(inputStream))
    }

    // endregion

    private fun resetDiscoveryInput() {
        fungus_input.text.clear()
        fungus_number_input.text.clear()
    }


    // region business Logic
    private fun registerDiscovery() {
        val discoveredFungus = getSelectedFungus()
        if (discoveredFungus == null) {
            Toast.makeText(activity, "Kein Pilz ausgewählt", Toast.LENGTH_LONG).show()
            return
        }
        val count = getDiscoveryCount()
        val location = getDiscoveryLocation()
        FungusDiscoveryRegistry.registerDiscovery(requireActivity(), discoveredFungus, count, location)
        Toast.makeText(activity, "Plizfund registriert: ${count}x ${discoveredFungus.latinName()} at lat: ${location.latitude}, long: ${location.longitude}", Toast.LENGTH_LONG).show()
        resetDiscoveryInput()

    }

    private fun getSelectedFungus() : Fungus? {
        val fungusName = fungus_input.text.toString()
        return FungusNameSearch.getFungusForLatinName(fungusName)
    }

    private fun getDiscoveryCount() : Int {
        return fungus_number_input.text.toString().toIntOrNull() ?: 1
    }

    private fun getDiscoveryLocation() : GeoPoint {
        val location = myLocationOverlay.lastFix

        return GeoPoint(location.latitude, location.longitude)
    }
    // endregion
}
