package com.ping.client.db.repositories

import com.ping.client.db.ServerSpec
import com.ping.models.{ClientDetails, DBAccessToken, DBClient, DBClientAddress}
import org.scalatest.AsyncWordSpec


class ClientRepoSpec extends AsyncWordSpec with ClientRepo with ServerSpec {

  "insert client" when {

    "simply inserting a client detail successfully" in {
      val client = DBClient(0, "Harshit Daga", "harshit@knoldus.com", "+919460444006")
      val address = DBClientAddress(0, 0, "NSEZ, L-11", "India", "201305")
      val token = DBAccessToken(0, 0, "someHashedToken", new java.sql.Timestamp(System.currentTimeMillis))
      val clientDetails = ClientDetails(client, address, token)
      insertClient(clientDetails) map { res =>
        assert(res.client === client.copy(id = 2))
        assert(res.clientAddress === address.copy(id =1, clientId = 2))
      }
    }

  }

  "get client" when {
    "going to fetch client by its id successfully" in {
      getClientById(1) map {
        case Some(client) => assert(client.name === "Girish")
        case None => assert(false)
      }
    }

    "going to fetch client by some invalid id" in {
      getClientById(-1) map {
        case None => assert(true)
        case Some(_) => assert(false)
      }
    }

    "going to fetch client by access token successfully" in {
      getClientByAccessToken("client_access_token") map {
        case Some(client) => assert(client.id === 1)
        case None => assert(false)
      }
    }

    "going to fetch client by invalid access token" in {
      getClientByAccessToken("some-invalid-access-token") map {
        case None => assert(true)
        case Some(client) => assert(false)
      }
    }

  }


}
