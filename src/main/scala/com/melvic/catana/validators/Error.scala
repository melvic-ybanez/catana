package com.melvic.catana.validators

import com.melvic.catana.entities.Field
import com.melvic.catana.utils.Strings

sealed trait Error {
  def field: Field

  def fieldString: String = field.toString

  def lowerFieldString: String = Strings.decapitalize(fieldString)
}

object Error {
  final case class Required(field: Field) extends Error {
    def message = s"$fieldString is required"
  }

  final case class NotANumber(field: Field, value: String) extends Error {
    def message = s"Not a number: $value"
  }

  final case class InvalidValue(field: Field, value: Long) extends Error {
    def message = s"Invalid $lowerFieldString: $value"
  }
}
