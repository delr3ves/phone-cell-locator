package com.emaginalabs.towerlocator.loader

import javax.inject.{Inject, Named}

import com.emaginalabs.towerlocator.business.config.CellTowerLocatorConfig
import com.emaginalabs.towerlocator.business.model.CellInfo
import com.emaginalabs.towerlocator.business.service.CellTowerService
import com.emaginalabs.towerlocator.loader.di.InjectorFactory
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.joda.time.DateTime
import org.slf4j.LoggerFactory

/**
 * @author Sergio Arroyo - @delr3ves
 */
class CellTowerLoader @Inject()(config: CellTowerLocatorConfig,
                                @Named("appName") appName: String,
                                @Named("isLocal") isLocal: Boolean)
  extends Serializable {

  private val log = LoggerFactory.getLogger(getClass)
  val HEADER = "radio,mcc,net,area,cell,unit,lon,lat,range,samples,changeable,created,updated,averageSignal"

  def uploadTowers(cellsFile: String, spark: SparkContext) {
    log.info("Loading Cells")
    val linesRDD: RDD[String] = spark.textFile(cellsFile)
    val towersRDD: RDD[CellInfo] = linesRDD
      .filter(!_.contains(HEADER))
      .map(_.split(",").toList)
      .filter(_.size >= 14)
      .map(
        values => new CellInfo(values(0), values(1).toInt, values(2).toInt,
          values(3).toInt, values(4).toInt, values(5), values(6).toDouble,
          values(7).toDouble, values(8).toInt, values(9).toInt, values(10).toInt,
          new DateTime(values(11).toLong), new DateTime(values(12).toLong), values(13).toInt
        )
      )
    towersRDD.cache
    towersRDD.foreach(storeCell)
    val foundTowers = towersRDD.count
    log.info(s"Found... $foundTowers")
  }

  private def storeCell(cell: CellInfo) {
    val injector = InjectorFactory getInstance(appName, isLocal, config)
    val service = injector getInstance (classOf[CellTowerService])
    service createCell (cell)
  }
}
