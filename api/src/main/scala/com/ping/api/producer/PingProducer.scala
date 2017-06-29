package com.ping.api.producer

import com.ping.config.Configuration
import com.ping.kafka.Producer


trait PingProducer extends Producer {

  def servers: String = Configuration.config.getString("kafka.consumer.servers")

}
