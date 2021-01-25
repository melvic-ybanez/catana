package com.melvic.catana.validators

import java.time.Instant

import cats.data.NonEmptyChain
import com.melvic.catana.entities.User
import common._
import cats.implicits._
import com.melvic.catana.validators.Error.InvalidNumber

object UserValidator {
  type UserData = (String, String, String, String, String, String)

  def register: UserData => ValidationResult[User] = {
    case (username, password, email, name, age, address) => (
      require("Username", username),
      require("Password", password),
      require("Email", email),
      require("Name", name),
      validateAge(age),
      require("Address", address)
    ).mapN { (_, _, _, _, age, _) =>
      User("", username, password, email, name, age, address, Instant.now)
    }
  }

  def validateAge(input: String): ValidationResult[Int] =
    numeric(input).andThen { age =>
      if (age < 1) InvalidNumber(age).invalidNec[Int] else age.toInt.validNec[Error]
    }
}
