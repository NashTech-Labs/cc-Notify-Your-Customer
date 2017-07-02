//package com.knoldus.twillio.actors
//
//import akka.actor.{ActorRef, ActorRefFactory, Props}
//import akka.routing.RoundRobinPool
//import akka.util.Timeout
//import scala.concurrent.duration._
//import TwillioActorNames._
//
///**
//  * TODO
//  * Sample class implementing factory pattern
//  * Please update code accordingly
//  *
//  * @param testService
//  */
//class TwillioActorFactory(testService: TestService) {
//
//  implicit val timeout = Timeout(180 seconds)
//
//  val availableProcessor = Runtime.getRuntime.availableProcessors()
//  val concurrencyFactor = availableProcessor
//
//  val actors: Map[String, Props] = Map(
//    TestActorName1 -> Props(classOf[TestActor], testService).withRouter(RoundRobinPool(concurrencyFactor)),
//    TestActorName2 -> Props(classOf[TestActor], testService).withRouter(RoundRobinPool(concurrencyFactor))
//  )
//
//  def getReceiver(name: String)(implicit system: ActorRefFactory): ActorRef = {
//    actors.get(name) match {
//      case None => throw new IllegalArgumentException("No Actor could be looked up for the specified name " + name)
//      case Some(props) => system.actorOf(props)
//    }
//  }
//
//}
//
//object TwillioActorNames {
//  lazy val TestActorName1 = "testActorName1"
//  lazy val TestActorName2 = "testActorName2"
//}
//
//
///**
//  * Example
//  * Test service
//  */
//trait TestService {
//  def test(msg: String): String = {
//    "Hello" + msg
//  }
//}
