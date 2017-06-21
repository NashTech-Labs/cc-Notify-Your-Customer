package com.ping.models

import java.sql.Timestamp


case class DBClient(
                   id: Long,
                   name: String,
                   userName: String,
                   password: String,
                   email: String,
                   phone: String
                 )

case class DBClientAddress(
                          id: Long,
                          clientId: Long,
                          address: String,
                          country: String,
                          pinCode: String
                        )


case class DBAccessToken(
                        id: Long,
                        clientId: Long,
                        token: String,
                        createdAt: Timestamp
                      )
