package com.ping.persistence.mapping

import java.sql.Timestamp

import com.ping.models.{RDPingLog, RDTwilioConfig}
import com.ping.persistence.provider.DBProvider
import slick.lifted.ProvenShape

/**
  * Created by girish on 1/7/17.
  */
trait PingLogMapping { this: DBProvider =>

  import driver.api._

  def pingLogInfo: TableQuery[PingLogMapping] = TableQuery[PingLogMapping]

  protected def pingLogInfoAutoInc = pingLogInfo returning pingLogInfo.map(_.id)

  class PingLogMapping(tag: Tag) extends Table[RDPingLog](tag, "ping_logs") {

    def * : ProvenShape[RDPingLog] = (id, uuid, clientId, messageType, message, to, sentAt, status) <>
      (RDPingLog.tupled, RDPingLog.unapply)

    def id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def uuid: Rep[String] = column[String]("log_uuid")

    def clientId: Rep[Long] = column[Long]("client_id")

    def messageType: Rep[String] = column[String]("message_type")

    def message: Rep[String] = column[String]("message")

    def to: Rep[String] = column[String]("sent_to")

    def sentAt: Rep[Timestamp] = column[Timestamp]("sent_at")

    def status: Rep[String] = column[String]("status")
  }

}
