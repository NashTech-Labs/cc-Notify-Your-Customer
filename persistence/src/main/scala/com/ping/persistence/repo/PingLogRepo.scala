package com.ping.persistence.repo

import com.ping.models.RDPingLog
import com.ping.persistence.mapping.PingLogMapping
import com.ping.persistence.provider.{DBProvider, PostgresDBProvider}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by girish on 1/7/17.
  */
trait PingLogRepo extends PingLogMapping { this: DBProvider =>

  import driver.api._

  def insert(rdPingLog: RDPingLog): Future[RDPingLog] = withTransaction {
    (pingLogInfoAutoInc += rdPingLog) map { incId =>
      rdPingLog.copy(id = incId)
    }
  }

}

object PingLogRepo extends PingLogRepo with PostgresDBProvider
