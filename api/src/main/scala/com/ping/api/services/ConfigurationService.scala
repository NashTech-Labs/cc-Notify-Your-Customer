package com.ping.api.services

import com.ping.domain._
import com.ping.models.RDClient
import com.ping.persistence.repo.{MailConfigRepo, SlackConfigRepo, TwilioConfigRepo}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.control.NonFatal

/**
  * Created by girish on 1/7/17.
  */
trait ConfigurationService {

  val mailConfigRepo: MailConfigRepo
  val slackConfigRepo: SlackConfigRepo
  val twilioConfigRepo: TwilioConfigRepo

  def getConfigStatus(client: RDClient): Future[ConfigUpdateResponse] = {
    for {
      mailResponse <- mailConfigRepo.get(client.id).map(_ => "Exists").recover { case NonFatal(ex) => "Does not exists" }
      slackResponse <- slackConfigRepo.get(client.id).map(_ => "Exists").recover { case NonFatal(ex) => "Does not exists" }
      twilioResponse <- twilioConfigRepo.get(client.id).map(_ => "Exists").recover { case NonFatal(ex) => "Does not exists" }
    } yield {
      ConfigUpdateResponse(Some(mailResponse), Some(slackResponse), Some(twilioResponse))
    }
  }

  def createConfig(configRequest: ConfigRequest, client: RDClient): Future[ConfigUpdateResponse] = {
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
    mailConfigRepo.get(client.id) flatMap {
      case Some(_) => Future.successful(Some("Mail config already exists, try with put call"))
      case None => mailConfigRepo.insert(mailConfig.getRDMailConfig(client.id)).map(_ => Some("Added"))
    } recover {
      case NonFatal(ex) => Some(ex.getMessage)
    }
  }

  private def insertSlackConfig(slackConfig: SlackConfig, client: RDClient) = {
    slackConfigRepo.get(client.id) flatMap {
      case Some(_) => Future.successful(Some("Slack config already exists, try with put call"))
      case None => slackConfigRepo.insert(slackConfig.getRDSlackConfig(client.id)).map(_ => Some("Added"))
    } recover {
      case NonFatal(ex) => Some(ex.getMessage)
    }
  }

  private def insertTwilioConfig(twillioConfig: TwilioConfig, client: RDClient) = {
    twilioConfigRepo.get(client.id) flatMap {
      case Some(_) => Future.successful(Some("Twilio config already exists, try with put call"))
      case None => twilioConfigRepo.insert(twillioConfig.getRDTwilioConfig(client.id)).map(_ => Some("Added"))
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
    mailConfigRepo.update(mailConfig.getRDMailConfig(client.id)).map(_ => Some("Updated")) recover {
      case NonFatal(ex) => Some(ex.getMessage)
    }
  }

  private def updateSlackConfig(slackConfig: SlackConfig, client: RDClient) = {
    slackConfigRepo.update(slackConfig.getRDSlackConfig(client.id)).map(_ => Some("Updated")) recover {
      case NonFatal(ex) => Some(ex.getMessage)
    }
  }

  private def updateTwilioConfig(twillioConfig: TwilioConfig, client: RDClient) = {
    twilioConfigRepo.update(twillioConfig.getRDTwilioConfig(client.id)).map(_ => Some("Updated")) recover {
      case NonFatal(ex) => Some(ex.getMessage)
    }
  }

  def deleteConfig(client: RDClient): Future[ConfigUpdateResponse] = {
    for {
      mailResponse <- mailConfigRepo.delete(client.id).map(_ => "Deleted").recover { case NonFatal(ex) => "Does not exists" }
      slackResponse <- slackConfigRepo.delete(client.id).map(_ => "Deleted").recover { case NonFatal(ex) => "Does not exists" }
      twilioResponse <- twilioConfigRepo.delete(client.id).map(_ => "Deleted").recover { case NonFatal(ex) => "Does not exists" }
    } yield {
      ConfigUpdateResponse(Some(mailResponse), Some(slackResponse), Some(twilioResponse))
    }
  }

}

object ConfigurationService extends ConfigurationService {
  val mailConfigRepo: MailConfigRepo = MailConfigRepo
  val slackConfigRepo: SlackConfigRepo = SlackConfigRepo
  val twilioConfigRepo: TwilioConfigRepo = TwilioConfigRepo
}
