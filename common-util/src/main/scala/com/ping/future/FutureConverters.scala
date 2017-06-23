package com.ping.future

import com.twitter.bijection.Conversion._
import com.twitter.bijection.twitter_util.UtilBijections.twitter2ScalaFuture
import com.twitter.util.{Future => TwitterFuture}

import scala.concurrent.{ExecutionContext, Future => ScalaFuture}

object FutureConverters {

  implicit class ScalaFutures[T](scalaF: ScalaFuture[T])(implicit ec: ExecutionContext) {
    def toTwitterFuture: TwitterFuture[T] = scalaF.as[TwitterFuture[T]]
  }

}


