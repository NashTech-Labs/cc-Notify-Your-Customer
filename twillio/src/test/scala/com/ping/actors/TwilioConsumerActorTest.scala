package com.ping.actors


import akka.actor.{Actor, ActorSystem}
import akka.testkit.{TestActorRef, ImplicitSender, TestKit}
import com.ping.json.JsonHelper
import com.ping.kafka.{KafkaConsumerApi, MessageFromKafka}
import com.ping.models.SmsDetail
import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpecLike}


class TwilioConsumerActorTest extends TestKit(ActorSystem("TwilioConsumerActorTest")) with ImplicitSender with WordSpecLike
  with MustMatchers with BeforeAndAfterAll with JsonHelper {

  override def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  val mockedKafkaConsumer = new KafkaConsumerApi {
    var counter = 0

    def read(): List[MessageFromKafka] =
      if (counter < 5) {
        counter = counter + 1
        List(MessageFromKafka(write(SmsDetail("1", List("+919671701006"), "Hello customer",Some("+13523584605")))))
      } else {
        Nil
      }

    def close(): Unit = {}
  }

  val workerActor = TestActorRef(new Actor {
    def receive = {
      case msg =>
        info(s"mail detail found $msg")
    }
  })

  val consumerActor = system.actorOf(TwilioConsumerActor.props(mockedKafkaConsumer, workerActor))


  "ConsumerActor " must {

    "consumed message from kafka " in {
      consumerActor ! TwilioConsumerActor.Read
      expectNoMsg()
    }
  }
}
