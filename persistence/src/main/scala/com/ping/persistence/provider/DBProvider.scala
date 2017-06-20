package com.ping.persistence.provider

import slick.jdbc.JdbcProfile

trait DBProvider {

  val driver: JdbcProfile

  import driver.api._

  val db: Database

}