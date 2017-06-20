package com.ping.api.controller

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller


class LogInController extends Controller {

  get("/login") { request: Request =>
    "You are log in successfully!!"
  }

}
