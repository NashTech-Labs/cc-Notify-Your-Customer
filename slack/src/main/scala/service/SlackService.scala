package service

import com.ping.domain.PingSlack
import infrastructure.{SlackApi, SlackApiImpl}

import scala.concurrent.Future


trait SlackService {

  val slackApi: SlackApi

  /**
    *
    * this method calls the send(..) method of the SlackApi
    */
  def sendSlackMsg(slackDetails: PingSlack): Future[Boolean] = {
    slackApi.send(slackDetails)
  }
}

object SlackServiceImpl extends SlackService {
  val slackApi: SlackApi = SlackApiImpl
}