package com.ping.future

import com.twitter.util.{Future => TwitterFuture, Promise => TwitterPromise}

import scala.concurrent.{ExecutionContext, Future => ScalaFuture}
import scala.util.{Failure, Success}

object FutureConverters {

  implicit class ScalaFutures[T](scalaF: ScalaFuture[T])(implicit ec: ExecutionContext) {

    def toTwitterFuture: TwitterFuture[T] = {
      val twitterP = TwitterPromise[T]()
      scalaF.onComplete {
        case Success(resp) => twitterP.setValue(resp)
        case Failure(ex) => twitterP.setException(ex)
      }
      twitterP
    }

  }

  /*implicit def twitterFutureToScalaFuture[T](twitterF: TwitterFuture[T]): ScalaFuture[T] = {
    val scalaP = ScalaPromise[T]
    twitterF.onSuccess { resp =>
      scalaP.success(resp)
    }
    twitterF.onFailure { ex: Throwable =>
      scalaP.failure(ex)
    }
    scalaP.future
  }

  implicit def scalaToTwitterFuture[T](scalaF: ScalaFuture[T])(implicit e: ExecutionContext): TwitterFuture[T] = {
    val twitterP = TwitterPromise[T]()
    scalaF.onComplete {
      case Success(resp) => twitterP.setValue(resp)
      case Failure(ex) => twitterP.setException(ex)
    }
    twitterP
  }*/

}


