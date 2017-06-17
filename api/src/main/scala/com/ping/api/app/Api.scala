package com.ping.api.app


import com.github.xiaodongw.swagger.finatra.{SwaggerController, SwaggerSupport, WebjarsController}
import com.ping.api.controller.{DefaultController, LogInController}
//import com.ping.api.controller.{DefaultController, LogInController, SampleSwagger}
import com.twitter.finagle.http.Request
import com.twitter.finatra.http._
import com.twitter.finatra.http.filters.CommonFilters
import com.twitter.finatra.http.routing.HttpRouter
import io.swagger.models.{Info, Swagger}


/*case class Student(name: String)

object SampleSwagger extends Swagger

class SampleController extends Controller with SwaggerSupport {
  implicit protected val swagger = SampleSwagger

  getWithDoc("/students/:id") { o =>
    o.summary("Read the detail information about the student")
      .tag("Student")
      .routeParam[String]("id", "the student id")
      .responseWith[Student](200, "the student details")
      .responseWith(404, "the student is not found")
  } { (request: Request) =>
    "Hello"
  }
}

object SampleApp extends HttpServer {
  val info = new Info()
    .description("The Student / Course management API, this is a sample for swagger document generation")
    .version("1.0.1")
    .title("Student / Course Management API")
  SampleSwagger.info(info)

  override def configureHttp(router: HttpRouter) {
    router
      .add(new SwaggerController(swagger = SampleSwagger))
  }
}*/


//Application
object HelloServer extends HttpServer {

  override protected def configureHttp(router: HttpRouter): Unit = {
    router
//      .filter[LoggingMDCFilter[Request, Response]]
//      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .add[LogInController]
      .add[DefaultController]
  }
}
