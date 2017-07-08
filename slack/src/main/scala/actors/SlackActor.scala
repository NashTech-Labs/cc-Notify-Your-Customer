package actors

import akka.actor._
import com.ping.domain.PingSlack
import com.ping.json.JsonHelper
import com.ping.kafka.MessageFromKafka
import com.ping.logger.PingLogger
import service.SlackService

import scala.concurrent.Future

class SlackActor(slackService: SlackService) extends Actor with PingLogger with JsonHelper {

  override def receive: PartialFunction[Any, Unit] = {
    case MessageFromKafka(msg: String) => {
      info(s"Got  message from kafka..: $msg")
      parse(msg).extractOpt[PingSlack] match {
        case Some(slackDetails) =>
          info(s"Processing message: $slackDetails")
          slackService.sendSlackMsg(slackDetails)
        case None =>
          error(s"Error found in parsing message: ${msg}")
          Future.successful("Parsing error")
      }
    }
    case _ => error("Error occured in slack")
  }

}

object SlackActorFactory {
  def props(slackService: SlackService) = Props(classOf[SlackActor], slackService)
}
