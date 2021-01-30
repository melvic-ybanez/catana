package com.melvic.catana.passwords

import java.security.MessageDigest

object Passwords {
  import Criterion.{Number => CNumber, _}

  def strengthPoints(implicit password: String): Int = {
    val lengthPoints = password.length / 8 * points(Length)
    val letterPoints = countFactor(_.isLetter) * points(Letter)
    val numberPoints = countFactor(_.isDigit) * points(CNumber)
    val specialPoints = countFactor(!Character.isLetterOrDigit(_)) * points(Special)
    val upperPoints = countFactor(_.isUpper) * points(Upper)
    lengthPoints + letterPoints + numberPoints + specialPoints + upperPoints
  }

  def strengthCategory(password: String): Strength =
    Strength.category(strengthPoints(password))

  def countFactor(p: Char => Boolean)(implicit password: String): Int = {
    val count = password.count(p)
    if (count > 1) 2 else if (count > 0) 1 else 0
  }

  def hash(password: String): Array[Byte] = {
    val digest = MessageDigest.getInstance("SHA-256")
    digest.update(password.getBytes("UTF-8"))
    digest.digest()
  }

  def hashString(password: String): String = hash(password).mkString
}
