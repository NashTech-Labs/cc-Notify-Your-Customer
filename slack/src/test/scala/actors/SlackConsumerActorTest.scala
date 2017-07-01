package actors

import akka.actor.{Actor, ActorSystem}
import akka.testkit.{ImplicitSender, TestActorRef, TestKit}
import com.ping.json.JsonHelper
import com.ping.kafka.{KafkaConsumerApi, MessageFromKafka}
import com.ping.models.PingSlack
import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpecLike}


class SlackConsumerActorTest extends TestKit(ActorSystem("SlackConsumerActorTest")) with ImplicitSender with WordSpecLike
  with MustMatchers with BeforeAndAfterAll with JsonHelper {

  val mockedKafkaConsumer = new KafkaConsumerApi {
    var counter = 0

    def read(): List[MessageFromKafka] =
      if (counter < 5) {
        counter = counter + 1
        List(MessageFromKafka(write(PingSlack("general", "hello"))))
      } else {
        Nil
      }

    def close(): Unit = {}
  }
  val workerActor = TestActorRef(new Actor {
    def receive = {
      case msg =>
        info(s"slack message found $msg")
    }
  })
  val consumerActor = system.actorOf(SlackConsumerActor.props(mockedKafkaConsumer, workerActor))

  override def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }


  "ConsumerActor " must {

    "consumed message from kafka " in {
      consumerActor ! SlackConsumerActor.Read
      expectNoMsg()
    }
  }
}