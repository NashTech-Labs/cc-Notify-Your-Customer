package com.ping.client.db

import java.util.UUID

import com.ping.client.db.provider.DBProvider
import slick.jdbc.H2Profile

trait ServerSpec extends DBProvider {
  val driver = H2Profile


  val SCHEMA_CLIENT = getClass.getResource("/schema_client.sql").getPath
  val SCHEMA_DATA = getClass.getResource("/client.sql").getPath
  val dbName = UUID.randomUUID().toString
  private val h2Url = s"jdbc:h2:mem:test_$dbName;MODE=PostgreSQL;DATABASE_TO_UPPER=false;INIT=" +
    s"runscript from '$SCHEMA_CLIENT'\\;" +
    s"runscript from '$SCHEMA_DATA'\\;"
  val db = driver.api.Database.forURL(url = h2Url, driver = "org.h2.Driver")
}
