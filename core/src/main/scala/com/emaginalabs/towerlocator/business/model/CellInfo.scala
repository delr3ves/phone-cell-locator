package com.emaginalabs.towerlocator.business.model

import org.joda.time.DateTime

/**
 * @author Sergio Arroyo - @delr3ves
 */
case class CellInfo(radio: String,
                       mcc: Int,
                       net: Int,
                       area: Int,
                       cell: Int,
                       unit: String,
                       lon: Double,
                       lat: Double,
                       range: Int,
                       samples: Int,
                       changeable: Int,
                       created: DateTime,
                       update: DateTime,
                       averageSignal: Int)
