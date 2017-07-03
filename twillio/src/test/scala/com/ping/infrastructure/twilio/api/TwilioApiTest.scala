package com.ping.infrastructure.twilio.api

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import com.ping.infrastructure.twillio.api.TwilioApi
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpecLike}


class TwilioApiTest extends TestKit(ActorSystem("NotificationSenderTest")) with ImplicitSender with WordSpecLike with MustMatchers
  with BeforeAndAfterAll with MockitoSugar {

  object TwillioApiTestObj extends TwilioApi

  "Twillio Api " should {

    "send sms to customs" in {
pending
      val isSent = TwillioApiTestObj.send("+13523584605", "+919671701006", "Hey How are you ??")
      assert(isSent)

    }

  }
}
