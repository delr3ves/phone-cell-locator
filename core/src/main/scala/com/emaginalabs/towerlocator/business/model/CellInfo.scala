package com.emaginalabs.towerlocator.business.model

import org.joda.time.DateTime

/**
 * @author Sergio Arroyo - @delr3ves
 */
case class CellInfo(radio: String,
                       mcc: Integer,
                       net: Integer,
                       area: Integer,
                       cell: Integer,
                       unit: String,
                       lon: Double,
                       lat: Double,
                       range: Integer,
                       samples: Integer,
                       changeable: Integer,
                       created: DateTime,
                       update: DateTime,
                       averageSignal: Integer)
