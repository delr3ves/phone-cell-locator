package com.emaginalabs.towerlocator.business.service

import javax.inject.Inject

import com.emaginalabs.towerlocator.business.dao.CellTowerDao
import com.emaginalabs.towerlocator.business.model.CellInfo

import scala.concurrent.Future

/**
 * @author Sergio Arroyo - @delr3ves
 */
class CellTowerServiceImpl @Inject()(cellTowerDao: CellTowerDao) extends CellTowerService {

  override def createCell(cell: CellInfo): Future[CellInfo] = {
    cellTowerDao.create(cell)
  }

  override def findCell(mcc: Integer, net: Integer): Future[Option[CellInfo]] = {
    cellTowerDao.find(mcc: Integer, net: Integer)
  }
}
