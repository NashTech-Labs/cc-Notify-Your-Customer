package com.ping.persistence.repo

import com.ping.models.RDTwilioConfig
import com.ping.persistence.mapping.TwilioConfigMapping
import com.ping.persistence.provider.{DBProvider, PostgresDBProvider}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


trait TwilioConfigRepo extends TwilioConfigMapping {
  this: DBProvider =>

  import driver.api._

  def insert(RDTwilioConfig: RDTwilioConfig): Future[RDTwilioConfig] = withTransaction {
    (twillioConfigInfoAutoInc += RDTwilioConfig) map { incId =>
      RDTwilioConfig.copy(id = incId)
    }
  }

  def get(id: Long): Future[Option[RDTwilioConfig]] = withTransaction {
    twilioConfigInfo.filter(_.id === id).result.headOption
  }

  def update(RDTwilioConfig: RDTwilioConfig): Future[Int] = withTransaction {
    twilioConfigInfo.filter(_.id === RDTwilioConfig.id).update(RDTwilioConfig)
  }

  def delete(id: Long): Future[Int] = withTransaction {
    twilioConfigInfo.filter(_.id === id).delete
  }

}

object TwilioConfigRepo extends TwilioConfigRepo with PostgresDBProvider
