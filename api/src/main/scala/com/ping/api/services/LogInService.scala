package com.ping.api.services

import com.ping.domain.{ClientRequest, ClientView}
import com.ping.logger.PingLogger
import com.ping.persistence.repo.ClientRepo

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.control.NonFatal

/**
  * Created by girish on 30/6/17.
  */
trait LogInService extends PingLogger{

  val clientRepo: ClientRepo

  def processSignUp(clientRequest: ClientRequest): Future[ClientView] = {
    val accessToken = java.util.UUID.randomUUID().toString
    val rdClient = clientRequest.getRDClient(accessToken)
    clientRepo.insert(rdClient).map(_.getClientView)
  } recover{
    case NonFatal(ex) =>
      error("Got error while inserting into rdbms", ex)
      throw ex
  }


}

object LogInService extends LogInService{
  val clientRepo: ClientRepo = ClientRepo
}
