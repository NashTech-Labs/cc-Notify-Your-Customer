package com.ping.api.controller

import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.ping.api.services.{ConfigurationService, LogInService}
import com.ping.domain.ClientRequest
import com.ping.json.JsonHelper
import com.ping.models.{RDMailConfig, RDSlackConfig, RDTwilioConfig}
import com.ping.persistence.repo.ClientRepo
import org.mockito.Mockito.when
import org.scalatest.Matchers._
import org.scalatest.WordSpecLike
import org.scalatest.mockito.MockitoSugar
import scala.concurrent.Future


class ClientSpec extends Client with WordSpecLike with JsonHelper with MockitoSugar with ScalatestRouteTest {

  private val mockedLogInService = mock[LogInService]
  private val mockedConfigurationService = mock[ConfigurationService]
  private val mockedClientRepo = mock[ClientRepo]

  val logInService: LogInService = mockedLogInService
  val configurationService: ConfigurationService = mockedConfigurationService
  val clientRepo: ClientRepo = mockedClientRepo


  "The Configuration routes" should {

    "return client view for sign-up request" in {
      val clientRequest = ClientRequest("Sachin Tendulkar", "sachin@gmail.com", "Mumbai", "password")
      when(mockedLogInService.processSignUp(clientRequest)).thenReturn(Future.successful(clientRequest
        .getRDClient("token").getClientView))

      Post("/v1/signup", HttpEntity(ContentTypes.`application/json`, write(clientRequest))) ~> clientRoutes ~> check {
        handled shouldBe true
        responseAs[String] shouldEqual """{"code":200,"data":{"name":"Sachin Tendulkar","email":"sachin@gmail.com","address":"Mumbai","accessToken":"token"}}"""
      }
    }

    "return error for bad sign-up request" in {
      val clientRequest = ClientRequest("Sachin Tendulkar", "sachin@gmail.com", "Mumbai", "password")
      when(mockedLogInService.processSignUp(clientRequest)).thenReturn(Future.failed(new Exception("Error found")))

      Post("/v1/signup", HttpEntity(ContentTypes.`application/json`, write(clientRequest))) ~> clientRoutes ~> check {
        handled shouldBe true
        responseAs[String] shouldEqual """{"code":400,"message":"Error found"}"""
      }
    }

    "return config for slack channel" in {
      val clientRequest = ClientRequest("Sachin Tendulkar", "sachin@gmail.com", "Mumbai", "password")
      when(mockedConfigurationService.getSlackConfig(1L)).thenReturn(Future.successful(Some(RDSlackConfig(1L, 1L, "token",
        "defaultChannel"))))

      Get("/v1/config/slack/1", HttpEntity(ContentTypes.`application/json`, write(clientRequest))) ~>
        RawHeader("accessToken", "abcde") ~> clientRoutes ~> check {
        handled shouldBe true
        responseAs[String] shouldEqual """{"code":200,"data":{"id":1,"clientId":1,"token":"token","defaultChannel":"defaultChannel"}}"""
      }
    }

    "return config for mail channel" in {
      val clientRequest = ClientRequest("Sachin Tendulkar", "sachin@gmail.com", "Mumbai", "password")
      when(mockedConfigurationService.getMailConfig(1L)).thenReturn(Future.successful(Some(RDMailConfig(1L, 1L, "email",
        "password"))))

      Get("/v1/config/mail/1", HttpEntity(ContentTypes.`application/json`, write(clientRequest))) ~>
        RawHeader("accessToken", "abcde") ~> clientRoutes ~> check {
        handled shouldBe true
        responseAs[String] shouldEqual """{"code":200,"data":{"id":1,"clientId":1,"email":"email","password":"password"}}"""
      }
    }
  }

  "return config for twilio channel" in {
    when(mockedConfigurationService.getTwilioConfig(1L)).thenReturn(Future.successful(Some(RDTwilioConfig(1L, 1L, "phone_no",
      "token", "sid"))))

    Get("/v1/config/twilio/1") ~>
      RawHeader("accessToken", "abcde") ~> clientRoutes ~> check {
      handled shouldBe true
      responseAs[String] shouldEqual """{"code":200,"data":{"id":1,"clientId":1,"phoneNumber":"phone_no","token":"token","sID":"sid"}}"""
    }
  }

  "return config for twilio channel with error" in {
    when(mockedConfigurationService.getTwilioConfig(2L)).thenReturn(Future.failed(new Exception("Error found")))

    Get("/v1/config/twilio/2") ~>
      RawHeader("accessToken", "abcde") ~> clientRoutes ~> check {
      handled shouldBe true
      responseAs[String] shouldEqual """{"code":400,"message":"Error found"}"""
    }
  }

  "return config for unknown channel" in {
    Get("/v1/config/unknown/1") ~>
      RawHeader("accessToken", "abcde") ~> clientRoutes ~> check {
      handled shouldBe true
      responseAs[String] shouldEqual """{"code":400,"message":"Unknown channel type, Only mail, slack, twilio are provided"}"""
    }
  }


}
