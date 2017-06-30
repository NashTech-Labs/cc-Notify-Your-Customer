package com.ping.api.directives

import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes.{BadRequest, Forbidden}
import akka.http.scaladsl.server.Directives.{complete, extractRequestContext}
import akka.http.scaladsl.server.Route
import com.ping.http.PingHttpResponse._
import com.ping.models.RDClient
import com.ping.persistence.repo.ClientRepo
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by girish on 30/6/17.
  */
trait Security {

  val clientRepo: ClientRepo

  def secured(func: (RDClient) => Future[HttpResponse]): Route = {
    extractRequestContext { requestContext =>
      complete {
        requestContext.request.headers.find(header => header.name.toLowerCase.equals("accesstoken")) match {
          case Some(accessToken) => validateAccessToken(accessToken.value, func)
          case None => Future.successful(HttpResponse(BadRequest, entity = ERROR("No access token found")))
        }
      }
    }
  }

  private def validateAccessToken(accessToken: String, func: (RDClient) => Future[HttpResponse]) = {
    clientRepo.getClientByAccessToken(accessToken) map {
      case Some(client) => func(client)
      case None => Future.successful(HttpResponse(Forbidden, entity = "Forbidden"))
    }
  }

}
