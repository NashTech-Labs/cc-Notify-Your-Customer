package com.ping.client.db.repositories

import com.ping.client.db.ServerSpec
import com.ping.models.DBClient
import org.h2.jdbc.JdbcSQLException
import org.scalatest.AsyncWordSpec


class ClientRepoSpec extends AsyncWordSpec with ClientRepo with ServerSpec {

  "insert client" when {

    "simply inserting a client successfully" in {
      val client = DBClient(0, "Harshit Daga", "dagaharshit", "pa55w0rd", "harshit@knoldus.com", "+919460444006")
      insertClient(client) map { res =>
        assert(res.id === 2)
      }
    }

    "userName is repeated-- failure case" in {
      val client = DBClient(0, "Harshit Daga", "_girish", "pa55w0rd", "harshit@knoldus.com", "+919460444006")
      recoverToSucceededIf[JdbcSQLException](insertClient(client))
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
