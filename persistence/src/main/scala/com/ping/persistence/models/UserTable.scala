package com.ping.persistence.models

import com.ping.models.User
import com.ping.persistence.provider.DBProvider

trait UserTable {
  this: DBProvider =>

  import driver.api._

  def userTableQuery = TableQuery[UserTable]

  class UserTable(tag: Tag) extends Table[User](tag, "User") {
    val id = column[Long]("id", O.PrimaryKey)
    val name = column[String]("name")
    val address = column[String]("address")

    def * = (id, name, address) <>(User.tupled, User.unapply)

  }

}

