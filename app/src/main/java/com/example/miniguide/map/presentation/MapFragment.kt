package com.example.miniguide.map.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.miniguide.R
import com.example.miniguide.app.di.Injector
import com.example.miniguide.common.base.BaseFragment
import com.example.miniguide.common.extension.askPermissionsSafely
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.location.*
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.coroutines.launch


class MapFragment : BaseFragment<MapViewModel>() {

    private var navigationMapRoute: NavigationMapRoute? = null
    private var mapboxMap: MapboxMap? = null
    private var routes: List<DirectionsRoute>? = null
    override var isResetBtnVisible: Boolean = false

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
        mapView?.onCreate(savedInstanceState)
    }

    override fun subscribe() {
        viewModel.currentRoute.observe { routes = it }
    }

    override fun inject() {
        Injector.plusMapComponent(this).inject(this)
    }

    override fun initViews() {
        requestMapPermissions()
    }

    override fun initClickListeners() {
        back_to_camera_tracking_mode.setOnClickListener {
            viewModel.onRoutesClick()
        }
    }

    private fun initMapView() {
        mapView?.getMapAsync { mapboxMap ->
            mapboxMap.setStyle(Style.MAPBOX_STREETS) {
                initLocationComponent(mapboxMap, it)
                this.mapboxMap = mapboxMap
                initRoute()
            }
        }

    }

    private fun initRoute() {
        routes?.let {
            if (navigationMapRoute != null) {
                navigationMapRoute?.removeRoute()
            } else {
                mapboxMap?.let {
                    navigationMapRoute =
                        NavigationMapRoute(null, mapView, it, R.style.NavigationMapRoute)
                }
            }
            navigationMapRoute?.addRoutes(it)
        }
    }

    @SuppressLint("MissingPermission")
    private fun initLocationComponent(mapboxMap: MapboxMap, style: Style) {
        val customLocationComponentOptions = LocationComponentOptions.builder(requireContext())
            .elevation(5f)
            .foregroundDrawable(R.drawable.ic_marker)
            .backgroundDrawable(R.drawable.ic_marker)
            .accuracyAlpha(.6f)
            .accuracyColor(Color.RED)
            .build()

        mapboxMap.apply {

            val locationComponentActivationOptions =
                LocationComponentActivationOptions.builder(requireContext(), style)
                    .locationComponentOptions(customLocationComponentOptions)
                    .build()

            locationComponent.apply {
                activateLocationComponent(locationComponentActivationOptions)
                isLocationComponentEnabled = true
                cameraMode = CameraMode.TRACKING
                renderMode = RenderMode.COMPASS
            }
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
}