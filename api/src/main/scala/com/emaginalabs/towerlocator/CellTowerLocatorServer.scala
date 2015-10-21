package com.emaginalabs.towerlocator

import com.emaginalabs.towerlocator.business.di.CellTowerLocatorModule
import com.emaginalabs.towerlocator.controller.{FindCellController, UserController}
import com.twitter.finagle.httpx.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.CommonFilters
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.logging.filter.{LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.logging.modules.Slf4jBridgeModule

object CellTowerLocatorServerMain extends CellTowerLocatorServer

class CellTowerLocatorServer extends HttpServer {

  val fileFlag = flag("configFile", "", "Configuration File")

  override def modules = Seq(Slf4jBridgeModule, new CellTowerLocatorModule(fileFlag))

  override def configureHttp(router: HttpRouter) {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .add[FindCellController]
      .add[UserController]
  }

}