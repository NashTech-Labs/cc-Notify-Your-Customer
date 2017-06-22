package com.knoldus.twillio.actors

import akka.actor.{ActorRef, ActorSystem}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest._
import org.scalatest.mockito.MockitoSugar


class TestActorSpec {

}

class LocalActorRefFactoryTest extends TestKit(ActorSystem("LocalActorRefFactoryTest")) with ImplicitSender
  with AsyncWordSpecLike with MustMatchers with MockitoSugar with BeforeAndAfterAll  {

  val mockedTestService = mock[TestService]

  object TwillioActorFactoryTest extends TwillioActorFactory(mockedTestService)

  "TwillioActorFactory" should {

    "be able to get actor 1" in {
      val actorRef = TwillioActorFactoryTest.getReceiver(TwillioActorNames.TEST_ACTOR_NAME1)
      actorRef.isInstanceOf[ActorRef] mustBe true
    }

    "be able to get actor 2" in {
      val actorRef = TwillioActorFactoryTest.getReceiver(TwillioActorNames.TEST_ACTOR_NAME2)
      actorRef.isInstanceOf[ActorRef] mustBe true
    }

  }
}
