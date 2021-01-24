package com.melvic.catana.validations

import cats.implicits._

object validators {
  def require(fieldName: String, value: String): ValidationResult[String] =
    if (value.isEmpty) Error.Required(fieldName).invalidNec else value.validNec
}
