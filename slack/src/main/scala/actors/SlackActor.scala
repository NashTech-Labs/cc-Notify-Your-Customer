package actors

import akka.actor._
import com.ping.json.JsonHelper
import com.ping.kafka.MessageFromKafka
import com.ping.logger.PingLogger
import service.SlackServiceImpl
import slack.main.scala.SlackDetails

class SlackActor extends Actor with PingLogger with JsonHelper {

  override def receive: PartialFunction[Any, Unit] = {
    case MessageFromKafka(msg: String) =>
      val slackDetails = parse(msg).extract[SlackDetails]
      SlackServiceImpl.sendSlackMsg(slackDetails)

    case _ => error("Error occured in slack")
  }

}

