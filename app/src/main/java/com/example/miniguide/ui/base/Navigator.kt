package com.example.miniguide.ui.base

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.example.miniguide.R
import com.example.miniguide.data.PointTypeModel
import com.example.miniguide.ui.search.SearchFragment

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

    fun back() {
        val popped = navController!!.popBackStack()

        if (!popped) {
            activity!!.finish()
        }
    }

    fun openRoutesChoose() {
        //navController?.navigate(R.id.action_global_routesFragment)
    }
}