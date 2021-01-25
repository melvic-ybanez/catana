package com.melvic.catana.validators

import cats.implicits._
import cats.kernel.Monoid
import com.melvic.catana.validators.Error.NotANumber

trait common {
  def require[M](fieldName: String, value: M)(implicit M: Monoid[M]): ValidationResult[M] =
    if (M.empty == value) Error.Required(fieldName).invalidNec else value.validNec

  def numeric(input: String): ValidationResult[Long] =
    if (isNumeric(input)) input.toLong.validNec else NotANumber(input).invalidNec

  def isNumeric(input: String) = input.forall(Character.isDigit)
}

object common extends common
