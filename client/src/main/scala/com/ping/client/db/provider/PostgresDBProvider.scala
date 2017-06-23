package com.ping.client.db.provider

import slick.jdbc.PostgresProfile

trait PostgresDBProvider extends DBProvider {

  val driver = PostgresProfile

  val db = driver.api.Database.forConfig("client_db")

}
