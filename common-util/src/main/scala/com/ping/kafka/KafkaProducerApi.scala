package com.ping.kafka

import java.util.Properties

import com.ping.logs.PingLogger
import org.apache.kafka.clients.producer.{KafkaProducer}
import scala.concurrent.ExecutionContext.Implicits.global
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.apache.kafka.common.serialization.StringSerializer
import java.util.Properties
import scala.concurrent.{Promise, Future}

import scala.util.Try



trait KafkaProducerApi {
  def send(topic: String, record: String): Future[RecordMetadata]
  def close():Unit={}

}

trait Producer extends KafkaProducerApi with PingLogger {

  def servers: String

  private val props: Properties = new Properties
  props.put("bootstrap.servers", servers)
  props.put("acks", "all")
  props.put("retries", "5")
  props.put("key.serializer", classOf[StringSerializer].getName)
  props.put("value.serializer", classOf[StringSerializer].getName)

  private val producer: KafkaProducer[String, String] = new KafkaProducer[String, String](props)

  def send(topic: String, record: String): Future[RecordMetadata] = {
    val message: ProducerRecord[String, String] = new ProducerRecord[String, String](topic, record)
    debug("Sending message to kafka cluster .....")
    val recordMetadataResponse = producer.send(message)
    val promise = Promise[RecordMetadata]()
    Future {
      promise.complete(Try(recordMetadataResponse.get()))
    }
    promise.future
  }

  override def close(): Unit = producer.close()
}
