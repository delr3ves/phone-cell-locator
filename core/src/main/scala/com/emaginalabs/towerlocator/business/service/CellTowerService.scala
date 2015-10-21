package com.emaginalabs.towerlocator.business.service

import com.emaginalabs.towerlocator.business.model.CellInfo

import scala.concurrent.Future

/**
 * @author Sergio Arroyo - @delr3ves
 */
trait CellTowerService {

  def createCell(cell: CellInfo): Future[CellInfo]

  def findCell(mcc: Integer, net: Integer): Future[Option[CellInfo]]
}
