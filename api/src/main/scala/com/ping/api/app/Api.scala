package com.ping.api.app


import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.ping.api.controller.PingController
import com.ping.api.services.PingServiceImpl

object Api extends App with PingController{

  implicit val system: ActorSystem = ActorSystem("ping-api-routes")
  lazy implicit val executor = system.dispatcher
  lazy implicit val materializer = ActorMaterializer()

  val pingService = PingServiceImpl

  val bindFuture = Http().bindAndHandle(pingRoutes, "localhost", 9001)

}
