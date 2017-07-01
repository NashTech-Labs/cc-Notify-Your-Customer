package com.ping.kafka

import org.apache.kafka.clients.producer.RecordMetadata
import org.scalatest.AsyncWordSpec

/**
  * Created by girish on 1/7/17.
  */
class KafkaProducerSpec extends AsyncWordSpec {

  val pingProducer = new Producer {
    def servers = "localhost:9092"
  }

  "A kafka Producer" should {

    "send message to the topic successfully" in {
      pending
      pingProducer.send("test", "Hello") map { (metadata: RecordMetadata) =>
        assert(metadata.offset > 0)
      }
    }
  }
}
