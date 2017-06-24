package com.ping.client.service

import com.ping.client.db.repositories.ClientRepo
import com.ping.hasher.Hasher
import com.ping.models._
import org.mockito.Mockito._
import org.scalatest.AsyncWordSpec
import org.scalatest.mockito.MockitoSugar

import scala.concurrent.Future


class ClientServiceSpec extends AsyncWordSpec with MockitoSugar {

  val mockClientRepo = mock[ClientRepo]
  val mockHasher = mock[Hasher]

  val clientServiceObj = new ClientService(mockClientRepo, mockHasher)


  "fetching client" should {

    "return successfully" in {
      val dbClient = DBClient(1, "Harshit", "harshit@knoldus.com", "94604440066")

      when(mockClientRepo.getClientById(1)) thenReturn Future.successful(Some(dbClient))
      clientServiceObj.getClientById(1) map {
        case Some(_) => assert(true)
        case None => assert(false)
      }
    }

    "return None for invalid client id" in {
      when(mockClientRepo.getClientById(-1)) thenReturn Future.successful(None)
      clientServiceObj.getClientById(-1) map {
        case Some(_) => assert(false)
        case None => assert(true)
      }
    }
  }

  "fetch client through access token" should {

    "return successfully" in {
      val dbClient = DBClient(1, "Harshit", "harshit@knoldus.com", "94604440066")

      when(mockClientRepo.getClientByAccessToken("someValidToken")) thenReturn Future.successful(Some(dbClient))
      clientServiceObj.getClientByAccessToken("someValidToken") map {
        case Some(_) => assert(true)
        case None => assert(false)
      }
    }

    "return None for invalid token" in {
      when(mockClientRepo.getClientByAccessToken("someinValidToken")) thenReturn Future.successful(None)
      clientServiceObj.getClientByAccessToken("someinValidToken") map {
        case Some(_) => assert(false)
        case None => assert(true)
      }
    }

  }


}
