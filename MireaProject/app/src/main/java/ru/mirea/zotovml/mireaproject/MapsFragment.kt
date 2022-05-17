package ru.mirea.zotovml.mireaproject

import android.Manifest
import android.R.attr.radius
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*
import kotlin.math.cos
import kotlin.math.sin


class MapsFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mMap:GoogleMap

    private val mirea_data: ArrayList<Pair<Double, Double>> = arrayListOf(
        (55.6700242747548 to 37.48032748853591),
        (45.052140651537506 to 41.912489524803135),
        (55.96364574750441 to 38.033276219061065)
    )
    companion object {
        const val REQUEST_CODE_PERMISSION_LOCATION = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.isTrafficEnabled = true

        // Создание обозначений всех кампусов Мирэа
        val mirea_coordinates:ArrayList<LatLng> = arrayListOf()
        for (i in 0 until mirea_data.size){
            mirea_coordinates.add(LatLng(mirea_data[i].first, mirea_data[i].second))
        }
        mMap.addMarker(MarkerOptions().title("МИРЭА")
            .snippet("1947г. пр. Вернадского, 78, Москва, 119454 " +
                    "(${mirea_data[0].first},${mirea_data[0].second})")
            .position(mirea_coordinates[0]))
        mMap.addMarker(MarkerOptions().title("МИРЭА")
            .snippet("1947г. пр-т Кулакова, 8, Ставрополь, Ставропольский край, 355028 " +
                    "(${mirea_data[1].first},${mirea_data[1].second})")
            .position(mirea_coordinates[1]))
        mMap.addMarker(MarkerOptions().title("МИРЭА")
            .snippet("1947г. 2А, корп. 61, ул. Вокзальная, Фрязино, Московская обл. " +
                    "(${mirea_data[2].first},${mirea_data[2].second})")
            .position(mirea_coordinates[2]))

        val permissionStatus = this.context?.let {
            ContextCompat.checkSelfPermission(
                it, Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        when (permissionStatus) {
            PackageManager.PERMISSION_GRANTED -> {
                mMap.isMyLocationEnabled = true
                getRandomLocation(55.6700242747548,37.48032748853591,15000000)
            }
            else -> {
                ActivityCompat.requestPermissions(
                    this.context as Activity
                    , arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION)
                    , REQUEST_CODE_PERMISSION_LOCATION)
            }
        }
    }

    private fun getRandomLocation(lat:Double, lng:Double, radius: Int) {
        val random = Random()
        val radiusInDegrees = (radius / 111000f)

        val u = random.nextDouble()
        val v = random.nextDouble()
        val w = radiusInDegrees * Math.sqrt(u)
        val t = 2 * Math.PI * v
        val x = w * cos(t)
        val y = w * sin(t)

        // Adjust the x-coordinate for the shrinking of the east-west distances

        // Adjust the x-coordinate for the shrinking of the east-west distances
        val new_x = x / cos(lat)

        val coordinates =  (y + lat to new_x + lng)
        val randomTarget = LatLng(coordinates.first, coordinates.second)
        val cameraPosition = CameraPosition.builder().target(randomTarget).zoom(12F).build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        mMap.addMarker(MarkerOptions().title("УПС!")
            .snippet("КАК Я ЗДЕСЬ ОКАЗАЛСЯ?").position(randomTarget))


    }
}