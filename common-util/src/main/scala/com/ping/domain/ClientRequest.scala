package com.ping.domain

import com.ping.models.RDClient

case class ClientRequest(
                          name: String,
                          email: String,
                          address: String,
                          password: String
                        ) {
  def getRDClient(accessToken: String) = RDClient(0L, name, email, address, password, accessToken)
}

case class ClientView(
                       name: String,
                       email: String,
                       address: String,
                       accessToken: String
                     )
