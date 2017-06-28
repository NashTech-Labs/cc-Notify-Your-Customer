package com.knoldus.mail.actors
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.actor.Actor.Receive
import com.knoldus.mail.services.EmailApiImpl
import com.ping.logger.PingLogger
import com.ping.models.EmailInfo

class MailSenderActor extends Actor with PingLogger{
  override def receive: PartialFunction[Any, Unit] = {
    case email:EmailInfo=>EmailApiImpl.send(email)
    case _=>  error("Error Occured in Mail sender")
  }

}
 object MailSenderActor extends App{
   val system = ActorSystem("mySystem")
   val myActor: ActorRef = system.actorOf(Props[MailSenderActor])
   myActor ! EmailInfo(1,List("himanshu.rajput@knoldus.in"),Nil,Nil,"fga","cvsdrg")
 }