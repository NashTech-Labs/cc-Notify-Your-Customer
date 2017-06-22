package com.knoldus.twillio.actors

import akka.actor.{ActorRef, ActorSystem}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest._
import org.scalatest.mockito.MockitoSugar


class TestActorSpec extends TestKit(ActorSystem("twillio-actor-ref-factory-test")) with ImplicitSender
  with AsyncWordSpecLike with MustMatchers with MockitoSugar with BeforeAndAfterAll {

  val mockedTestService = mock[TestService]

  object TwillioActorFactoryTest extends TwillioActorFactory(mockedTestService)

  "get actor 1" when {

    "actor name is valid" in {
      val actorRef = TwillioActorFactoryTest.getReceiver(TwillioActorNames.TEST_ACTOR_NAME1)
      actorRef.isInstanceOf[ActorRef] mustBe true
    }

    "actor name is invalid" in {
      val actorRef = TwillioActorFactoryTest.getReceiver("invalid")
      actorRef.isInstanceOf[ActorRef] mustBe true
    }

  }

  "get actor 2" when {

    "actor name is valid" in {
      val actorRef = TwillioActorFactoryTest.getReceiver(TwillioActorNames.TEST_ACTOR_NAME2)
      actorRef.isInstanceOf[ActorRef] mustBe true
    }

    "actor name is invalid" in {
      val actorRef = TwillioActorFactoryTest.getReceiver("invalid")
      actorRef.isInstanceOf[ActorRef] mustBe true
    }
  }

}
