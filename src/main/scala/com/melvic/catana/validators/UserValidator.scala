package com.melvic.catana.validators

import java.time.{Instant, LocalDateTime, ZoneOffset}

import com.melvic.catana.entities.Users
import com.melvic.catana.entities.Users._
import common._
import cats.implicits._
import com.melvic.catana.validators.Error.InvalidValue

object UserValidator {
  type UserData = (String, String, String, String, String, String)

  def register: UserData => ValidationResult[Users] = {
    case (username, password, email, name, age, address) => (
      require(Username, username),
      require(Password, password),
      require(Email, email),
      require(Name, name),
      validateAge(age),
      require(Address, address)
    ).mapN { (_, _, _, _, age, _) =>
      Users.default(
        Username(username),
        Password(password),
        Email(email),
        Name(name),
        Address(address),
        age,
        LocalDateTime.now(ZoneOffset.UTC)
      )
    }
  }

  def validateAge(input: String): ValidationResult[Int] =
    numeric(Age, input).andThen { age =>
      if (age < 1) InvalidValue(Age, age).invalidNec[Int] else age.toInt.validNec[Error]
    }
}
