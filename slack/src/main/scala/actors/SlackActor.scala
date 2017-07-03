package actors

import akka.actor._
import com.ping.domain.PingSlack
import com.ping.json.JsonHelper
import com.ping.kafka.MessageFromKafka
import com.ping.logger.PingLogger
import service.SlackServiceImpl

class SlackActor extends Actor with PingLogger with JsonHelper {

  override def receive: PartialFunction[Any, Unit] = {
    case MessageFromKafka(msg: String) =>
      val slackDetails = parse(msg).extract[PingSlack]
      SlackServiceImpl.sendSlackMsg(slackDetails)

    case _ => error("Error occured in slack")
  }

}

