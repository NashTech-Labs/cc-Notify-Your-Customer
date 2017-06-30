package com.ping.models

import java.sql.Timestamp

import com.ping.domain.PingStatus

/**
  * Created by girish on 1/7/17.
  */
case class RDPingLog(
                      id: Long,
                      uuid: String,
                      clientId: Long,
                      messageType: String,
                      message: String,
                      to: String,
                      sentAt: Timestamp,
                      status: String = PingStatus.initiated
                    ) {
  def getLogView: RDPingLogView = RDPingLogView(uuid, messageType, message, to, sentAt, status)
}


case class RDPingLogView(
                          uuid: String,
                          messageType: String,
                          message: String,
                          to: String,
                          sentAt: Timestamp,
                          status: String
                        )
