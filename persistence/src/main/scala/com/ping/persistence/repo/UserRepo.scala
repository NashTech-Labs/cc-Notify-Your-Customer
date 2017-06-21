package com.ping.persistence.repo


import com.ping.persistence.provider.{PostgresDBProvider, DBProvider}
import com.ping.persistence.mapping.UserMapping
import scala.concurrent.ExecutionContext.Implicits.global
import com.ping.models.User
import scala.concurrent.Future

trait UserRepo extends UserMapping {
  this: DBProvider =>

  import driver.api._

  def insert(user: User): Future[Int] = db.run(userInfo += user)

  def delete(id: Long) = {
    val query = userInfo.filter(x => x.id === id)
    db.run(query.delete)
  }

}

object UserRepo extends UserRepo with PostgresDBProvider
