package com.melvic.catana.validators

import cats.implicits._
import cats.kernel.Monoid
import com.melvic.catana.entities.Field
import com.melvic.catana.validators.Error.NotANumber

trait common {
  def require[M](field: Field, value: M)(implicit M: Monoid[M]): ValidationResult[M] =
    if (M.empty == value) Error.Required(field).invalidNec else value.validNec

  def numeric(field: Field, input: String): ValidationResult[Long] =
    if (isNumeric(input)) input.toLong.validNec else NotANumber(field, input).invalidNec

  def isNumeric(input: String) = input.nonEmpty && input.forall(Character.isDigit)
}

object common extends common
