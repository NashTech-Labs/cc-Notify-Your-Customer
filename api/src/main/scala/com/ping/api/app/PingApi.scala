package com.ping.api.app


import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.ping.api.controller.{ConfigurationController, DefaultController, LogInController, PingController}
import com.ping.api.services.{ConfigurationService, LogInService, PingService, PingServiceImpl}
import com.ping.kafka.{KafkaProducerApi, Producer}
import akka.http.scaladsl.server.Directives._
import com.ping.config.Configuration._
import com.ping.persistence.repo.ClientRepo


object PingApi extends App with DefaultController with PingController with LogInController with ConfigurationController{

  implicit val system: ActorSystem = ActorSystem("ping-api-routes")
  lazy implicit val executor = system.dispatcher
  lazy implicit val materializer = ActorMaterializer()

  val serverIPs = config.getString("kafka.consumer.servers")

  val pingProducer = new Producer {
    def servers = serverIPs
  }

  val logInService: LogInService = LogInService
  val configurationService: ConfigurationService = ConfigurationService
  val clientRepo: ClientRepo = ClientRepo
  val pingService: PingService = PingServiceImpl(pingProducer)

  val routes = singletoneRoute ~ pingRoutes ~ loginRoutes ~ configRoutes ~ defaultRoutes

  val apiHost = config.getString("ping.api.host")
  val apiPort = config.getInt("ping.api.port")

  val bindFuture = Http().bindAndHandle(routes, apiHost, apiPort)
  warn(s"Ping api has been started on ${apiHost} ${apiPort}")
}
