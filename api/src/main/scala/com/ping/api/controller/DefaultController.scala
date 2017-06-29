package com.ping.api.controller


import com.ping.models.Partner
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller


class SampleController extends Controller {

  get("/partner/:id") { (request: Request) =>
    val PartnerId: Int = request.getParam("id").toInt
    Partner(Some(PartnerId), "partner1", "ABC")
  }

  post("/partner") { (request: Request) =>
    val PartnerId = math.random.toInt
    Partner(Some(PartnerId), "partner", "ABC")
  }

  put("/partner/:id") { (request: Request) =>
    val PartnerId: Int = request.getParam("id").toInt
    val name = request.getParam("name").toString
    val designation = request.getParam("designation").toString
    Partner(Some(PartnerId), name, designation)
  }

  delete("/partner/:id") { (request: Request) =>
    val PartnerId: Int = request.getParam("id").toInt
    val name = request.getParam("name").toString
    val designation = request.getParam("designation").toString
    Partner(Some(PartnerId), name, designation)
  }

}
