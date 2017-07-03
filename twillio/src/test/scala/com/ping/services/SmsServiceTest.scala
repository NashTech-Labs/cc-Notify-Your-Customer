package com.ping.services

import com.ping.domain.TwilioMessage
import com.ping.infrastructure.twillio.api.{TwilioApi, TwilioPingHttpClient}
import com.ping.models.RDTwilioConfig
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{AsyncWordSpecLike, MustMatchers, WordSpecLike}

import scala.concurrent.Future


class SmsServiceTest extends AsyncWordSpecLike with  MustMatchers with MockitoSugar {

  val mocktwillioApi = mock[TwilioApi]
  val mockTwilioHttpClient = mock[TwilioPingHttpClient]

  object MockSmsServiceTestObject extends SmsService {
    val twillioApi: TwilioApi = mocktwillioApi
    val twilioPingHttpClient: TwilioPingHttpClient = mockTwilioHttpClient
  }

  "Sms Service " should {

    "send notification with sms " in {
      when(mocktwillioApi.send("+13523584605", "+919671701006", "Hello Customer")).thenReturn(true)
      when(mockTwilioHttpClient.getClientConfig("1")).thenReturn(Future.successful(Some(RDTwilioConfig(1, 1, "+919671701006", "abcde", "sid"))))
      val response: Future[Boolean] = MockSmsServiceTestObject.send(TwilioMessage("+919671701006", "Hello Customer", 1L))
      //response mustBe Map("+919671701006" -> true)
      response.map( res =>
        assert(res ==true )
      )
    }

    "not send notification with sms " in {
      when(mocktwillioApi.send("+18442080503", "+919910630243", "+%s@%s.(1/1)### hey ####")).thenReturn(false)
      when(mockTwilioHttpClient.getClientConfig("2")).thenReturn(Future.successful(Some(RDTwilioConfig(2, 2, "+919671701006", "abcde", "sid"))))
      val response = MockSmsServiceTestObject.send(TwilioMessage("+919910630243", "+%s@%s.(1/1)### hey ####", 2L))
      response.map{ res =>
        assert(res ==true )
    }
  }

    "correctly split message in 152 chars" in {
      val longMessage = "abcdefghijklmnopqrstuvwxyz" * 6
      val firstPart = "(1/2)\n" + ("abcdefghijklmnopqrstuvwxyz" * 5) + "abcdefghijklmnopqrstuv"
      val secondPart = "(2/2)\n" + "wxyz"
      when(mockTwilioHttpClient.getClientConfig("3")).thenReturn(Future.successful(Some(RDTwilioConfig(3, 3, "+919671701006", "abcde", "sid"))))
      when(mocktwillioApi.send("+13523584605", "+919671701006", firstPart)).thenReturn(true)
      when(mocktwillioApi.send("+13523584605", "+919671701006", secondPart)).thenReturn(true)
      val response = MockSmsServiceTestObject.send(TwilioMessage("+919671701006", longMessage, 3L))
      response.map{ res =>
        assert(res ==true )
      }
    }

//    "able to get an object of Sms Service" in {
//      assert(SmsServiceImpl.isInstanceOf[SmsService])
//    }

  }

}
