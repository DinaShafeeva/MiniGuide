package com.example.miniguide.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.miniguide.R
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView


class MapActivity : AppCompatActivity() {
    private var mapview: MapView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.setApiKey("61407ca4-bd81-4965-99a5-fab425b40bd4")
        MapKitFactory.initialize(this)
        setContentView(R.layout.activity_map)

        setContentView(R.layout.activity_map)
        mapview = findViewById<MapView>(R.id.map_view)
        mapview?.map?.move(
            CameraPosition(Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0F),
            null
        )
    }

    override fun onStop() {
        super.onStop()
        mapview?.onStop()
        MapKitFactory.getInstance().onStop()
    }

    override fun onStart() {
        super.onStart()
        mapview?.onStart()
        MapKitFactory.getInstance().onStart()
    }
}