package com.ping.models


case class User (
                id: Long,
                name: String,
                address: String
                )

case class Partner(id: Option[Int], name: String, address: String)
