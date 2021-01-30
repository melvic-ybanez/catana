package com.melvic.catana.validators

import cats.implicits._
import cats.kernel.Monoid
import com.melvic.catana.entities.Field
import com.melvic.catana.validators.Error.NotANumber

trait Common {
  def require[M](field: Field, value: M)(implicit M: Monoid[M]): ValidationResult[M] =
    if (M.empty == value) Error.Required(field).invalidNec else value.validNec

  def numeric(field: Field, input: String): ValidationResult[Long] =
    if (isNumeric(input)) input.toLong.validNec else NotANumber(field, input).invalidNec

  def isUnsignedNumeric(input: String) = input.nonEmpty && input.forall(Character.isDigit)

  def isNumeric(input: String) =
    if (input.length > 1)
      input.head == '-' && isUnsignedNumeric(input.tail) || isUnsignedNumeric(input)
    else isUnsignedNumeric(input)
}

object Common extends Common
