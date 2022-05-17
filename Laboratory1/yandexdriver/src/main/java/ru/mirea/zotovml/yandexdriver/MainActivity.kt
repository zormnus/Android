package ru.mirea.zotovml.yandexdriver

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.RequestPoint
import com.yandex.mapkit.RequestPointType
import com.yandex.mapkit.directions.DirectionsFactory
import com.yandex.mapkit.directions.driving.DrivingOptions
import com.yandex.mapkit.directions.driving.DrivingRoute
import com.yandex.mapkit.directions.driving.DrivingRouter
import com.yandex.mapkit.directions.driving.DrivingSession
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.Error
import com.yandex.runtime.network.NetworkError
import com.yandex.runtime.network.RemoteError


class MainActivity : AppCompatActivity(), DrivingSession.DrivingRouteListener {

    private val MAPKIT_API_KEY = "ec6c8bf5-f1bf-4248-9e08-1b2d0cfeeb08"
    private val ROUTE_START_LOCATION: Point = Point(55.670005, 37.479894)
    private val ROUTE_END_LOCATION: Point = Point(55.794229, 37.700772)
    private val SCREEN_CENTER: Point = Point(
        (ROUTE_START_LOCATION.latitude + ROUTE_END_LOCATION.latitude) / 2,
        (ROUTE_START_LOCATION.longitude + ROUTE_END_LOCATION.longitude) /
                2
    )

    private lateinit var mapView: MapView
    private lateinit var mapObjects: MapObjectCollection
    private lateinit var drivingRouter: DrivingRouter
    private lateinit var drivingSession: DrivingSession
    private val colors = intArrayOf(0xFFFF0000.toInt(), 0xFF00FF00.toInt(), 0x00FFBBBB.toInt(),
        0xFF0000FF.toInt()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        MapKitFactory.setApiKey(MAPKIT_API_KEY)
        MapKitFactory.initialize(this)
        DirectionsFactory.initialize(this)
        // Укажите имя activity
        // Укажите имя activity
        setContentView(R.layout.activity_main)
        super.onCreate(savedInstanceState)
        mapView = findViewById(R.id.mapview)
        // Устанавливаем начальную точку и масштаб
        // Устанавливаем начальную точку и масштаб
        mapView.map.move(
            CameraPosition(
                SCREEN_CENTER, 10F, 0F, 0F)
        )
        // Ининциализируем объект для создания маршрута водителя
        // Ининциализируем объект для создания маршрута водителя
        drivingRouter = DirectionsFactory.getInstance().createDrivingRouter()
        mapObjects = mapView.map.mapObjects.addCollection()
        submitRequest()
    }

    override fun onDrivingRoutes(list: MutableList<DrivingRoute>) {
        var color: Int
        for (i in 0 until list.size) {
            // настроиваем цвета для каждого маршрута
            color = colors[i]
            // добавляем маршрут на карту
            mapObjects.addPolyline(list[i].geometry).strokeColor = color
        }
    }

    override fun onDrivingRoutesError(error: Error) {
        var errorMessage = getString(R.string.unknown_error_message)
        if (error is RemoteError) {
            errorMessage = getString(R.string.remote_error_message)
        } else if (error is NetworkError) {
            errorMessage = getString(R.string.network_error_message)
        }
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    override fun onStop() {
        super.onStop()
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    private fun submitRequest() {
        val options = DrivingOptions()
        options.alternativeCount = 3
        val requestPoints: ArrayList<RequestPoint> = ArrayList()
        requestPoints.add(RequestPoint(
            ROUTE_START_LOCATION,
            RequestPointType.WAYPOINT,
            null))
        requestPoints.add(RequestPoint(
            ROUTE_END_LOCATION,
            RequestPointType.WAYPOINT,
            null))
        drivingSession = drivingRouter.requestRoutes(requestPoints, options, this)
    }
}