package com.emaginalabs.towerlocator.business.dao

import javax.inject.Inject

import com.emaginalabs.towerlocator.business.config.MongoConfig
import reactivemongo.api._
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * @author Sergio Arroyo - @delr3ves
 */
class MongoConnectionManager @Inject() (driver: MongoDriver) {

  def connect(connectionConfig: MongoConfig): DefaultDB = {
    val connection = driver.connection(connectionConfig.urls)
    connection(connectionConfig.database)
    //val collection = db(collection)
  }
}
