package com.example.miniguide.ui

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.miniguide.R
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.*
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.search.*
import com.yandex.runtime.image.ImageProvider
import com.yandex.runtime.network.NetworkError
import com.yandex.runtime.network.RemoteError


class MapActivity : AppCompatActivity(), Session.SearchListener, CameraListener {
    private var mapview: MapView? = null
    private val searchEdit: EditText? = null
    private var searchManager: SearchManager? = null
    private var searchSession: Session? = null

    private fun submitQuery(query: String) {
        searchSession = searchManager?.submit(
            query,
            VisibleRegionUtils.toPolygon(mapview!!.map.visibleRegion),
            SearchOptions(),
            this
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        MapKitFactory.setApiKey("61407ca4-bd81-4965-99a5-fab425b40bd4")
        MapKitFactory.initialize(this)
        SearchFactory.initialize(this)

        setContentView(R.layout.activity_map)
        super.onCreate(savedInstanceState)

        searchManager = SearchFactory.getInstance().createSearchManager(SearchManagerType.COMBINED);

        mapview = findViewById<MapView>(R.id.map_view)
        //mapview?.map?.addCameraListener(this)

        searchEdit?.setOnEditorActionListener(TextView.OnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                submitQuery(searchEdit?.text.toString())
            }
            false
        })
        mapview?.map?.move(
            CameraPosition(Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0F),
            null
        )
        submitQuery(searchEdit?.text.toString())
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

    override fun onSearchError(error: com.yandex.runtime.Error) {
        var errorMessage = getString(R.string.unknown_error_message)
        if (error is RemoteError) {
            errorMessage = getString(R.string.remote_error_message)
        } else if (error is NetworkError) {
            errorMessage = getString(R.string.network_error_message)
        }
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onSearchResponse(response: Response) {
        val mapObjects: MapObjectCollection = mapview?.map?.mapObjects!!
        mapObjects.clear()
        for (searchResult in response.collection.children) {
            val resultLocation =
                searchResult.obj!!.geometry[0].point
            if (resultLocation != null) {
                mapObjects.addPlacemark(
                    resultLocation,
                    ImageProvider.fromResource(this, R.drawable.search_result)
                )
            }
        }
    }

    override fun onCameraPositionChanged(
        map: com.yandex.mapkit.map.Map,
        cameraPosition: CameraPosition,
        cameraUpdateSource: CameraUpdateSource,
        finished: Boolean
    ) {
        if (finished) {
            submitQuery(searchEdit!!.text.toString())
        }
    }
}