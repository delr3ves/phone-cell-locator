package com.emaginalabs.towerlocator.business.dao

import javax.inject.Inject

import com.emaginalabs.towerlocator.business.config.CellTowerLocatorConfig
import com.emaginalabs.towerlocator.business.model.CellInfo
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


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
        "radio" -> new BSONString(cellInfo.radio),
        "area" -> cellInfo.area,
        "cell" -> cellInfo.cell,
        "unit" -> cellInfo.unit,
        "samples" -> cellInfo.samples,
        "changeable" -> cellInfo.changeable,
        "averageSignal" -> cellInfo.averageSignal,
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
    val query = BSONDocument(
      "mcc" -> BSONInteger(mcc),
      "net" -> BSONInteger(net)
    )

    val futureFoundCell = collection.find(query).cursor[BSONDocument].
      collect[List](1)
    futureFoundCell.map {
      case Nil => None
      case cells => Some(CellInfoReader.read(cells(0)))
    }
  }

  implicit object CellInfoReader extends BSONDocumentReader[CellInfo] {
    def read(doc: BSONDocument): CellInfo = {
      CellInfo(
        doc.getAs[String]("radio").getOrElse[String](null),
        doc.getAs[Int]("mcc").getOrElse(null.asInstanceOf[Int]),
        doc.getAs[Int]("net").getOrElse(null.asInstanceOf[Int]),
        doc.getAs[Int]("area").getOrElse(null.asInstanceOf[Int]),
        doc.getAs[Int]("cell").getOrElse(null.asInstanceOf[Int]),
        doc.getAs[String]("unit").getOrElse(null),
        doc.getAs[Double]("lon").getOrElse(null.asInstanceOf[Double]),
        doc.getAs[Double]("lat").getOrElse(null.asInstanceOf[Double]),
        doc.getAs[Int]("range").getOrElse(null.asInstanceOf[Int]),
        doc.getAs[Int]("samples").getOrElse(null.asInstanceOf[Int]),
        doc.getAs[Int]("chabgeable").getOrElse(null.asInstanceOf[Int]),
//        doc.getAs[DateTime]("created").getOrElse(null),
//        doc.getAs[DateTime]("update").getOrElse(null),
        new DateTime,
        new DateTime,
        doc.getAs[Int]("averageSignal").getOrElse(null.asInstanceOf[Int])
      )
    }
  }

  implicit object BSONDateTimeHandler extends BSONHandler[BSONDateTime, DateTime] {
    val fmt = ISODateTimeFormat.dateTime()
    def read(time: BSONDateTime) = new DateTime(time.value)
    def write(jdtime: DateTime) = BSONDateTime(jdtime.getMillis)
  }
}

