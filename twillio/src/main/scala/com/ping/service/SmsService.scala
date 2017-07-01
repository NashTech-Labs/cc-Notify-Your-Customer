package com.ping.services

import com.ping.infrastructure.twillio.api.{TwilioApi, TwillioApiImpl}
import com.ping.logger.PingLogger
import com.ping.models.SmsDetail


trait SmsService extends PingLogger {
  val twillioApi: TwilioApi

  def send(smsDetail: SmsDetail): Map[String, Boolean] = {
    import com.ping.config.Configuration._
    val smsFromNo = smsDetail.from.getOrElse(config.getString("sms.from"))

    smsDetail.to.flatMap { to =>
      if (smsDetail.body.contains("###")) {
        smsDetail.body.split("###").toList.map { msg =>
          val isSent = twillioApi.send(smsFromNo, to, msg)
          to -> isSent
        }
      } else {
        val splitedSms = smsDetail.body.grouped(152).toList
        (1 to splitedSms.length).map { num =>
          val sms = (if (splitedSms.length > 1) s"($num/${splitedSms.length})\n" else "") + splitedSms(num - 1)
          val isSent = twillioApi.send(smsFromNo, to, sms)
          to -> isSent
        }
      }
    }.toMap

  }

}

object SmsServiceImpl extends SmsService {
  val twillioApi: TwilioApi = TwillioApiImpl
}
