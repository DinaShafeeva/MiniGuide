package com.example.miniguide.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.miniguide.R
import com.example.miniguide.helper.askPermissionsSafely
import com.example.miniguide.ui.base.BaseFragment
import com.example.miniguide.ui.base.Navigator
import com.mapbox.api.directions.v5.DirectionsCriteria
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.api.optimization.v1.MapboxOptimization
import com.mapbox.api.optimization.v1.models.OptimizationResponse
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.LocationComponentOptions
import com.mapbox.mapboxsdk.location.OnCameraTrackingChangedListener
import com.mapbox.mapboxsdk.location.OnLocationClickListener
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.LineLayer
import com.mapbox.mapboxsdk.style.layers.Property
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.*
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import com.mapbox.mapboxsdk.utils.BitmapUtils
import com.mapbox.search.CategorySearchEngine
import com.mapbox.search.SearchRequestTask
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@AndroidEntryPoint
class MapFragment : BaseFragment<MapViewModel>() {
    private val viewModel: MapViewModel by viewModels()

    private lateinit var categorySearchEngine: CategorySearchEngine
    private lateinit var searchRequestTask: SearchRequestTask
    private val ICON_SOURCE_ID = "icon-source-id"
    private val ROUTE_LAYER_ID = "route-layer-id"
    private val ICON_LAYER_ID = "icon-layer-id"

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
    var pointsArray: DoubleArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pointsArray = arguments?.getDoubleArray(Navigator.POINTS_LIST)
        Log.d("pointsArr", pointsArray?.toList().toString())
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
        if (pointsArray != null) {
            makePoints()
            mapView.getMapAsync { mapboxMap ->

                mapboxMap.setStyle(
                    Style.MAPBOX_STREETS
                ) { style ->
                    initSource(style)
                    initLayers(style)

                    createRoute(pointsList)
                }
            }
        }
    }

    private fun initSource(loadedMapStyle: Style) {
        loadedMapStyle.addSource(GeoJsonSource(ROUTE_SOURCE_ID))
        val iconGeoJsonSource = GeoJsonSource(
            ICON_SOURCE_ID, FeatureCollection.fromFeatures(
                arrayOf<Feature>(
                    Feature.fromGeometry(
                        Point.fromLngLat(
                            pointsList[0].longitude(),
                            pointsList[0].latitude()
                        )
                    ),
                    Feature.fromGeometry(
                        Point.fromLngLat(
                            pointsList[pointsList.size - 1].longitude(),
                            pointsList[pointsList.size - 1].latitude()
                        )
                    )
                )
            )
        )
        loadedMapStyle.addSource(iconGeoJsonSource)
    }

    private fun initLayers(loadedMapStyle: Style) {
        val routeLayer = LineLayer(ROUTE_LAYER_ID, ROUTE_SOURCE_ID)

        // Add the LineLayer to the map. This layer will display the directions route.
        routeLayer.setProperties(
            lineCap(Property.LINE_CAP_ROUND),
            lineJoin(Property.LINE_JOIN_ROUND),
            lineWidth(3f),
            lineColor(Color.parseColor("#80C6B5E4"))
        )
        loadedMapStyle.addLayer(routeLayer)

        // Add the red marker icon image to the map
        loadedMapStyle.addImage(
            RED_PIN_ICON_ID, BitmapUtils.getBitmapFromDrawable(
                resources.getDrawable(R.drawable.ic_location)
            )!!
        )

        // Add the red marker icon SymbolLayer to the map
        loadedMapStyle.addLayer(
            SymbolLayer(ICON_LAYER_ID, ICON_SOURCE_ID).withProperties(
                iconImage(RED_PIN_ICON_ID),
                iconIgnorePlacement(true),
                iconAllowOverlap(true),
                iconOffset(arrayOf(0f, -9f))
            )
        )
    }

    private fun initMapView() {
        mapView?.getMapAsync { mapboxMap ->
            this.mapboxMap = mapboxMap
            mapboxMap.setStyle(Style.MAPBOX_STREETS) {
                this.mapboxMap = mapboxMap
                initLocationComponent(mapboxMap, it)
                //  if (pointsArray != null) initRoute()
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

//    override lateinit var viewModel: MapViewModel

    override fun initClickListeners() {
        toRoutesBtn.setOnClickListener {
            view?.findNavController()?.navigate(R.id.routesFragment)
        }
    }

    override fun initViews() {
    }

    override fun subscribe() {
    }

    private var routes: MutableList<DirectionsRoute>? = null
    private var navigationMapRoute: NavigationMapRoute? = null
    private var mapboxMap: MapboxMap? = null
    var pointsList = emptyList<Point>().toMutableList()

    private fun makePoints() {
        pointsList.clear()
        pointsArray?.let { pointsArray ->
            if (pointsArray.isNotEmpty()) {
                pointsList.add(
                    Point.fromLngLat(
                        pointsArray[pointsArray.size - 4],
                        pointsArray[pointsArray.size - 3]
                    )
                )
                var index = 0
                var longitude = 0.0
                pointsArray.forEach {
                    if (index % 2 == 0) {
                        longitude = it
                    } else {
                        if (!pointsList.any { point ->
                            (point.longitude() == longitude) && (point.latitude() == it)
                        }) pointsList.add(Point.fromLngLat(longitude, it))
                    }
                    index++
                }
                pointsList.add(
                    Point.fromLngLat(
                        pointsArray[pointsArray.size - 2],
                        pointsArray[pointsArray.size - 1]
                    )
                )
                pointsList.distinct()
                Log.d("pointsList", pointsList.toString())
            }
        }
    }

    private var currentRoute: DirectionsRoute? = null
    private val ROUTE_SOURCE_ID = "route-source-id"
    private val RED_PIN_ICON_ID = "red-pin-icon-id"

    fun createRoute(points: List<Point>) {
        buildClient(points).enqueueCall(object : Callback<OptimizationResponse> {

            override fun onResponse(
                call: Call<OptimizationResponse>,
                response: Response<OptimizationResponse>
            ) {

                if (response.body() == null) {
                    Log.d(
                        "onResponse",
                        "No routes found, make sure you set the right user and access token."
                    )
                    return
                } else if (response.body()!!.trips()?.isEmpty() == true) {
                    Log.d("onResponse", "No routes found")
                    return
                }

               // currentRoute = response.body()?.routes()?.get(0)
                val optimizedRoute = response.body()!!.trips()!![0]
                if (mapboxMap != null) {
                    if (navigationMapRoute != null) {
                        navigationMapRoute?.removeRoute()
                    } else {
                        navigationMapRoute = NavigationMapRoute(
                            null,
                            mapView,
                            mapboxMap!!,
                            R.style.NavigationMapRoute
                        )
                    }
                    navigationMapRoute?.addRoute(optimizedRoute)
                }
            }

            override fun onFailure(call: Call<OptimizationResponse>, t: Throwable) {
                Log.d("onFailure", t.message.orEmpty())
            }

        })
    }

    private fun buildClient(points: List<Point>): MapboxOptimization {
        val client = MapboxOptimization.builder()
            .source("first")
            .destination("last")
            .coordinates(pointsList)
            .roundTrip(false)
            .steps(true)
            .overview(DirectionsCriteria.OVERVIEW_FULL)
            .profile(DirectionsCriteria.PROFILE_WALKING)
            .accessToken(getString(R.string.mapbox_access_token))

        return client.build()
    }
}