package com.example.miniguide.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.miniguide.R
import com.example.miniguide.app.di.Injector
import com.example.miniguide.common.extension.askPermissionsSafely
import com.example.miniguide.ui.routes.adapters.CustomArrayAdapter
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.location.*
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.coroutines.launch


class MapFragment : Fragment() {

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Mapbox.getInstance(requireContext(), getString(R.string.mapbox_access_token))
        mapView?.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestMapPermissions()
//        back_to_camera_tracking_mode.setOnClickListener {
//            view.findNavController().navigate(R.id.action_mapFragment_to_routesFragment)
//        }
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
            .foregroundDrawable(R.drawable.ic_launcher_foreground)
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
                addOnLocationClickListener(onLocationClickListener)
                addOnCameraTrackingChangedListener(onCameraTrackingChangedListener)
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
        Injector.clearRoutesComponent()
        mapView?.onDestroy()
    }
}