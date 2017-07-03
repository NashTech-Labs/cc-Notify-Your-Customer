package com.ping.services

import com.ping.infrastructure.twillio.api.TwilioApi
import com.ping.models.SmsDetail
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{MustMatchers, WordSpecLike}


class SmsServiceTest extends WordSpecLike with MustMatchers with MockitoSugar {

  val mocktwillioApi = mock[TwilioApi]


  object MockSmsServiceTestObject extends SmsService {
    val twillioApi: TwilioApi = mocktwillioApi
  }

  "Sms Service " should {

    "send notification with sms " in {
      when(mocktwillioApi.send("+13523584605", "+919671701006", "Hello Customer")).thenReturn(true)
      val response = MockSmsServiceTestObject.send(SmsDetail("1", List("+919671701006"), "Hello Customer"))
      response mustBe Map("+919671701006" -> true)
    }

    "not send notification with sms " in {
      when(mocktwillioApi.send("+18442080503", "+919910630243", "+%s@%s.(1/1)### hey ####")).thenReturn(false)
      val response = MockSmsServiceTestObject.send(SmsDetail("2", List("+919910630243"), "+%s@%s.(1/1)### hey ####"))
      response mustBe Map("+919910630243" -> false)
    }

    "correctly split message in 152 chars" in {
      val longMessage = "abcdefghijklmnopqrstuvwxyz" * 6
      val firstPart = "(1/2)\n" + ("abcdefghijklmnopqrstuvwxyz" * 5) + "abcdefghijklmnopqrstuv"
      val secondPart = "(2/2)\n" + "wxyz"
      when(mocktwillioApi.send("+13523584605", "+919671701006", firstPart)).thenReturn(true)
      when(mocktwillioApi.send("+13523584605", "+919671701006", secondPart)).thenReturn(true)
      val response = MockSmsServiceTestObject.send(SmsDetail("3", List("+919671701006"), longMessage))
      response mustBe Map("+919671701006" -> true)
    }

    "able to get an object of Sms Service" in {
      assert(SmsServiceImpl.isInstanceOf[SmsService])
    }

  }

}
