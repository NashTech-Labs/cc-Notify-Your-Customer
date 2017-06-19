package com.ping.persistence.repo

import scala.concurrent.ExecutionContext.Implicits.global
import com.ping.db.DbContext._

import com.ping.models.User

import scala.concurrent.Future

class UserRepo {


  val users: Quoted[EntityQuery[User]] = quote(query[User])

//  def find(id: Long): Future[Option[User]] = run(users.filter(_.id == lift(id))).map(_.headOption)

//  def insert(user: User): Future[User] = run(users.insert(lift(user)).returning(_.id)).map(autoIncId => user.copy(id = autoIncId))

}
