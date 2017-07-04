package com.ping.api.controller

import akka.http.javadsl.model.HttpEntities
import akka.http.scaladsl.model.StatusCodes.BadRequest
import akka.http.scaladsl.model.{ContentTypes, HttpResponse, StatusCode}
import akka.http.scaladsl.server.Directives.{as, complete, get, path, pathPrefix, post, _}
import akka.http.scaladsl.server.Route
import com.ping.api.directives.Security
import com.ping.api.services.PingService
import com.ping.domain.Ping
import com.ping.http.PingHttpResponse.{ERROR, OK}
import com.ping.json.JsonHelper
import com.ping.logs.PingLogger
import com.ping.models.RDClient

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Try

trait PingController extends Security with PingLogger with JsonHelper {

  val pingService: PingService

  def pingRoutes: Route = pingRequestPost

  def pingRequestPost: Route = pathPrefix("v1") {
    path("ping") {
      get {
        complete(HttpResponse(BadRequest, entity = "Bad request"))
      } ~
        post {
          entity(as[String]) { data =>
            secured { client =>
              info(s"Got ping request with data :: \n${Try(pretty(data)).getOrElse(data)}")
              processPings(data, client)
            }
          }
        }
    }
  }

  private def processPings(data: String, client: RDClient): Future[HttpResponse] = {
    parse(data).extractOpt[Ping] match {
      case Some(ping) =>
        info(s"Ping...............$ping")
        pingService.processPing(ping, client) map { response =>
          if (response.mail.isEmpty && response.slack.isEmpty && response.message.isEmpty) {
            HttpResponse(BadRequest, entity = HttpEntities.create(ContentTypes.`application/json`,
              ERROR("You don't have any active channels")))
          } else {
            HttpResponse(StatusCode.int2StatusCode(200), entity = HttpEntities.create(ContentTypes.`application/json`,
              OK(response)))
          }
        }
      case None => Future.successful(HttpResponse(BadRequest, entity = HttpEntities.create(ContentTypes.`application/json`,
        ERROR("Invalid json"))))
    }
  }

}
