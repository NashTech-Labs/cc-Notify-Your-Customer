package com.ping.persistence.repo

import com.ping.models.RDClient
import com.ping.persistence.mapping.ClientMapping
import com.ping.persistence.provider.{DBProvider, PostgresDBProvider}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.control.NonFatal

/**
  * Created by girish on 30/6/17.
  */
trait ClientRepo extends ClientMapping { this: DBProvider =>

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
    clientInfo.filter(x => x.id === client.id).update(client)
  }

  def delete(id: Long): Future[Int] = withTransaction {
    clientInfo.filter(x => x.id === id).delete
  }

}

object ClientRepo extends ClientRepo with PostgresDBProvider
