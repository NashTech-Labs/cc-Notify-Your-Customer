package com.ping.api.directives

import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes.{BadRequest, Forbidden}
import akka.http.scaladsl.server.Directives.{complete, extractRequestContext}
import akka.http.scaladsl.server.Route
import com.ping.config.Configuration
import com.ping.http.PingHttpResponse.ERROR
import com.ping.models.RDClient

import scala.concurrent.Future


trait AdminSecurity {

  def securedWithAdminAccess(func: (String) => Future[HttpResponse]): Route = {
    extractRequestContext { requestContext =>
      complete {
        requestContext.request.headers.find(header => header.name.toLowerCase.equals("accesstoken")) match {
          case Some(accessToken) => validateAccessToken(accessToken.value, func)
          case None => Future.successful(HttpResponse(BadRequest, entity = ERROR("No access token found")))
        }
      }
    }
  }

  private def validateAccessToken(accessToken: String, func: (String) => Future[HttpResponse]) = {
    val adminToken: String = Configuration.config.getString("ping.api.admin.token")
    if(accessToken == adminToken){
      func(accessToken)
    }else{
      Future.successful(HttpResponse(Forbidden, entity = "Unauthorized"))
    }
  }
}
