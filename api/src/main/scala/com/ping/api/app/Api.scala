package com.ping.api.app


import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.ping.api.controller.{ConfigurationController, LogInController, PingController}
import com.ping.api.services.{PingService, PingServiceImpl}
import com.ping.kafka.{KafkaProducerApi, Producer}
import akka.http.scaladsl.server.Directives._


object Api extends App with PingController with LogInController with ConfigurationController{

  implicit val system: ActorSystem = ActorSystem("ping-api-routes")
  lazy implicit val executor = system.dispatcher
  lazy implicit val materializer = ActorMaterializer()

  val pingProducer: KafkaProducerApi = new Producer {
    val servers = "localhost"
  }

  val routes = pingRoutes ~ loginRoutes ~ configRoutes

  val pingService: PingService = PingServiceImpl(pingProducer)

  val bindFuture = Http().bindAndHandle(routes, "localhost", 9001)

}
