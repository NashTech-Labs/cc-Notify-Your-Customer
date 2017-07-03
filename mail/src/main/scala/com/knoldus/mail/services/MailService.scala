package com.knoldus.mail.services

import akka.actor.ActorSystem
import com.ping.domain.PingEmail
import infrastructure.{MailPingHttpClient, PingClientApiFactory}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


trait MailService extends EmailApi {

  val mailPingHttpClient: MailPingHttpClient

  def sendEmail(emailInfo: PingEmail): Future[Option[Int]] = {
    mailPingHttpClient.getClientConfig(emailInfo.clientId.toString).map {
      case Some(config) => send(emailInfo, config.email, config.password)
      case None => None
    }
  }


}

object MailServiceImpl {
  def apply(system: ActorSystem): MailService= new MailService{
    val mailPingHttpClient: MailPingHttpClient = PingClientApiFactory(system)
  }
}