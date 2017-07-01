package com.ping.api.controller

import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes.{BadRequest, OK}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.PathMatchers.Segment
import akka.http.scaladsl.server.Route


trait DefaultController {

  val singletoneRoute: Route = pathSingleSlash {
    get {
      complete {
        HttpResponse(OK, entity = "Welcome to Ping api!")
      }
    } ~
      post {
        complete {
          HttpResponse(OK, entity = "Welcome to Ping api!")
        }
      }
  }

  val defaultRoutes: Route = path(Segment) { _ =>
    get {
      complete {
        HttpResponse(BadRequest, entity = "Unknown resource!")
      }
    } ~
      post {
        complete {
          HttpResponse(BadRequest, entity = "Unknown resource!")
        }
      }
  }

}
