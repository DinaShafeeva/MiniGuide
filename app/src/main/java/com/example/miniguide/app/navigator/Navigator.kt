package com.example.miniguide.app.navigator

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.example.miniguide.R
import com.example.miniguide.routes.pointSearch.PointSearchFragment
import com.example.miniguide.routes.pointSearch.model.PointTypeModel
import com.example.miniguide.routes.router.RoutesRouter

class Navigator : RoutesRouter{

    private var navController: NavController? = null
    private var activity: AppCompatActivity? = null

    fun attach(navController: NavController, activity: AppCompatActivity) {
        this.navController = navController
        this.activity = activity
    }

    fun detach() {
        navController = null
        activity = null
    }

    override fun openRoutes() {
        navController?.navigate(R.id.action_global_routesFragment)
    }

    override fun openPointSearch(pointTypeModel: PointTypeModel) {
        val bundle = PointSearchFragment.getBundle(pointTypeModel)

        navController?.navigate(R.id.action_global_pointSearchFragment, bundle)
    }

    override fun back() {
        val popped = navController!!.popBackStack()

        if (!popped) {
            activity!!.finish()
        }
    }
}