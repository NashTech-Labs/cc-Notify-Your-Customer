package com.ping.services

import akka.actor.ActorSystem
import com.ping.domain.TwilioMessage
import com.ping.infrastructure.twillio.api.{TwilioApi, TwilioPingClientApiFactory, TwilioPingHttpClient}
import com.ping.logger.PingLogger

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


trait SmsService extends PingLogger {

  val twilioPingHttpClient: TwilioPingHttpClient

  def send(smsDetail: TwilioMessage): Future[Boolean] = {
    info(s"Processing message: $smsDetail")
    twilioPingHttpClient.getClientConfig(smsDetail.clientId.toString).map {
      case Some(config) =>
        val twillioApi: TwilioApi = new TwilioApi {
          val accountSid: String = config.sID
          val authToken: String = config.token
        }
        info(s"Got config for client id: ${smsDetail.clientId}")
        if (smsDetail.text.length < 160) {
          info(s"Sending message to twilio: ${smsDetail.text}")
          twillioApi.send(config.phoneNumber, smsDetail.to, smsDetail.text)
        } else {
          val splitedSms: List[String] = smsDetail.text.grouped(150).toList
          splitedSms.map { text =>
            info(s"Sending message to twilio with length > 160 chars: $text")
            twillioApi.send(config.phoneNumber, smsDetail.to, text)
          }
        }
        true
      case None =>
        false
    }
  }

}

object SmsServiceImpl {

  def apply(system: ActorSystem) = new SmsService {
    val twilioPingHttpClient: TwilioPingHttpClient = TwilioPingClientApiFactory(system)
  }
}
