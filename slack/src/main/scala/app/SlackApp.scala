package app

import actors.{SlackActor, SlackConsumerActor}
import akka.actor.{ActorSystem, Props}
import com.ping.kafka.Consumer
import com.ping.logger.PingLogger
import com.ping.kafka.Topics._
object SlackApp extends App with PingLogger {
  val groupId = "1"
  val servers = "localhost"
  val system = ActorSystem("slack-system")
  val slackSender = system.actorOf(Props[SlackActor])
  val consumerActor = system.actorOf(SlackConsumerActor.props(new Consumer(groupId, servers, List(topicSlack)), slackSender))
  consumerActor ! SlackConsumerActor.Read
}