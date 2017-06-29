package service

import infrastructure.{SlackApi, SlackApiImpl}
import slack.main.scala.SlackDetails

import scala.concurrent.Future


trait SlackService {

  val slackApi: SlackApi

  /**
    *
    * this method calls the send(..) method of the SlackApi
    */
  def sendSlackMsg(slackDetails: SlackDetails): Future[Boolean] = {
    slackApi.send(slackDetails)
  }
}

object SlackServiceImpl extends SlackService {
  val slackApi: SlackApi = SlackApiImpl
}