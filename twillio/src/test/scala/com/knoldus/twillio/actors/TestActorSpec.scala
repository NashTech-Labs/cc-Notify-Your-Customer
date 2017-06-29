//package com.knoldus.twillio.actors
//
//import akka.actor.{ActorRef, ActorSystem}
//import akka.testkit.{ImplicitSender, TestKit}
//import org.scalatest._
//import org.scalatest.mockito.MockitoSugar
//
//
//class TestActorSpec extends TestKit(ActorSystem("twillio-actor-ref-factory-test")) with ImplicitSender
//  with WordSpecLike with MustMatchers with MockitoSugar with BeforeAndAfterAll {
//
//  val mockedTestService = mock[TestService]
//
//  object TwillioActorFactoryTest extends TwillioActorFactory(mockedTestService)
//
//  "get actor 1" when {
//
//    "actor name is valid" in {
//      val actorRef = TwillioActorFactoryTest.getReceiver(TwillioActorNames.TestActorName1)
//      actorRef.isInstanceOf[ActorRef] mustBe true
//    }
//
//    "actor name is invalid" in {
//      intercept[IllegalArgumentException] {
//        TwillioActorFactoryTest.getReceiver("invalid")
//      }
//    }
//
//  }
//
//  "get actor 2" when {
//
//    "actor name is valid" in {
//      val actorRef = TwillioActorFactoryTest.getReceiver(TwillioActorNames.TestActorName2)
//      actorRef.isInstanceOf[ActorRef] mustBe true
//    }
//
//    "actor name is invalid" in {
//      intercept[IllegalArgumentException] {
//        TwillioActorFactoryTest.getReceiver("invalid")
//      }
//    }
//  }
//
//}
