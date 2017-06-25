package com.ping.api.app


import com.ping.api.controller.SampleController
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.routing.HttpRouter


object SampleApp extends HttpServer {

  override def configureHttp(router: HttpRouter) {
    router
      .add[SampleController]
  }

}
