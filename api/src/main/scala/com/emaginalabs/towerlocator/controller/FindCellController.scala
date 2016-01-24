package com.emaginalabs.towerlocator.controller

import javax.inject.Inject

import com.emaginalabs.towerlocator.business.model.CellInfo
import com.emaginalabs.towerlocator.business.service.CellTowerService
import com.twitter.finagle.httpx.Request
import com.twitter.finatra.http.Controller

import com.emaginalabs.towerlocator.utils.ImplicitFutureConverters
import scala.concurrent.ExecutionContext.Implicits.global

class FindCellController @Inject()(cellTowerService: CellTowerService) extends Controller {

  get("/tower/:mcc/:net") { request: Request =>
    val mcc = request.params.getIntOrElse("mcc", 0)
    val net = request.params.getIntOrElse("net", 0)

    ImplicitFutureConverters.scalaToTwitterFuture(cellTowerService.findCell(mcc, net))
  }

  post("/tower") { cellInfo: CellInfo =>
    ImplicitFutureConverters.scalaToTwitterFuture(cellTowerService.createCell(cellInfo))
  }
}
