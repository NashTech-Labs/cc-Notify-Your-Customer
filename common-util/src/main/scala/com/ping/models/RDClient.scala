package com.ping.models

import com.ping.domain.ClientView


case class RDClient(
                     id: Long,
                     name: String,
                     email: String,
                     address: String,
                     password: String,
                     accessToken: String,
                     mailEnabled: Boolean = false,
                     slackEnabled: Boolean = false,
                     twilioEnabled: Boolean = false
                   ){
  def getClientView: ClientView = ClientView(name, email, address, accessToken)
}
