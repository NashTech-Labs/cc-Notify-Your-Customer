package app

import actors.{SlackActorFactory, SlackConsumerActor}
import akka.actor.ActorSystem
import com.ping.kafka.Consumer
import com.ping.kafka.Topics._
import com.ping.logger.PingLogger
import service.SlackServiceImpl

object SlackApp extends App with PingLogger {
  val groupId = "1"
  val servers = "http://localhost:9092"
  val system = ActorSystem("slack-system")
  val slackSender = system.actorOf(SlackActorFactory.props(SlackServiceImpl(system)))
  val consumerActor = system.actorOf(SlackConsumerActor.props(new Consumer(groupId, servers, List(topicSlack)), slackSender))
  consumerActor ! SlackConsumerActor.Read
  info("Mail service has been started......")
}