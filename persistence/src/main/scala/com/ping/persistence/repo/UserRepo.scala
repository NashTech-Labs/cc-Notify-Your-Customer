package com.ping.persistence.repo


import com.ping.persistence.provider.{PostgresDBProvider, DBProvider}
import com.ping.persistence.mapping.UserMapping
import scala.concurrent.ExecutionContext.Implicits.global
import com.ping.models.User
import scala.concurrent.Future

trait UserRepo extends UserMapping {
  this: DBProvider =>

  import driver.api._

  def insert(user: User): Future[User] = withTransaction {
    (userAutoInc += user) map { incId =>
      user.copy(id = incId)
    }
  }

  def delete(id: Long) = withTransaction {
    userInfo.filter(x => x.id === id).delete
  }

}

object UserRepo extends UserRepo with PostgresDBProvider
