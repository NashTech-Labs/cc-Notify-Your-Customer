package com.ping.client.db.repositories

import com.ping.client.db.mapping.{AccessTokenMapping, ClientAddressMapping, ClientMapping}
import com.ping.client.db.provider.DBProvider
import com.ping.models.DBClient

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait ClientRepo extends ClientMapping with AccessTokenMapping with ClientAddressMapping {
  this: DBProvider =>

  import driver.api._

  def insertClient(client: DBClient): Future[DBClient] = {
    db.run(clientAutoInc += client) map { insertId =>
      client.copy(id = insertId)
    }
  }

  def getClientById(id: Long): Future[Option[DBClient]] = {
    val query = clientInfo.filter(_.id === id).result.headOption
    db.run(query)
  }

  def getClientByAccessToken(accessToken: String): Future[Option[DBClient]] = {
    val query = accessTokenInfo.filter(_.token === accessToken) join clientInfo on {
      case (accToken, client) => accToken.clientId === client.id
    } map {
      case (_, client) => client
    }

    db.run(query.result.headOption)
  }

}
