package com.ping.persistence.provider

import slick.jdbc.PostgresProfile

trait PostgresDBProvider extends DBProvider {

  val driver = PostgresProfile

  import driver.api._

  val db = Database.forConfig("db")

}
