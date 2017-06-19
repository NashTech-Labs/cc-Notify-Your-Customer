package com.ping.persistence.repo


import com.ping.persistence.provider.{PostGresDBProvider, DBProvider}
import com.ping.persistence.models.UserTable
import scala.concurrent.ExecutionContext.Implicits.global
import com.ping.models.User
import scala.concurrent.Future

trait UserRepo extends UserTable {

  this: DBProvider =>
  import driver.api._

  def create = db.run(userTableQuery.schema.create)

  def insert(user: User) = db.run(userTableQuery += user)

  def delete(id: Long) = {
    val query = userTableQuery.filter(x => x.id === id)
    db.run(query.delete)
  }

}

object UserRepo extends UserRepo with PostGresDBProvider