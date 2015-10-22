package com.emaginalabs.towerlocator.loader.di

import com.emaginalabs.towerlocator.business.config.CellTowerLocatorConfig
import com.google.inject.{Guice, Injector}

/**
 * @author Sergio Arroyo - @delr3ves
 */
object InjectorFactory {

  var injector: Option[Injector] = None

  def getInstance(appName: String, isLocal: Boolean, config: CellTowerLocatorConfig): Injector = {
    if (injector.isEmpty) {
      injector = Some {
        Guice.createInjector(new CellTowerLoaderModule(appName, isLocal, config))
      }
    }
    injector.get
  }

}
