package com.example.miniguide.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.miniguide.R
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Geometry
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.search.*
import kotlinx.android.synthetic.main.activity_map.*


class MapActivity : AppCompatActivity() {
    private var mapview: MapView? = null
    private var searchManager: SearchManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        MapKitFactory.setApiKey("61407ca4-bd81-4965-99a5-fab425b40bd4")
        MapKitFactory.initialize(this)
        SearchFactory.initialize(this)

        setContentView(R.layout.activity_map)
        super.onCreate(savedInstanceState)

        searchManager = SearchFactory.getInstance().createSearchManager(SearchManagerType.COMBINED)

        mapview = findViewById<MapView>(R.id.map_view)

        mapview?.map?.move(
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
                        override fun onSearchResponse(p0: Response) {
                            val cities = p0.collection.children.firstOrNull()?.obj
                                ?.metadataContainer
                                ?.getItem(ToponymObjectMetadata::class.java)
                                ?.address
                            Log.d("SearchView", p0.metadata.found.toString())
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
        mapview?.onStop()
        MapKitFactory.getInstance().onStop()
    }

    override fun onStart() {
        super.onStart()
        mapview?.onStart()
        MapKitFactory.getInstance().onStart()
    }
}