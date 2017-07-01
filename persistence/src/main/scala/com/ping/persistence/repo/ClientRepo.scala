package com.ping.persistence.repo

import com.ping.logger.PingLogger
import com.ping.models.RDClient
import com.ping.persistence.mapping.ClientMapping
import com.ping.persistence.provider.{DBProvider, PostgresDBProvider}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


trait ClientRepo extends ClientMapping with PingLogger {
  this: DBProvider =>

  import driver.api._

  def insert(client: RDClient): Future[RDClient] = withTransaction {
    (clientAutoInc += client) map { incId =>
      client.copy(id = incId)
    }
  }

  def get(id: Long): Future[Option[RDClient]] = withTransaction {
    clientInfo.filter(x => x.id === id).result.headOption
  }

  def getClientByAccessToken(accessToken: String): Future[Option[RDClient]] = withTransaction {
    clientInfo.filter(_.accessToken === accessToken).result.headOption
  }

  def update(client: RDClient): Future[Int] = withTransaction {
    clientInfo.filter(_.id === client.id).update(client)
  }

  def enableMail(clientId: Long): Future[Int] = withTransaction {
    (for {clients <- clientInfo if clients.id === clientId} yield clients.mailEnabled).update(true)
  }

  def enableSlack(clientId: Long): Future[Int] = withTransaction {
    (for {clients <- clientInfo if clients.id === clientId} yield clients.slackEnabled).update(true)
  }

  def enableTwilio(clientId: Long): Future[Int] = withTransaction {
    (for {clients <- clientInfo if clients.id === clientId} yield clients.twilioEnabled).update(true)
  }


  def delete(id: Long): Future[Int] = withTransaction {
    clientInfo.filter(_.id === id).delete
  }

}

object ClientRepo extends ClientRepo with PostgresDBProvider
