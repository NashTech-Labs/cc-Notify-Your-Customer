package com.ping.kafka

import java.util.{Properties, UUID}

import com.ping.logs.PingLogger
import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}
import org.apache.kafka.common.errors.WakeupException
import org.apache.kafka.common.serialization.StringDeserializer

import scala.collection.JavaConversions._
import scala.util.control.NonFatal


trait KafkaConsumerApi {

  def read(): List[MessageFromKafka]

  def close(): Unit

}

class Consumer(groupId: String, servers: String, topics: List[String]) extends KafkaConsumerApi with PingLogger{

  private val timeout = 10000

  private val props: Properties = new Properties
  props.put("bootstrap.servers", servers)
  props.put("client.id", UUID.randomUUID.toString)
  props.put("group.id", groupId)
  props.put("key.deserializer", classOf[StringDeserializer].getName)
  props.put("value.deserializer", classOf[StringDeserializer].getName)
  props.put("auto.commit.interval.ms", "900")

  private val consumer = new KafkaConsumer[String, String](props)
  consumer.subscribe(topics)

  def read(): List[MessageFromKafka] = {
    try {
      info("Reading from kafka queue ...... " + topics)
      val consumerRecords: ConsumerRecords[String, String] = consumer.poll(timeout)
      consumerRecords.map(record => MessageFromKafka(record.value())).toList
    }
    catch {
      case NonFatal(wakeupException: WakeupException) => error(" Getting WakeupException ", wakeupException)
        Nil
    }
  }

  def close(): Unit = consumer.close()

}

case class MessageFromKafka(record: String)
