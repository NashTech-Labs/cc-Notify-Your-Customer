package com.knoldus.twillio.actors

import akka.actor.Actor
import com.ping.logger.PingLogger


case class Message(msg: String)

class TestActor(testService: TestService) extends Actor with PingLogger {

  def receive ={
    case Message(msg) =>
      println("test log")
      testService.test(msg)
    case msg =>
      println(s"Oops! Could not understand message: ${msg}")
  }


}
