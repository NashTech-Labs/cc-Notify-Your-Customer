package com.ping.hasher

import org.scalatest.WordSpec


class HasherSpec extends WordSpec {

  "A hash string" should {

    "be retrieved for an input string" in {
      val text = "Harshit"
      val hashText = Hasher.getHash(text)
      println(s"Hashed text generated:: $hashText")

      assert(hashText.nonEmpty)
    }
  }

}
