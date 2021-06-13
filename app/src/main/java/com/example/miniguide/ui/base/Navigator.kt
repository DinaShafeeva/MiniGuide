package com.example.miniguide.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.example.miniguide.R
import com.example.miniguide.data.PointTypeModel
import com.example.miniguide.ui.search.SearchFragment
import com.mapbox.search.result.SearchResult

class Navigator {

    private var navController: NavController? = null
    private var activity: AppCompatActivity? = null

    fun attach(navController: NavController, activity: AppCompatActivity) {
        this.navController = navController
        this.activity = activity
    }

    fun openRoutes() {
        navController?.navigate(R.id.routesFragment)
    }

    fun openPointSearch(pointTypeModel: PointTypeModel) {
        val bundle = SearchFragment.getBundle(pointTypeModel)

        navController?.navigate(R.id.searchFragment, bundle)
    }

    fun openMap(array: DoubleArray) {
        val bundle = Bundle()
        bundle.putDoubleArray(POINTS_LIST, array)
        navController?.navigate(R.id.mapFragment, bundle)
    }

    fun back() {
        val popped = navController!!.popBackStack()

        if (!popped) {
            activity!!.finish()
        }
    }

    fun openRoutesChoose() {
        //navController?.navigate(R.id.action_global_routesFragment)
    }

    companion object {
        const val POINTS_LIST = "points_list"
    }
}