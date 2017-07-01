package com.ping.domain

import com.ping.models.{RDMailConfig, RDSlackConfig, RDTwilioConfig}


case class ConfigRequest(
                          mailConfig: Option[MailConfig],
                          slackConfig: Option[SlackConfig],
                          twilioConfig: Option[TwilioConfig]
                        )

case class ConfigUpdateResponse(
                                 mailConfig: Option[String],
                                 slackConfig: Option[String],
                                 twilioConfig: Option[String]
                               )

case class MailConfig(
                       email: String,
                       password: String
                     ) {
  def getRDConfig(clientId: Long): RDMailConfig = RDMailConfig(0L, clientId, email, password)
}

case class SlackConfig(
                        accessToken: String,
                        defaultChannel: String
                      ) {
  def getRDConfig(clientId: Long): RDSlackConfig = RDSlackConfig(0L, clientId, accessToken, defaultChannel)
}

case class TwilioConfig(
                         phoneNumber: String,
                         email: String,
                         password: String
                       ) {
  def getRDConfig(clientId: Long): RDTwilioConfig = RDTwilioConfig(0L, clientId, phoneNumber, email, password)
}
