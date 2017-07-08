package com.ping.api.services

import com.ping.domain._
import com.ping.json.JsonHelper
import com.ping.logger.PingLogger
import com.ping.models.{RDClient, RDMailConfig, RDSlackConfig, RDTwilioConfig}
import com.ping.persistence.repo.{ClientRepo, MailConfigRepo, SlackConfigRepo, TwilioConfigRepo}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.control.NonFatal


trait ConfigurationService extends JsonHelper with PingLogger {

  val mailConfigRepo: MailConfigRepo
  val slackConfigRepo: SlackConfigRepo
  val twilioConfigRepo: TwilioConfigRepo
  val clientRepo: ClientRepo

  def getConfigStatus(client: RDClient): Future[ConfigUpdateResponse] = {
    for {
      mailResponse <- mailConfigRepo.get(client.id).map(_ => "Exists").recover { case NonFatal(_) => "Does not exists" }
      slackResponse <- slackConfigRepo.get(client.id).map(_ => "Exists").recover { case NonFatal(_) => "Does not exists" }
      twilioResponse <- twilioConfigRepo.get(client.id).map(_ => "Exists").recover { case NonFatal(_) => "Does not exists" }
    } yield {
      ConfigUpdateResponse(Some(mailResponse), Some(slackResponse), Some(twilioResponse))
    }
  }

  def createConfig(configRequest: ConfigRequest, client: RDClient): Future[ConfigUpdateResponse] = {
    info(s"Creating configuration for client id: ${client.id}")
    for {
      mailResponse <- configRequest.mailConfig match {
        case Some(mailConf) => insertMailConfig(mailConf, client)
        case None => Future.successful(None)
      }
      slackResponse <- configRequest.slackConfig match {
        case Some(slackConf) => insertSlackConfig(slackConf, client)
        case None => Future.successful(None)
      }
      twilioResponse <- configRequest.twilioConfig match {
        case Some(twilioConf) => insertTwilioConfig(twilioConf, client)
        case None => Future.successful(None)
      }
    } yield {
      ConfigUpdateResponse(mailResponse, slackResponse, twilioResponse)
    }
  }

  private def insertMailConfig(mailConfig: MailConfig, client: RDClient): Future[Option[String]] = {
    info(s"Inserting mail config for client: ${client.id}")
    mailConfigRepo.get(client.id) flatMap {
      case Some(_) => Future.successful(Some("Mail config already exists, try with put call"))
      case None => mailConfigRepo.insert(mailConfig.getRDConfig(client.id)).flatMap { _ =>
        clientRepo.enableMail(client.id).map { _ => Some("Added") }
      }
    } recover {
      case NonFatal(ex) => Some(ex.getMessage)
    }
  }

  private def insertSlackConfig(slackConfig: SlackConfig, client: RDClient): Future[Option[String]] = {
    slackConfigRepo.get(client.id) flatMap {
      case Some(_) => Future.successful(Some("Slack config already exists, try with put call"))
      case None => slackConfigRepo.insert(slackConfig.getRDConfig(client.id)).flatMap { _ =>
        clientRepo.enableSlack(client.id).map(_ => Some("Added"))
      }
    } recover {
      case NonFatal(ex) => Some(ex.getMessage)
    }
  }

  private def insertTwilioConfig(twillioConfig: TwilioConfig, client: RDClient): Future[Option[String]] = {
    twilioConfigRepo.get(client.id) flatMap {
      case Some(_) => Future.successful(Some("Twilio config already exists, try with put call"))
      case None => twilioConfigRepo.insert(twillioConfig.getRDConfig(client.id)).flatMap { _ =>
        clientRepo.enableTwilio(client.id).map(_ => Some("Added"))
      }
    } recover {
      case NonFatal(ex) => Some(ex.getMessage)
    }
  }

  def updateConfig(configRequest: ConfigRequest, client: RDClient): Future[ConfigUpdateResponse] = {
    for {
      mailResponse <- configRequest.mailConfig match {
        case Some(mailConf) => updateMailConfig(mailConf, client)
        case None => Future.successful(None)
      }
      slackResponse <- configRequest.slackConfig match {
        case Some(slackConf) => updateSlackConfig(slackConf, client)
        case None => Future.successful(None)
      }
      twilioResponse <- configRequest.twilioConfig match {
        case Some(twilioConf) => updateTwilioConfig(twilioConf, client)
        case None => Future.successful(None)
      }
    } yield {
      ConfigUpdateResponse(mailResponse, slackResponse, twilioResponse)
    }
  }

  private def updateMailConfig(mailConfig: MailConfig, client: RDClient): Future[Option[String]] = {
    mailConfigRepo.getByClientId(client.id).flatMap {
      case Some(config) =>
        mailConfigRepo.update(mailConfig.getUpdatedConfig(config.id, client.id)).flatMap { _ =>
          clientRepo.enableMail(client.id).map(_ => Some("Updated"))
        }
      case None => Future.successful(Some("No existing config found"))
    } recover {
      case NonFatal(ex) => Some(ex.getMessage)
    }
  }

  private def updateSlackConfig(slackConfig: SlackConfig, client: RDClient): Future[Some[String]] = {
    slackConfigRepo.getByClientId(client.id).flatMap {
      case Some(conf) =>
        slackConfigRepo.update(slackConfig.getUpdatedConfig(conf.id, client.id)).flatMap { _ =>
          clientRepo.enableSlack(client.id).map(_ => Some("Updated"))
        }
      case None => Future.successful(Some("No existing config found"))
    } recover {
      case NonFatal(ex) => Some(ex.getMessage)
    }
  }

  private def updateTwilioConfig(twillioConfig: TwilioConfig, client: RDClient) = {
    twilioConfigRepo.getByClientId(client.id).flatMap {
      case Some(config) =>
        twilioConfigRepo.update(twillioConfig.getUpdatedConfig(config.id, client.id)).flatMap { _ =>
          clientRepo.enableTwilio(client.id).map(_ => Some("Updated"))
        }
      case None => Future.successful(Some("No existing config found"))
    } recover {
      case NonFatal(ex) => Some(ex.getMessage)
    }
  }

  def deleteConfig(client: RDClient): Future[ConfigUpdateResponse] = {
    for {
      mailResponse <- mailConfigRepo.delete(client.id).flatMap(_ => clientRepo.disableMail(client.id).map(_ => "Deleted"))
        .recover { case NonFatal(ex) => "Does not exists" }
      slackResponse <- slackConfigRepo.delete(client.id).flatMap(_ => clientRepo.disableSlack(client.id).map(_ => "Deleted"))
        .recover { case NonFatal(ex) => "Does not exists" }
      twilioResponse <- twilioConfigRepo.delete(client.id).flatMap(_ => clientRepo.disableTwilio(client.id).map(_ => "Deleted"))
        .recover { case NonFatal(ex) => "Does not exists" }
    } yield {
      ConfigUpdateResponse(Some(mailResponse), Some(slackResponse), Some(twilioResponse))
    }
  }

  def getMailConfig(clientId: Long): Future[Option[RDMailConfig]] = {
    mailConfigRepo.get(clientId)
  }

  def getSlackConfig(clientId: Long): Future[Option[RDSlackConfig]] = {
    slackConfigRepo.get(clientId)
  }

  def getTwilioConfig(clientId: Long): Future[Option[RDTwilioConfig]] = {
    twilioConfigRepo.get(clientId)
  }

}

object ConfigurationService extends ConfigurationService {
  val mailConfigRepo: MailConfigRepo = MailConfigRepo
  val slackConfigRepo: SlackConfigRepo = SlackConfigRepo
  val twilioConfigRepo: TwilioConfigRepo = TwilioConfigRepo
  val clientRepo: ClientRepo = ClientRepo
}
