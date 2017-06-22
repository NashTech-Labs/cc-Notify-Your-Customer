package com.knoldus.twillio.actors

import akka.actor.{ActorRef, ActorRefFactory, Props}
import akka.routing.RoundRobinPool
import akka.util.Timeout
import scala.concurrent.duration._


/**
  * TODO
  * Sample class implementing factory pattern
  * Please update code accordingly
  *
  * @param testService
  */
class TwillioActorFactory(testService: TestService) {

  import TwillioActorNames._

  implicit val timeout = Timeout(180 seconds)

  val availableProcessor = Runtime.getRuntime.availableProcessors()
  val concurrencyFactor = availableProcessor

  val actors: Map[String, Props] = Map(
    TEST_ACTOR_NAME1 -> Props(classOf[TestActor], testService).withRouter(RoundRobinPool(concurrencyFactor)),
    TEST_ACTOR_NAME2 -> Props(classOf[TestActor], testService).withRouter(RoundRobinPool(concurrencyFactor))
  )

  def getReceiver(name: String)(implicit system: ActorRefFactory): ActorRef = {
    actors.get(name) match {
      case None => throw new IllegalArgumentException("No Actor could be looked up for the specified name " + name)
      case Some(props) => system.actorOf(props)
    }
  }

}

object TwillioActorNames {
  lazy val TEST_ACTOR_NAME1 = "testActorName1"
  lazy val TEST_ACTOR_NAME2 = "testActorName2"
}


/**
  * Example
  * Test service
  */
trait TestService {
  def test(msg: String): String = {
    "Hello" + msg
  }
}
