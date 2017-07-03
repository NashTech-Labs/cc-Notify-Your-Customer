package com.ping.models


case class RDSlackConfig(
                          id: Long,
                          clientId: Long,
                          token: String,
                          defaultChannel: String
                        )
