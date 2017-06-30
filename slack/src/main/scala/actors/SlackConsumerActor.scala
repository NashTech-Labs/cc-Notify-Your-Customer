package actors


import akka.actor.{Actor, ActorRef, Props}
import com.ping.kafka.KafkaConsumerApi
import com.ping.logger.PingLogger
import scala.concurrent.duration._



class SlackConsumerActor(consumer: KafkaConsumerApi, slackRef: ActorRef) extends Actor with PingLogger {

  import SlackConsumerActor._
  import context.dispatcher

  val waitingTime: FiniteDuration = 1 seconds

  def receive: Receive = {
    case Read =>
      val records = consumer.read()
      records foreach { message => slackRef ! message }
      if (records.isEmpty) {
        context.system.scheduler.scheduleOnce(waitingTime, self, Read)
      } else {
        self ! Read
      }
  }


}

object SlackConsumerActor {

  def props(consumer: KafkaConsumerApi, slackRef: ActorRef): Props = Props(classOf[SlackConsumerActor], consumer, slackRef)

  case object Read

}