package com.ping.client.db.mapping

import java.sql.Timestamp

import com.ping.client.db.provider.DBProvider
import com.ping.models.DBAccessToken
import slick.lifted.ProvenShape


private[db] trait AccessTokenMapping {
  this: DBProvider =>

  import driver.api._

  val accessTokenInfo: TableQuery[AccessTokenMapping] = TableQuery[AccessTokenMapping]

  protected def accessTokenAutoInc = accessTokenInfo returning accessTokenInfo.map(_.id)

  class AccessTokenMapping(tag: Tag) extends Table[DBAccessToken](tag, "access_token") {

    def * : ProvenShape[DBAccessToken] = (id, clientId, token, createdAt) <> (DBAccessToken.tupled, DBAccessToken.unapply)

    def id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def clientId: Rep[Long] = column[Long]("client_id")

    def token: Rep[String] = column[String]("token")

    def createdAt: Rep[Timestamp] = column[Timestamp]("created_at")

  }

}
