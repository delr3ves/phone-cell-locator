package com.emaginalabs.towerlocator.business.di

import com.emaginalabs.towerlocator.business.config.{CellTowerLocatorConfig, MongoConfig}
import com.emaginalabs.towerlocator.business.dao.{CellTowerDao, CellTowerDaoMongoImpl}
import com.emaginalabs.towerlocator.business.service.{CellTowerService, CellTowerServiceImpl}
import com.google.inject.{AbstractModule, Provides}
import reactivemongo.api.MongoDriver

/**
 * @author Sergio Arroyo - @delr3ves
 */
class CellTowerLocatorModule(cellTowerLocatorConfig: CellTowerLocatorConfig) extends AbstractModule {

  protected override def configure(): Unit = {
    bind(classOf[CellTowerService]).to(classOf[CellTowerServiceImpl])
    bind(classOf[CellTowerDao]).to(classOf[CellTowerDaoMongoImpl])
    bind(classOf[CellTowerLocatorConfig]).toInstance(cellTowerLocatorConfig)
  }

  @Provides
  protected def getMongoDriver(): MongoDriver = {
    new MongoDriver
  }

}
