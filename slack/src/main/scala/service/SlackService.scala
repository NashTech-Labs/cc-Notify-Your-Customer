package service

import akka.actor.ActorSystem
import com.ping.domain.PingSlack
import com.ping.logger.PingLogger
import infrastructure.{SlackApi, SlackApiImpl}

import scala.concurrent.Future


trait SlackService extends PingLogger{

  val slackApi: SlackApi

  /**
    *
    * this method calls the send(..) method of the SlackApi
    */
  def sendSlackMsg(slackDetails: PingSlack): Future[String] = {
    info(s"Processing message: $slackDetails")
    slackApi.send(slackDetails)
  }
}

object SlackServiceImpl {
  def apply(system: ActorSystem) = new SlackService {
    val slackApi: SlackApi = SlackApiImpl(system)
  }
}
