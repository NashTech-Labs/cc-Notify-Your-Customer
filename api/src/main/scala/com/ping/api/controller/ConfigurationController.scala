package com.ping.api.controller

import akka.http.javadsl.model.HttpEntities
import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model.{ContentTypes, HttpResponse, StatusCode}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.ping.api.directives.Security
import com.ping.api.services.ConfigurationService
import com.ping.domain.ConfigRequest
import com.ping.http.PingHttpResponse.{ERROR, OK}
import com.ping.json.JsonHelper
import com.ping.logs.PingLogger
import com.ping.models.RDClient

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.control.NonFatal

/**
  * Created by girish on 1/7/17.
  */
trait ConfigurationController extends Security with PingLogger with JsonHelper {

  val configurationService: ConfigurationService

  def configRoutes: Route = configRequestPost

  def configRequestPost: Route = pathPrefix("v1") {
    path("config") {
      get {
        secured { client =>
          info(s"Got config get request ::")
          processConfigGetRequest(client)
        }
      } ~
        post {
          entity(as[String]) { data =>
            secured { client =>
              info(s"Got config post request with data :: $data")
              processConfigPostRequest(data, client)
            }
          }
        } ~
        put {
          entity(as[String]) { data =>
            secured { client =>
              info(s"Got config put request with data :: $data")
              processConfigPutRequest(data, client)
            }
          }
        } ~
        delete {
          secured { client =>
            info(s"Got config delete request ")
            processConfigDeleteRequest(client)
          }
        }
    }
  }

  private def processConfigPostRequest(data: String, client: RDClient): Future[HttpResponse] = {
    parse(data).extractOpt[ConfigRequest] match {
      case Some(configRequest) =>
        configurationService.createConfig(configRequest, client).map { configResponse =>
          HttpResponse(StatusCode.int2StatusCode(200), entity = HttpEntities.create(ContentTypes.`application/json`,
            OK(configResponse)))
        } recover {
          case NonFatal(ex: Exception) => handleExceptions(ex)
        }
      case None => Future.successful(HttpResponse(BadRequest, entity = HttpEntities.create(ContentTypes.`application/json`,
        ERROR("Invalid json"))))
    }

  }

  private def processConfigPutRequest(data: String, client: RDClient): Future[HttpResponse] = {
    parse(data).extractOpt[ConfigRequest] match {
      case Some(configRequest) =>
        configurationService.createConfig(configRequest, client).map { configResponse =>
          HttpResponse(StatusCode.int2StatusCode(200), entity = HttpEntities.create(ContentTypes.`application/json`,
            OK(configResponse)))
        } recover {
          case NonFatal(ex: Exception) => handleExceptions(ex)
        }
      case None => Future.successful(HttpResponse(BadRequest, entity = HttpEntities.create(ContentTypes.`application/json`,
        ERROR("Invalid json"))))
    }
  }

  private def processConfigDeleteRequest(client: RDClient): Future[HttpResponse] = {
    configurationService.deleteConfig(client).map { configResponse =>
      HttpResponse(StatusCode.int2StatusCode(200), entity = HttpEntities.create(ContentTypes.`application/json`,
        OK(configResponse)))
    } recover {
      case NonFatal(ex: Exception) => handleExceptions(ex)
    }
  }

  private def processConfigGetRequest(client: RDClient): Future[HttpResponse] = {
    configurationService.getConfigStatus(client).map { configResponse =>
      HttpResponse(StatusCode.int2StatusCode(200), entity = HttpEntities.create(ContentTypes.`application/json`,
        OK(configResponse)))
    } recover {
      case NonFatal(ex: Exception) => handleExceptions(ex)
    }
  }

  private def handleExceptions(ex: Exception): HttpResponse = ex match {
    case ex: Exception =>
      HttpResponse(BadRequest, entity = HttpEntities.create(ContentTypes.`application/json`, ERROR(ex.getMessage)))
  }

}
