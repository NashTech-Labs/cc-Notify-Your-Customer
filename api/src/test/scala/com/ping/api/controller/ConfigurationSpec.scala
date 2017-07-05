package com.ping.api.controller

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.ping.json.JsonHelper
import com.ping.logger.PingLogger
import org.mockito.Matchers
import org.scalatest.WordSpec
import org.scalatest.mockito.MockitoSugar

/**
  * Created by girish on 5/7/17.
  *//*
class ConfigurationSpec extends WordSpec with JsonHelper with MockitoSugar with Matchers with ScalatestRouteTest
  with PingLogger{

  "The Configuration routes" should {

    "return success response for client" in {
      Post("/v1/sync/trip", HttpEntity(ContentTypes.`application/json`, """{}""")) ~>
//        addCredentials(AccessTokenCredentials) ~> routes ~> check {
//        handled shouldBe true
//        responseAs[String] shouldEqual """{"code":400,"message":"Bad request, Please try with get call"}"""
      }
    }

}*/
