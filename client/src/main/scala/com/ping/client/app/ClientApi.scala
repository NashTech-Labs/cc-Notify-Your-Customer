package com.ping.client.app

import com.ping.client.controller.Client
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.routing.HttpRouter


object ClientApi extends HttpServer {


  override def defaultFinatraHttpPort = ":9000"

  override protected def configureHttp(router: HttpRouter): Unit = {
    router.add[Client]
  }

}
