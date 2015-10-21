package com.emaginalabs.towerlocator.business.dao

import com.emaginalabs.towerlocator.business.model.CellInfo

import scala.concurrent.Future

/**
 * @author Sergio Arroyo - @delr3ves
 */
trait CellTowerDao {

  def create(cellInfo: CellInfo): Future[CellInfo]

  def find(mcc: Integer, net: Integer): Future[Option[CellInfo]]

}
