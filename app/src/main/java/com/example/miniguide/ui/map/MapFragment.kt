package com.example.miniguide.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.miniguide.R
import com.example.miniguide.helper.askPermissionsSafely
import com.example.miniguide.ui.base.BaseFragment
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.location.*
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.search.*
import com.mapbox.search.result.SearchResult
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.coroutines.launch


class MapFragment : BaseFragment<MapViewModel>() {

    private lateinit var categorySearchEngine: CategorySearchEngine
    private lateinit var searchRequestTask: SearchRequestTask

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        viewModel = viewModel {  }
//    }

    private val onLocationClickListener = OnLocationClickListener {
        Log.e("Callback - ", " OnLocationClickListener")
    }

    private val onCameraTrackingChangedListener = object : OnCameraTrackingChangedListener {
        override fun onCameraTrackingDismissed() {
            Log.e("Callback - ", " onCameraTrackingChangedListener")

        }

        override fun onCameraTrackingChanged(currentMode: Int) {
            Log.e("Callback - ", " onCameraTrackingChanged")
        }

    }

    private val searchCallback: SearchCallback = object : SearchCallback {

        override fun onResults(results: List<SearchResult>, responseInfo: ResponseInfo) {
            if (results.isEmpty()) {
                Log.i("SearchApiExample", "No category search results")
            } else {
                Log.i("SearchApiExample", "Category search results: $results")
            }
        }

        override fun onError(e: Exception) {
            Log.i("SearchApiExample", "Search error", e)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Mapbox.getInstance(requireContext(), getString(R.string.mapbox_access_token))
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestMapPermissions()
        mapView?.onCreate(savedInstanceState)
        categorySearchEngine = MapboxSearchSdk.createCategorySearchEngine()

        searchRequestTask = categorySearchEngine.search(
            "cafe",
            CategorySearchOptions(limit = 3, proximity = Point.fromLngLat(-122.08400000000002, 37.421998333333335)),
            searchCallback
        )
    }

    private fun initMapView() {
        mapView?.getMapAsync { mapboxMap ->
            mapboxMap.setStyle(Style.MAPBOX_STREETS) {
                initLocationComponent(mapboxMap, it)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun initLocationComponent(mapboxMap: MapboxMap, style: Style) {
        val customLocationComponentOptions = LocationComponentOptions.builder(requireContext())
            .elevation(5f)
            .accuracyAlpha(.6f)
            .accuracyColor(Color.RED)
            .foregroundDrawable(R.drawable.ic_location)
            .backgroundDrawable(R.drawable.ic_location)
            .build()

        mapboxMap.apply {

            val locationComponentActivationOptions =
                LocationComponentActivationOptions.builder(requireContext(), style)
                    .locationComponentOptions(customLocationComponentOptions)
                    .build()

            // Activate with options
            locationComponent.activateLocationComponent(locationComponentActivationOptions)

            // Enable to make component visible
            locationComponent.isLocationComponentEnabled = true

            // Set the component's camera mode
            locationComponent.cameraMode = CameraMode.TRACKING

            // Set the component's render mode
            locationComponent.renderMode = RenderMode.COMPASS

            // Add the location icon click listener
            locationComponent.addOnLocationClickListener(onLocationClickListener)

            // Add the camera tracking listener. Fires if the map camera is manually moved.
            locationComponent.addOnCameraTrackingChangedListener(onCameraTrackingChangedListener)

        }
    }

    private fun requestMapPermissions() {
        viewLifecycleOwner.lifecycleScope.launch {
            val result = askPermissionsSafely(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )

            if (result.isSuccess) {
                initMapView()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override lateinit var viewModel: MapViewModel

    override fun initClickListeners() {
        toRoutesBtn.setOnClickListener{
            view?.findNavController()?.navigate(R.id.routesFragment)
        }
    }

    override fun initViews() {
    }

    override fun subscribe() {
    }
}