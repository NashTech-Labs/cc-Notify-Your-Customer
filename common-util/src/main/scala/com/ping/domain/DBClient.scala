package com.ping.domain

import java.sql.Timestamp


case class DBClient(
                   id: Long,
                   name: String,
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

case class ClientDetails (
                         client: DBClient,
                         clientAddress: DBClientAddress,
                         tokenDetails: DBAccessToken
                         )
