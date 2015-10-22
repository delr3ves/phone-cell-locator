package com.emaginalabs.towerlocator.loader

import com.emaginalabs.towerlocator.business.config.{MongoConfig, CellTowerLocatorConfig}
import org.apache.spark.SparkContext
import org.apache.commons.cli._

/**
 * @author Sergio Arroyo - @delr3ves
 */
object LoadTowers extends SparkLauncherUtils {

  val CELLS_FILE = "cells"

  def main(args: Array[String]) {

    val options: CommandLine = getCmdLine(args)
    val configId = options.getOptionValue(CONFIG_ARGUMENT)
    val config: CellTowerLocatorConfig = new CellTowerLocatorConfig(
      cellsMongoConfig = new MongoConfig(List("localhost:27017"), "cellTower")
    )

    val injector = createInjector("CellTowerLoader", isLocal(options), config)
    val spark = injector getInstance classOf[SparkContext]
    val loader = injector getInstance classOf[CellTowerLoader]
    loader.uploadTowers(options.getOptionValue(CELLS_FILE), spark)

    spark.stop()
  }


  override def addSpecificOptions(options: Options): Options = {
    options.addOption(CELLS_FILE, true, "The file where the cells are defined")
  }
}
