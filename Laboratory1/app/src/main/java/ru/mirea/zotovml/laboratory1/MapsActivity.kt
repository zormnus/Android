package ru.mirea.zotovml.laboratory1

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var isWork:Boolean = true

    companion object {
        const val REQUEST_CODE_PERMISSION_LOCATION = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE;
        mMap.uiSettings.isZoomControlsEnabled = true;
        mMap.isTrafficEnabled = true;
        // Add a marker in Sydney and move the camera
        val permissionStatus = ContextCompat.checkSelfPermission(this
            , Manifest.permission.ACCESS_COARSE_LOCATION)
        when (permissionStatus) {
            PackageManager.PERMISSION_GRANTED -> {
                isWork = true
                mMap.isMyLocationEnabled = true
            }
            else -> {
                ActivityCompat.requestPermissions(this
                    , arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION)
                    , REQUEST_CODE_PERMISSION_LOCATION)
            }
        }
        setUpMap()
    }

    private fun setUpMap() {
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL

        val mirea = LatLng(55.670005, 37.479894)
        val cameraPosition = CameraPosition.builder().target(mirea).zoom(12F).build()

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        mMap.addMarker(MarkerOptions().title("МИРЭА")
            .snippet("Крупнейший политехнический ВУЗ").position(mirea))

    }
}