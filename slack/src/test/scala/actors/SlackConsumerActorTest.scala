package actors

import akka.actor.{Actor, ActorSystem}
import akka.testkit.{ImplicitSender, TestActorRef, TestKit}
import com.ping.domain.PingSlack
import com.ping.json.JsonHelper
import com.ping.kafka.{KafkaConsumerApi, MessageFromKafka}
import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpecLike}


class SlackConsumerActorTest extends TestKit(ActorSystem("SlackConsumerActorTest")) with ImplicitSender with WordSpecLike
  with MustMatchers with BeforeAndAfterAll with JsonHelper {

  val mockedKafkaConsumer = new KafkaConsumerApi {


    def read(): List[MessageFromKafka] =


        List(MessageFromKafka(write(PingSlack("1",Some("general"), "hello"))))

    def close(): Unit = {}
  }
  val workerActor = TestActorRef(new Actor {
    def receive = {
      case msg => }
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
