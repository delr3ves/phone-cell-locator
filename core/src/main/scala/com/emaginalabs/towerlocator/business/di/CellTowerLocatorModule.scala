package com.emaginalabs.towerlocator.business.di

import com.emaginalabs.towerlocator.business.config.{MongoConfig, CellTowerLocatorConfig}
import com.emaginalabs.towerlocator.business.dao.{MongoConnectionManager, CellTowerDao, CellTowerDaoMongoImpl}
import com.emaginalabs.towerlocator.business.service.{CellTowerService, CellTowerServiceImpl}
import com.google.inject.Provides
import com.twitter.app.Flag
import com.twitter.inject.TwitterModule
import com.twitter.inject.requestscope.RequestScopeBinding
import reactivemongo.api.MongoDriver

/**
 * @author Sergio Arroyo - @delr3ves
 */
class CellTowerLocatorModule(configId: Flag[String]) extends TwitterModule with RequestScopeBinding {

  protected override def configure(): Unit = {
    bind[CellTowerService].to[CellTowerServiceImpl]
    bind[CellTowerDao].to[CellTowerDaoMongoImpl]
  }

  @Provides
  protected def getMongoDriver(): MongoDriver = {
    new MongoDriver
  }

  @Provides
  protected def loadConfig(): CellTowerLocatorConfig = {
    new CellTowerLocatorConfig(
      cellsMongoConfig = new MongoConfig(List("localhost:27017"), "cellTower")
    )
  }
}
