package com.ping.api.services

import com.ping.domain.{ClientRequest, ClientView}
import com.ping.persistence.repo.ClientRepo

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by girish on 30/6/17.
  */
trait LogInService {

  val clientRepo: ClientRepo

  def processSignUp(clientRequest: ClientRequest): Future[ClientView] = {
    val accessToken = java.util.UUID.randomUUID().toString
    val rdClient = clientRequest.getRDClient(accessToken)
    clientRepo.insert(rdClient).map(_.getClientView)
  }


}
