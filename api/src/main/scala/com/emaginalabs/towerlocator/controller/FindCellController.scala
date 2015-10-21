package com.emaginalabs.towerlocator.controller

import javax.inject.Inject

import com.emaginalabs.towerlocator.business.model.CellInfo
import com.emaginalabs.towerlocator.business.service.CellTowerService
import com.twitter.finagle.httpx.Request
import com.twitter.finatra.http.Controller

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class FindCellController @Inject()(cellTowerService: CellTowerService) extends Controller {

  get("/tower/:mcc/:net") { request: Request =>
    val mcc = request.params.getIntOrElse("mcc", 0)
    val net = request.params.getIntOrElse("net", 0)

    val futureCell: Future[Option[CellInfo]] = cellTowerService.findCell(mcc, net)

    futureCell.map {
      case Some(cell) => response.ok.json(cell)
      case None => response.notFound()
    }
  }

  post("/tower") { cellInfo: CellInfo =>
    val futureCell: Future[CellInfo] = cellTowerService.createCell(cellInfo)

    futureCell.map {
      case _ => response.ok.json(_)
    }
  }
}
