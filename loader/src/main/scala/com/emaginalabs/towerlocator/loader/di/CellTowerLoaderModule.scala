package com.emaginalabs.towerlocator.loader.di

import com.emaginalabs.towerlocator.business.config.CellTowerLocatorConfig
import com.emaginalabs.towerlocator.business.di.CellTowerLocatorModule
import com.google.inject.name.Names
import com.google.inject.{Provides, Singleton}
import com.twitter.inject.TwitterModule
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author Sergio Arroyo - @delr3ves
 */
class CellTowerLoaderModule(appName: String, isLocal: Boolean, config: CellTowerLocatorConfig) extends TwitterModule {

  override def configure(): Unit = {
    install(new CellTowerLocatorModule(config))
    bind[String].annotatedWith(Names.named("appName")).toInstance(appName)
    bind[Boolean].annotatedWith(Names.named("isLocal")).toInstance(isLocal)
  }

  @Provides
  @Singleton
  private def sparkContext: SparkContext = {
    val sparkConf = new SparkConf().setAppName(appName)
    if (isLocal) {
      sparkConf.setMaster("local[2]")
    }
    new SparkContext(sparkConf)
  }
}

