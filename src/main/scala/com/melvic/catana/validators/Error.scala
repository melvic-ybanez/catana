package com.melvic.catana.validators

import com.melvic.catana.entities.Field

sealed trait Error {
  def field: Field

  def fieldString: String = field.toString

  def lowerFieldString: String =
    fieldString.head.toLower + fieldString.tail
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
