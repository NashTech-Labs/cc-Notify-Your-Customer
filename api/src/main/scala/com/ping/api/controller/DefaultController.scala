package com.ping.api.controller


import com.github.xiaodongw.swagger.finatra.SwaggerSupport
import com.twitter.finagle.http.{Request, Response, Status}
import com.twitter.finatra.http.{Controller, _}
import io.swagger.models.Swagger


class DefaultController extends Controller {
  get("/") { request: Request =>
    "Welcome to ping!"
  }
}
/*
object SampleSwagger extends Swagger

class DefaultController extends Controller with SwaggerSupport{

  implicit protected val swagger = SampleSwagger

  getWithDoc("/students/:id") { o =>
    o.summary("Read the detail information about the student")
      .tag("Student")
      .routeParam[String]("id", "the student id")
      .responseWith[Student](200, "the student details")
      .responseWith(404, "the student is not found")
  }{ request: Request =>
    info(s"request found.. ${request}")
    if(0 == "1"){
      Student("1234", "John")
    }else{
      "the student is not found"
    }
  }
}

case class Student(id: String, name: String)*/
