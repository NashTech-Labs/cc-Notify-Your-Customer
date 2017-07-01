package com.ping.api.controller

import akka.http.javadsl.model.HttpEntities
import akka.http.scaladsl.model.StatusCodes.BadRequest
import akka.http.scaladsl.model.{ContentTypes, HttpResponse, StatusCode}
import akka.http.scaladsl.server.Directives.{as, complete, get, path, pathPrefix, post, _}
import akka.http.scaladsl.server.Route
import com.ping.api.directives.Security
import com.ping.api.services.LogInService
import com.ping.domain.ClientRequest
import com.ping.http.PingHttpResponse._
import com.ping.json.JsonHelper
import com.ping.logs.PingLogger

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.control.NonFatal

trait LogInController extends Security with PingLogger with JsonHelper {

  val logInService: LogInService

  def loginRoutes = signUpRequestPost

  def signUpRequestPost: Route = pathPrefix("v1") {
    path("signup") {
      get {
        complete(HttpResponse(BadRequest, entity = "Bad request"))
      } ~
        post {
          entity(as[String]) { data =>
            info(s"Got sign up request with data :: $data")
            complete(processSignUpRequest(data))
          }
        }
    }
  }

  def processSignUpRequest(data: String): Future[HttpResponse] = {
    parse(data).extractOpt[ClientRequest] match {
      case Some(clientRequest) =>
        logInService.processSignUp(clientRequest).map { clientView =>
          HttpResponse(StatusCode.int2StatusCode(200), entity = HttpEntities.create(ContentTypes.`application/json`,
            OK(clientView)))
        } recover {
          case NonFatal(ex: Exception) => handleExceptions(ex)
        }
      case None => Future.successful(HttpResponse(BadRequest, entity = HttpEntities.create(ContentTypes.`application/json`,
        ERROR("Invalid json"))))
    }
  }

  private def handleExceptions(ex: Exception): HttpResponse = ex match {
    case ex: Exception =>
      HttpResponse(BadRequest, entity = HttpEntities.create(ContentTypes.`application/json`, ERROR(ex.getMessage)))
  }

}
