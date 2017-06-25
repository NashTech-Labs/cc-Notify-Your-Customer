package com.ping.persistence.repo

import com.ping.models.User
import com.ping.persistence.repo.db.ServerSpec
import org.scalatest.AsyncWordSpec


class UserRepoSpec extends AsyncWordSpec with UserRepo with ServerSpec {

  "inserting user" should {

    "respond successfully" in {
      val user = User(0, "Girish", "Delhi")
      insert(user) map { resp =>
        assert(resp.id === 2)
      }
    }

  }

  "deleting user" should {

    "successfully delete with valid userId" in {
      delete(1) map { count =>
        assert(count === 1)
      }
    }
  }

}
