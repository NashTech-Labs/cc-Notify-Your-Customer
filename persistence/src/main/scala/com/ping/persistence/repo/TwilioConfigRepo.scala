package com.ping.persistence.repo

import com.ping.models.RDTwilioConfig
import com.ping.persistence.mapping.TwilioConfigMapping
import com.ping.persistence.provider.{DBProvider, PostgresDBProvider}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


trait TwilioConfigRepo extends TwilioConfigMapping {
  this: DBProvider =>

  import driver.api._

  def insert(rdTwilioConfig: RDTwilioConfig): Future[RDTwilioConfig] = withTransaction {
    (twillioConfigInfoAutoInc += rdTwilioConfig) map { incId =>
      rdTwilioConfig.copy(id = incId)
    }
  }

  def get(clientId: Long): Future[Option[RDTwilioConfig]] = withTransaction {
    twilioConfigInfo.filter(_.clientId === clientId).result.headOption
  }

  def getByClientId(clientId: Long): Future[Option[RDTwilioConfig]] = withTransaction {
    twilioConfigInfo.filter(_.clientId === clientId).result.headOption
  }

  def update(rdTwilioConfig: RDTwilioConfig): Future[Int] = withTransaction {
    twilioConfigInfo.filter(_.id === rdTwilioConfig.id).update(rdTwilioConfig)
  }

  def delete(id: Long): Future[Int] = withTransaction {
    twilioConfigInfo.filter(_.id === id).delete
  }

}

object TwilioConfigRepo extends TwilioConfigRepo with PostgresDBProvider
