package com.ping.api.services

import com.ping.date.{DateUtil, UUIDHelper}
import com.ping.domain._
import com.ping.json.JsonHelper
import com.ping.kafka.KafkaProducerApi
import com.ping.kafka.Topics._
import com.ping.logs.PingLogger
import com.ping.models._
import com.ping.persistence.repo.PingLogRepo

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.control.NonFatal


trait PingService extends JsonHelper with PingLogger {

  val pingProducer: KafkaProducerApi
  val pingLogRepo: PingLogRepo
  val dateUtil: DateUtil
  val uuidHelper: UUIDHelper

  def processPing(ping: Ping, client: RDClient): Future[PingResponse] = {
    for {
      mail <- if (validate(client, MessageType.mail)) {
        ping.mail match {
          case Some(mail) => sendMail(mail, client)
          case None => Future.successful(None)
        }
      } else {
        Future.successful(None)
      }
      slack <- if (validate(client, MessageType.slack)) {
        ping.slack match {
          case Some(slack) => sendSlackMessage(slack, client)
          case None => Future.successful(None)
        }
      } else {
        Future.successful(None)
      }
      message <- if (validate(client, MessageType.twilio)) {
        ping.message match {
          case Some(message) => sendPhoneMessage(message, client)
          case None => Future.successful(None)
        }
      } else {
        Future.successful(None)
      }
    } yield {
      PingResponse(slack, mail, message)
    }
  }

  private def validate(client: RDClient, channel: String): Boolean = {
    channel match {
      case MessageType.mail => client.mailEnabled
      case MessageType.slack => client.slackEnabled
      case MessageType.twilio => client.twilioEnabled
      case _ => true
    }
  }

  private def sendMail(mail: PingEmail, client: RDClient): Future[Option[RDPingLogView]] = {
    val pingLog = RDPingLog(0L, uuidHelper.getRandomUUID, client.id, MessageType.mail, mail.subject + "***" + mail.content,
      (mail.to ::: mail.cc ::: mail.bcc).mkString(", "), dateUtil.currentTimestamp, PingStatus.initiated)

    pingLogRepo.insert(pingLog) map { log =>
      dispatchPing(topicMail, write(mail.copy(clientId = client.id)))
      Some(log.getLogView)
    } recover {
      case NonFatal(ex) =>
        error("Error found while dispatching mail", ex)
        None
    }
  }

  private def sendSlackMessage(slack: PingSlack, client: RDClient) = {
    val pingLog = RDPingLog(0L, uuidHelper.getRandomUUID, client.id, MessageType.slack, slack.message,
      slack.channelId.getOrElse("default"), dateUtil.currentTimestamp, PingStatus.initiated)
    pingLogRepo.insert(pingLog) map { log =>
      dispatchPing(topicSlack, write(slack.copy(clientId = client.id)))
      Some(log.getLogView)
    } recover {
      case NonFatal(ex) =>
        error("Error found while dispatching mail", ex)
        None
    }
  }

  private def sendPhoneMessage(message: TwilioMessage, client: RDClient) = {
    val pingLog = RDPingLog(0L, uuidHelper.getRandomUUID, client.id, MessageType.twilio, message.text,
      message.to, dateUtil.currentTimestamp, PingStatus.initiated)
    pingLogRepo.insert(pingLog) map { log =>
      dispatchPing(topicMessage, write(message.copy(clientId = client.id)))
      Some(log.getLogView)
    } recover {
      case NonFatal(ex) =>
        error("Error found while dispatching mail", ex)
        None
    }
  }

  private def dispatchPing(topic: String, message: String) = Future {
    pingProducer.send(topic, write(message))
  }

}

object PingServiceImpl {
  def apply(pingProducerImpl: KafkaProducerApi) = new PingService {
    val pingProducer: KafkaProducerApi = pingProducerImpl
    val pingLogRepo: PingLogRepo = PingLogRepo
    val dateUtil: DateUtil = DateUtil
    val uuidHelper: UUIDHelper = UUIDHelper
  }
}
