package com.ping.hasher

import com.ping.logger.PingLogger
import org.scalatest.WordSpec


class HasherSpec extends WordSpec with PingLogger{

  "A hash string" should {

    "be retrieved for an input string" in {
      val text = "Harshit"
      val hashText = Hasher.getHash(text)
      info(s"Hashed text generated:: $hashText")

      assert(hashText.nonEmpty)
    }
  }

}
