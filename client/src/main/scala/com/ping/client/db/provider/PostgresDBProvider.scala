package com.ping.client.db.provider

import slick.jdbc.PostgresProfile

trait PostgresDBProvider extends DBProvider {

  val driver = PostgresProfile

  import driver.api._

  val db = Database.forConfig("client_db")

}
