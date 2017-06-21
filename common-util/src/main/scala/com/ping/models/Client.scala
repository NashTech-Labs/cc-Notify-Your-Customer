package com.ping.models

import java.sql.Timestamp


case class Client(
                   id: Long,
                   name: String,
                   userName: String,
                   password: String,
                   email: String,
                   phone: String
                 )

case class ClientAddress(
                          id: Long,
                          clientId: Long,
                          address: String,
                          country: String,
                          pinCode: String
                        )


case class AccessToken(
                        clientId: Long,
                        accessToken: String,
                        createdAt: Timestamp
                      )
