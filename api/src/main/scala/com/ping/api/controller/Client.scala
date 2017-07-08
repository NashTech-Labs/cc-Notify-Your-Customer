package com.ping.api.controller

import akka.http.javadsl.model.HttpEntities
import akka.http.scaladsl.model.StatusCodes.BadRequest
import akka.http.scaladsl.model.{ContentTypes, HttpResponse, StatusCode}
import akka.http.scaladsl.server.Directives.{as, complete, get, path, pathPrefix, post, _}
import akka.http.scaladsl.server.Route
import com.ping.api.directives.{AdminSecurity, Security}
import com.ping.api.services.{ConfigurationService, LogInService}
import com.ping.domain.{ClientRequest, MessageType}
import com.ping.http.PingHttpResponse._
import com.ping.json.JsonHelper
import com.ping.logs.PingLogger

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Try
import scala.util.control.NonFatal

trait Client extends Security with AdminSecurity with PingLogger with JsonHelper {

  val logInService: LogInService
  val configurationService: ConfigurationService

  def clientRoutes = signUpRequestPost ~ getConfigForClient

  def signUpRequestPost: Route = pathPrefix("v1") {
    path("signup") {
      get {
        complete(HttpResponse(BadRequest, entity = "Bad request"))
      } ~
        post {
          entity(as[String]) { data =>
            info(s"Got sign up request with data :: \n${Try(pretty(data)).getOrElse(data)}")
            complete(processSignUpRequest(data))
          }
        }
    }
  }

  private def processSignUpRequest(data: String): Future[HttpResponse] = {
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

  private def getConfigForClient: Route = pathPrefix("v1") {
    path("config" / Segment / Segment) { (channel, clientId) =>
      get {
        securedWithAdminAccess { accessToken =>
          info(s"Got configuration request with access token: $accessToken client id: $clientId and channel: $channel")
          processGetConfigurationRequest(clientId, channel)
        }
      }
    }
  }

  private def processGetConfigurationRequest(clientIdStr: String, channel: String): Future[HttpResponse] = {
    val clientId = Try(clientIdStr.toLong).getOrElse(throw new Exception("client id should be long value"))
    channel match {
      case MessageType.mail => processMailConfigRequest(clientId)
      case MessageType.slack => processSlackConfigRequest(clientId)
      case MessageType.twilio => processTwilioConfigRequest(clientId)
      case _ => Future.successful(HttpResponse(BadRequest, entity = HttpEntities.create(ContentTypes.`application/json`,
        ERROR("Unknown channel type, Only mail, slack, twilio are provided"))))
    }
  } recover {
    case NonFatal(ex) =>
      HttpResponse(BadRequest, entity = HttpEntities.create(ContentTypes.`application/json`, ERROR(ex.getMessage)))
  }

  private def processMailConfigRequest(clientId: Long): Future[HttpResponse] = {
    configurationService.getMailConfig(clientId) map {
      case Some(config) =>
        HttpResponse(StatusCode.int2StatusCode(200), entity = HttpEntities.create(ContentTypes.`application/json`,
          OK(config)))
      case None =>
        HttpResponse(BadRequest, entity = HttpEntities.create(ContentTypes.`application/json`, ERROR("No configuration" +
          s" found for client with id: $clientId")))
    }
  }

  private def processSlackConfigRequest(clientId: Long): Future[HttpResponse] = {
    configurationService.getSlackConfig(clientId) map {
      case Some(config) =>
        HttpResponse(StatusCode.int2StatusCode(200), entity = HttpEntities.create(ContentTypes.`application/json`,
          OK(config)))
      case None =>
        HttpResponse(BadRequest, entity = HttpEntities.create(ContentTypes.`application/json`, ERROR("No configuration" +
          s" found for client with id: $clientId")))
    }
  }

  private def processTwilioConfigRequest(clientId: Long): Future[HttpResponse] = {
    configurationService.getTwilioConfig(clientId) map {
      case Some(config) =>
        HttpResponse(StatusCode.int2StatusCode(200), entity = HttpEntities.create(ContentTypes.`application/json`,
          OK(config)))
      case None =>
        HttpResponse(BadRequest, entity = HttpEntities.create(ContentTypes.`application/json`, ERROR("No configuration" +
          s" found for client with id: $clientId")))
    }
  }


}
