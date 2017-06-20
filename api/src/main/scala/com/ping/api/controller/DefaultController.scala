package com.ping.api.controller


import com.google.inject.Inject
import com.jakehschwartz.finatra.swagger.SwaggerController
import com.ping.models.Partner
import com.twitter.finagle.http.Request
import io.swagger.models.Swagger


class SampleController@Inject()(s: Swagger) extends SwaggerController {
  implicit protected val swagger = s

  getWithDoc("/partner/:id") { id =>
    id.summary("Read the detail information about the partner")
      .tag("Partner")
      .routeParam[String]("id", "the partner id")
      .responseWith[Partner](200, "the partner details")
      .responseWith(404, "the partner not found")
  } { (request: Request) =>
    val PartnerId: Int = request.getParam("id").toInt
    Partner(Some(PartnerId), "partner1", "ABC")
  }

  postWithDoc("/partner") { data =>
    data.summary("Create a new Partner")
      .tag("Partner")
      .bodyParam[Partner]("Partner", "the partner details")
      .responseWith[Partner](200, "the partner details with id")
      .responseWith(404, "the partner not found")
  } { (request: Request) =>
    val PartnerId = math.random().toInt
    Partner(Some(PartnerId), "partner", "ABC")
  }

  putWithDoc("/partner/:id") { id =>
    id.summary("Update a partner")
      .tag("Partner")
      .routeParam[String]("id", "the Partner id")
      .queryParam[String]("name", "the Partner name")
      .queryParam[String]("designation", "the Partner designation")
      .responseWith[Partner](200, "the Partner details with id")
      .responseWith(404, "the Partner not found")
      .responseWith(500, "internal server error")
  } { (request: Request) =>
    val PartnerId: Int = request.getParam("id").toInt
    val name = request.getParam("name").toString
    val designation = request.getParam("designation").toString
    Partner(Some(PartnerId), name, designation)
  }

  deleteWithDoc("/partner/:id") { id =>
    id.summary("Delete a partner")
      .tag("partner")
      .routeParam[String]("id", "the partner id")
      .responseWith[Partner](200, "the partner details with id")
      .responseWith(404, "the partner not found")
  } { (request: Request) =>
    val PartnerId: Int = request.getParam("id").toInt
    val name = request.getParam("name").toString
    val designation = request.getParam("designation").toString
    Partner(Some(PartnerId), name, designation)
  }
}
