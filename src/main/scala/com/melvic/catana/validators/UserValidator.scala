package com.melvic.catana.validators

import java.time.{LocalDateTime, ZoneOffset}

import cats.Contravariant
import cats.implicits._
import com.melvic.catana.db.{DBContext, UsersDA}
import com.melvic.catana.entities.Users
import com.melvic.catana.entities.Users._
import com.melvic.catana.validators.Error.{AlreadyExists, InvalidFormat, InvalidValue}
import com.melvic.catana.{email => E}

object UserValidator {
  type UserData = (String, String, String, String, String, String)
  type Validation = UserData => ValidationResult[Users]

  def register(implicit ctx: DBContext): Validation = Contravariant[* => ValidationResult[Users]]
    .contramap(registerRaw)(stripSpaces)

  def registerRaw(implicit ctx: DBContext): Validation = {
    case (username, password, email, name, age, address) => (
      validateUsername(username),
      require(Password, password),
      validateEmail(email),
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

  def validateEmail(rawEmail: String)(implicit ctx: DBContext): ValidationResult[String] =
    if (E.isValid(rawEmail)) {
      val validEmail = rawEmail.validNec
      validEmail.andThen { email =>  /*_*/
        val unique = UsersDA.empty(ctx)(UsersDA.byEmail(email))
        if (unique) validEmail else AlreadyExists(Email).invalidNec    /*_*/
      }
    } else InvalidFormat(Email, rawEmail).invalidNec

  def validateUsername(rawUsername: String)(implicit ctx: DBContext): ValidationResult[String] = {
    val required = require(Username, rawUsername)
    required.andThen { username =>    /*_*/
      val unique = UsersDA.empty(ctx)(UsersDA.byUsername(username))
      if (unique) required else AlreadyExists(Username).invalidNec    /*_*/
    }
  }

  def stripSpaces: UserData => UserData = { case (username, password, email, name, age, address) =>
    (username.trim, password, email.trim, name.trim, age.trim, address.trim)
  }
}
