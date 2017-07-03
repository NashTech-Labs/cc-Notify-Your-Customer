package com.knoldus.mail.services

import akka.actor.ActorSystem
import com.ping.domain.PingEmail
import com.ping.logger.PingLogger
import infrastructure.{MailPingHttpClient, PingClientApiFactory}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


trait MailService extends EmailApi with PingLogger {

  val mailPingHttpClient: MailPingHttpClient

  def sendEmail(emailInfo: PingEmail): Future[Option[Int]] = {
    info(s"Processing messag $emailInfo")
    mailPingHttpClient.getClientConfig(emailInfo.clientId.toString).map {
      case Some(config) =>
        info(s"Got config from client api: $config, processing mail: $emailInfo")
        send(emailInfo, config.email, config.password)
      case None =>
        error(s"Could not get config from client for client id: ${emailInfo.clientId}")
        None
    }
  }


}

object MailServiceImpl {
  def apply(system: ActorSystem): MailService = new MailService {
    val mailPingHttpClient: MailPingHttpClient = PingClientApiFactory(system)
  }
}
