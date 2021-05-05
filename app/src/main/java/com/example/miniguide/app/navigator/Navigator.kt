package com.example.miniguide.app.navigator

import android.content.Context
import androidx.navigation.NavController
import com.example.miniguide.R
import com.example.miniguide.ui.routes.router.RoutesRouter

class Navigator : RoutesRouter{

    private var navController: NavController? = null

    fun attachNavController(navController: NavController, graph: Int) {
        navController.setGraph(graph)
        this.navController = navController
    }

    fun detachNavController(navController: NavController) {
        if (this.navController == navController) {
            this.navController = null
        }
    }

    override fun openRoutes() {
        navController?.navigate(R.id.action_mapFragment_to_routesFragment)
    }
}