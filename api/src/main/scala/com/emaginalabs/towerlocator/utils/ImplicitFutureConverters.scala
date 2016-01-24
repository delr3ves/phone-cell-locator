package com.emaginalabs.towerlocator.utils

import com.twitter.{util => twitter}
import scala.concurrent.{ExecutionContext, Promise, Future}
import scala.util.{Failure, Success, Try}
import language.implicitConversions

/**
 * @author Sergio Arroyo - @delr3ves
 */
object ImplicitFutureConverters {

  implicit def scalaToTwitterTry[T](t: Try[T]): twitter.Try[T] = t match {
    case Success(r) => twitter.Return(r)
    case Failure(ex) => twitter.Throw(ex)
  }

  implicit def twitterToScalaTry[T](t: twitter.Try[T]): Try[T] = t match {
    case twitter.Return(r) => Success(r)
    case twitter.Throw(ex) => Failure(ex)
  }

  implicit def scalaToTwitterFuture[T](f: Future[T])(implicit ec: ExecutionContext): twitter.Future[T] = {
    val promise = twitter.Promise[T]()
    f.onComplete(promise update _)
    promise
  }

  implicit def twitterToScalaFuture[T](f: twitter.Future[T]): Future[T] = {
    val promise = Promise[T]()
    f.respond(promise complete _)
    promise.future
  }

}

//
//import com.twitter.util.{Future => TwitterF, Promise => TwitterP}
//import scala.concurrent.{Future => ScalaF, Promise => ScalaP}
//
//object TwitterFutureSupport {
//  /**
//  Implicit conversion from a Twitter Future to a Scala Future
//    **/
//  implicit def twitterFutureToScalaFuture[T](twitterF: TwitterF[T]): ScalaF[T] = {
//    val scalaP = ScalaP[T]
//    twitterF.onSuccess { r: T =>
//      scalaP.success(r)
//    }
//    twitterF.onFailure { e: Throwable =>
//      scalaP.failure(e)
//    }
//    scalaP.future
//  }
//
//}

