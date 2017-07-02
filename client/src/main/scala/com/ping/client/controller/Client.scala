package com.ping.client.controller

import com.google.inject.Inject
import com.ping.client.service.ClientService
import com.ping.domain.HttpResponse
import com.ping.domain.client.ClientRequest
import com.ping.future.FutureConverters.ScalaFutures
import com.ping.json.JsonHelper
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.util.{Future => TwitterFuture}

import scala.concurrent.ExecutionContext.Implicits.global

class Client @Inject()(val clientService: ClientService) extends Controller with JsonHelper {

  get("/ping") { req: Request =>
    "pong"
  }

  post("/add") { req: Request =>
    val httpBody = req.contentString
    //    val httpQueryParams = req.params.iterator.toList

    parse(httpBody).extractOpt[ClientRequest] match {
      case Some(request) => (clientService.insert(request) map { resp =>
        val httpResp = HttpResponse(code = 200, message = None, data = resp)
        response.ok(write(httpResp)).contentTypeJson()
      }).toTwitterFuture.rescue {
        case ex: IllegalArgumentException => warn("Invalid inputs for client adding......", ex)
          val resp = HttpResponse(code = 422, message = Some(ex.getMessage), None)
          TwitterFuture.value(response.badRequest(write(resp)).contentTypeJson())
        case _ => val resp = HttpResponse(code = 422, message = Some("There was an internal server error"), None)
          TwitterFuture.value(response.internalServerError(write(resp)).contentTypeJson())


      }
      case None => warn("Input json was invalid or malformed..........")
        val resp = HttpResponse(code = 400, message = Some("Bad request, Error found in data"), None)
        TwitterFuture.value(response.badRequest(write(resp)).contentTypeJson())
    }
  }

  get("/:token") { request: Request =>
    (clientService.getClientByAccessToken(request.params("token")) map {
      case Some(resp) => val httpR = HttpResponse(code = 200, data = resp, message = None)
        response.ok(write(httpR)).contentTypeJson()
      case None =>
        val resp = HttpResponse(code = 400, message = None, data = None)
        response.ok(write(resp)).contentTypeJson()
    }).toTwitterFuture.rescue {
      case ex: Throwable =>
        val resp = HttpResponse(code = 500, message = Some("There was in internal server error"), None)
        TwitterFuture.value(response.internalServerError(write(resp)).contentTypeJson())
    }

  }

}
