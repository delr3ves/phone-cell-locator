package com.emaginalabs.towerlocator.loader

import com.emaginalabs.towerlocator.business.config.CellTowerLocatorConfig
import com.emaginalabs.towerlocator.loader.di.CellTowerLoaderModule
import com.google.inject.{Guice, Injector}
import org.apache.commons.cli._

/**
 * @author Sergio Arroyo - @delr3ves
 */
trait SparkLauncherUtils {

    val LOCAL_ARGUMENT: String = "local"
    val CONFIG_ARGUMENT: String = "config"

    def isLocal(cmdLine: CommandLine): Boolean = {
      cmdLine.hasOption(LOCAL_ARGUMENT)
    }

    def getOptions(): Options = {
      val options: Options = new Options
      options.addOption(LOCAL_ARGUMENT, false, "executes the topology in local mode")
      options.addOption(CONFIG_ARGUMENT, true, "gives the url for config properties")
      options.addOption("h", "help", false, "prints this message")
      addSpecificOptions(options)
    }

    def getCmdLine(args: Array[String]): CommandLine = {
      val parser: CommandLineParser = new BasicParser
      val cmdLine: CommandLine = parser.parse(getOptions, args)
      return cmdLine
    }

    def addSpecificOptions(options: Options): Options = {
      options
    }

    def createInjector(appName: String, isLocal: Boolean, config: CellTowerLocatorConfig): Injector = {
      Guice.createInjector(new CellTowerLoaderModule(appName, isLocal, config))
    }

}
