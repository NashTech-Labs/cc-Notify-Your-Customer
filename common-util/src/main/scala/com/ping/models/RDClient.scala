package com.ping.models

import com.ping.domain.ClientView


case class RDClient(
                     id: Long,
                     name: String,
                     email: String,
                     address: String,
                     password: String,
                     accessToken: String
                   ){
  def getClientView: ClientView = ClientView(name, email, address, accessToken)
}
