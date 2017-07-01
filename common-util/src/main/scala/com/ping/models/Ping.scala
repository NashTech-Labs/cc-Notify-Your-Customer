package com.ping.models


case class Ping(
                 slack: Option[PingSlack] = None,
                 mail: Option[PingEmail] = None,
                 message: Option[PingMessage] = None
               )


case class PingResponse(
                         slack: Option[String],
                         mail: Option[String],
                         message: Option[String]
                       )

case class PingMessage(
                        to: String,
                        text: String
                      )

case class PingEmail(
                      to: List[String],
                      cc: List[String],
                      bcc: List[String],
                      subject: String,
                      content: String
                    )

case class PingSlack(
                      channelName: String,
                      body: String
                    )


object MessageType {
  val mail = "mail"
  val slack = "slack"
  val message = "phone_mgs"
}
