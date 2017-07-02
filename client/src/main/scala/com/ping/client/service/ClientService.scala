package com.ping.client.service

import com.google.inject.Inject
import com.ping.client.db.repositories.ClientRepo
import com.ping.date.DateUtil
import com.ping.domain.{ClientDetails, DBAccessToken, DBClient, DBClientAddress}
import com.ping.domain.client.ClientRequest
import com.ping.hasher.Hasher

import scala.concurrent.Future


class ClientService @Inject()(
                               val clientRepo: ClientRepo,
                               hasher: Hasher,
                               dateUtil: DateUtil
                             ) {

  def insert(clientReq: ClientRequest): Future[ClientDetails] = {
    val dbClient = DBClient(0, clientReq.name, clientReq.email, clientReq.phone)
    val dbClientAddress = DBClientAddress(0, 0, clientReq.address, clientReq.country, clientReq.pinCode)
    val hashedToken = hasher.getHash(clientReq.name + clientReq.email + clientReq.phone)
    val dbToken = DBAccessToken(0, 0, hashedToken, dateUtil.currentTimestamp)

    val details = ClientDetails(dbClient, dbClientAddress, dbToken)
    clientRepo.insertClient(details)
  }

  def getClientById(id: Long): Future[Option[DBClient]] = {
    clientRepo.getClientById(id)
  }

  def getClientByAccessToken(token: String): Future[Option[DBClient]] = {
    clientRepo.getClientByAccessToken(token)
  }

}

