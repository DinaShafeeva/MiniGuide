package com.example.miniguide.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.miniguide.R
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Geometry
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.search.*
import com.yandex.runtime.image.ImageProvider
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : AppCompatActivity() {
    private var mapView: MapView? = null
    private var searchManager: SearchManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        MapKitFactory.setApiKey("61407ca4-bd81-4965-99a5-fab425b40bd4")
        MapKitFactory.initialize(this)
        SearchFactory.initialize(this)

        setContentView(R.layout.activity_map)
        super.onCreate(savedInstanceState)

        searchManager = SearchFactory.getInstance().createSearchManager(SearchManagerType.COMBINED)

        mapView = findViewById<MapView>(R.id.map_view)

        mapView?.map?.move(
            CameraPosition(Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0F),
            null
        )
        initSearchView()
    }

    private fun initSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val point = Geometry.fromPoint(Point(59.95, 30.32))
                val searchSession = searchManager!!.submit(
                    query,
                    point,
                    SearchOptions(),
                    object : Session.SearchListener {
                        override fun onSearchResponse(p0: Response) {

                        }

                        override fun onSearchError(p0: com.yandex.runtime.Error) {

                        }
                    }
                )
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val point = Geometry.fromPoint(Point(59.95, 30.32))
                val searchSession = searchManager?.submit(
                    newText,
                    point,
                    SearchOptions(),
                    object : Session.SearchListener {
                        override fun onSearchResponse(response: Response) {
                            val cities = response.collection.children.firstOrNull()?.obj
                                ?.metadataContainer
                                ?.getItem(ToponymObjectMetadata::class.java)
                                ?.address
                            val mapObjects: MapObjectCollection? = mapView?.map?.mapObjects
                            mapObjects?.clear()
                            response.collection.children.forEach { searchResult ->
                                val resultLocation: Point? =
                                    searchResult.obj?.geometry?.get(0)?.point
                                if (resultLocation != null) {
                                    mapObjects?.addPlacemark(
                                        resultLocation,
                                        ImageProvider.fromResource(
                                            this@MapActivity,
                                            R.drawable.search_result
                                        )
                                    )
                                }
                            }
                        }

                        override fun onSearchError(p0: com.yandex.runtime.Error) {

                        }
                    }
                )
                return false
            }
        })
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
        MapKitFactory.getInstance().onStop()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
        MapKitFactory.getInstance().onStart()
    }
}