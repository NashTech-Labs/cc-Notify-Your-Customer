package com.ping.client.service

import com.google.inject.Inject
import com.ping.client.db.repositories.ClientRepo
import com.ping.models.DBClient

import scala.concurrent.Future

class ClientService @Inject()(clientRepo: ClientRepo) {

  def getClientById(id: Long): Future[Option[DBClient]] = {
    clientRepo.getClientById(id)
  }

  def getClientByAccessToken(token: String): Future[Option[DBClient]] = {
    clientRepo.getClientByAccessToken(token)
  }

}
