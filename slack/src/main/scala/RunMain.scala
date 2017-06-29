import service.SlackServiceImpl
import slack.main.scala.SlackDetails

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * run RunMain to send the message "welcome user on slack!! :)" on slack channel named notify-me as user
  */
object RunMain extends App {

  val slackServiceImpl = SlackServiceImpl
  val result = Await.result(slackServiceImpl.sendSlackMsg(SlackDetails( "general", "welcome ", Some("bot"))), Duration(20, "seconds"))

}
