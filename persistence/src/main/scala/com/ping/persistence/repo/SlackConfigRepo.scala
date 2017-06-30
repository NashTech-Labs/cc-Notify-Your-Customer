package com.ping.persistence.repo

import com.ping.models.RDSlackConfig
import com.ping.persistence.mapping.SlackConfigMapping
import com.ping.persistence.provider.{DBProvider, PostgresDBProvider}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by girish on 30/6/17.
  */
trait SlackConfigRepo extends SlackConfigMapping {
  this: DBProvider =>

  import driver.api._

  def insert(rdSlackConfig: RDSlackConfig): Future[RDSlackConfig] = withTransaction {
    (slackConfigInfoAutoInc += rdSlackConfig) map { incId =>
      rdSlackConfig.copy(id = incId)
    }
  }

  def get(id: Long): Future[Option[RDSlackConfig]] = withTransaction {
    slackConfigInfo.filter(_.id === id).result.headOption
  }

  def update(RDSlackConfig: RDSlackConfig): Future[Int] = withTransaction {
    slackConfigInfo.filter(_.id === RDSlackConfig.id).update(RDSlackConfig)
  }

  def delete(id: Long): Future[Int] = withTransaction {
    slackConfigInfo.filter(_.id === id).delete
  }

}

object SlackConfigRepo extends SlackConfigRepo with PostgresDBProvider
