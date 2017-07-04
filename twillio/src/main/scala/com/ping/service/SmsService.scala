package com.ping.services

import akka.actor.ActorSystem
import com.ping.config.Configuration
import com.ping.domain.TwilioMessage
import com.ping.infrastructure.twillio.api.{TwilioApi, TwilioPingClientApiFactory, TwilioPingHttpClient, TwillioApiImpl}
import com.ping.logger.PingLogger
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


trait SmsService extends PingLogger {
  val twillioApi: TwilioApi
  val twilioPingHttpClient: TwilioPingHttpClient

  def send(smsDetail: TwilioMessage): Future[Boolean] = {

    twilioPingHttpClient.getClientConfig(smsDetail.clientId.toString).map {
      case Some(config) =>
        smsDetail.to.flatMap { to =>
          if (smsDetail.text.contains("###")) {
            smsDetail.text.split("###").toList.map { msg =>
              val isSent = twillioApi.send(config.phoneNumber, smsDetail.to, msg)
              to -> isSent
            }
          } else {
            val splitedSms = smsDetail.text.grouped(152).toList
            (1 to splitedSms.length).map { num =>
              val sms = (if (splitedSms.length > 1) s"($num/${splitedSms.length})\n" else "") + splitedSms(num - 1)
              val isSent = twillioApi.send(config.phoneNumber, smsDetail.to, sms)
              to -> isSent
            }
          }
        }.toMap
        true
      case None =>
        false
    }
  }

}

object SmsServiceImpl {

  def apply(system: ActorSystem) = new SmsService {
    val twillioApi: TwilioApi = TwillioApiImpl
    val twilioPingHttpClient: TwilioPingHttpClient = TwilioPingClientApiFactory(system)
  }
}
