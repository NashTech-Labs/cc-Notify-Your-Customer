package com.ping.hasher

import org.mindrot.jbcrypt.BCrypt


class Hasher {

  def getHash(text: String): String = {
    BCrypt.hashpw(text, BCrypt.gensalt())
  }

}

object Hasher extends Hasher
