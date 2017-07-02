package com.ping.client.db.repositories

import com.ping.client.db.mapping.{AccessTokenMapping, ClientAddressMapping, ClientMapping}
import com.ping.client.db.provider.{DBProvider, PostgresDBProvider}
import com.google.inject.{ImplementedBy, Singleton}
import com.ping.domain.{ClientDetails, DBClient}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@ImplementedBy(classOf[ClientRepoImpl])
trait ClientRepo extends ClientMapping with AccessTokenMapping with ClientAddressMapping {
  this: DBProvider =>

  import driver.api._

  def insertClient(clientDetails: ClientDetails): Future[ClientDetails] = withTransaction {
    for {
      clientAutoIncId <- clientAutoInc += clientDetails.client
      addressId <- clientAddressAutoInc += clientDetails.clientAddress.copy(clientId = clientAutoIncId)
      tokenId <- accessTokenAutoInc += clientDetails.tokenDetails.copy(clientId = clientAutoIncId)
    } yield {
      val client = clientDetails.client.copy(id = clientAutoIncId)
      val address = clientDetails.clientAddress.copy(id = addressId, clientId = clientAutoIncId)
      val token = clientDetails.tokenDetails.copy(id = tokenId, clientId = clientAutoIncId)
      ClientDetails(client, address, token)
    }

  }

  def getClientById(id: Long): Future[Option[DBClient]] = withTransaction {
    clientInfo.filter(_.id === id).result.headOption

  }

  def getClientByAccessToken(accessToken: String): Future[Option[DBClient]] = withTransaction {
    val query = accessTokenInfo.filter(_.token === accessToken) join clientInfo on {
      case (accToken, client) => accToken.clientId === client.id
    } map {
      case (_, client) => client
    }

    query.result.headOption
  }

}

@Singleton
class ClientRepoImpl extends ClientRepo with PostgresDBProvider
