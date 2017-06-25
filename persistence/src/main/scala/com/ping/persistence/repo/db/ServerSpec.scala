package com.ping.persistence.repo.db

import java.util.UUID

import com.ping.persistence.provider.DBProvider
import slick.jdbc.H2Profile

trait ServerSpec extends DBProvider {
  val driver = H2Profile

  val SCHEMA = getClass.getResource("/schema.sql").getPath
  val SCHEMA_DATA = getClass.getResource("/persistence.sql").getPath
  val dbName = UUID.randomUUID().toString
  private val h2Url = s"jdbc:h2:mem:test_$dbName;MODE=PostgreSQL;DATABASE_TO_UPPER=false;INIT=" +
    s"runscript from '$SCHEMA'\\;" +
    s"runscript from '$SCHEMA_DATA'\\;"
  val db = driver.api.Database.forURL(url = h2Url, driver = "org.h2.Driver")
}
