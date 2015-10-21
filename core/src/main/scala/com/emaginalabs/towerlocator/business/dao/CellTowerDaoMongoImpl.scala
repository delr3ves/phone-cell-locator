package com.emaginalabs.towerlocator.business.dao

import javax.inject.Inject

import com.emaginalabs.towerlocator.business.config.CellTowerLocatorConfig
import com.emaginalabs.towerlocator.business.model.CellInfo
import org.joda.time.DateTime
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson._

import scala.concurrent.Future
import scala.util.{Success, Failure}
import scala.concurrent.ExecutionContext.Implicits.global


/**
 * @author Sergio Arroyo - @delr3ves
 */
class CellTowerDaoMongoImpl @Inject() (
    mongoConnectionManager: MongoConnectionManager,
    config:CellTowerLocatorConfig)
  extends CellTowerDao {

  private val CELL_TOWER_COLLECTION = "celltowers"

  val collection = mongoConnectionManager
    .connect(config.cellsMongoConfig)
    .collection[BSONCollection](CELL_TOWER_COLLECTION)

  implicit object CellInfoWriter extends BSONDocumentWriter[CellInfo] {
    def write(cellInfo: CellInfo): BSONDocument =
      BSONDocument(
        "mcc" -> new BSONInteger(cellInfo.mcc),
        "net" -> new BSONInteger(cellInfo.net),
        "lat" -> new BSONDouble(cellInfo.lat),
        "lon" -> new BSONDouble(cellInfo.lon)
      )
  }


  override def create(cellInfo: CellInfo): Future[CellInfo] = {
    val insertionFuture = collection.insert(cellInfo)
    insertionFuture.map {
      case _ => cellInfo
    }
  }

  override def find(mcc: Integer, net: Integer): Future[Option[CellInfo]] = {
    Future {
      Some {
        new CellInfo("GSM", mcc, net, 20385, 53741, null, 11.0018364,
          50.7931283, 1021, 47, 1,
          new DateTime(1394286585), new DateTime(1408453644), null)
      }
    }
  }
}
