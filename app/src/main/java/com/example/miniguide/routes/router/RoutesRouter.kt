package com.example.miniguide.routes.router

import com.example.miniguide.routes.presentation.pointSearch.model.PointTypeModel

interface RoutesRouter {

    fun openRoutes()

    fun openPointSearch(pointTypeModel: PointTypeModel)

    fun back()
}