package service

import akka.actor.ActorSystem
import com.ping.domain.PingSlack
import infrastructure.{SlackApi, SlackApiImpl}

import scala.concurrent.Future


trait SlackService {

  val slackApi: SlackApi

  /**
    *
    * this method calls the send(..) method of the SlackApi
    */
  def sendSlackMsg(slackDetails: PingSlack): Future[String] = {
    slackApi.send(slackDetails)
  }
}

object SlackServiceImpl {
  def apply(system: ActorSystem) = new SlackService {
    val slackApi: SlackApi = SlackApiImpl(system)
  }
}
