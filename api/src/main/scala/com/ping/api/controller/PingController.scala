package com.ping.api.controller

import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes.BadRequest
import akka.http.scaladsl.server.Directives.{as, complete, entity, get, path, pathPrefix, post, _}
import akka.http.scaladsl.server.Route
import com.ping.api.services.PingService
import com.ping.json.JsonHelper
import com.ping.logs.PingLogger

import scala.concurrent.Future

trait PingController extends PingLogger with JsonHelper {

  val pingService: PingService

  def pingRoutes = pingRequestPost

  def pingRequestPost: Route = pathPrefix("v1") {
    path("ping") {
      get {
        complete(HttpResponse(BadRequest, entity = "Bad request"))
      } ~
        post {
          entity(as[String]) { data =>
            info(s"Got pricing request with data :: $data")
            complete(processPings(data))
          }
        }
    }
  }


  def processPings(data: String): Future[HttpResponse] = {
//    parse(data)
//    pingService.processPing()
    ???
  }

}
