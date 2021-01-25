package com.melvic.catana.validators

import com.melvic.catana.entities.Field

sealed trait Error

object Error {
  final case class Required(field: Field) extends Error {
    def message = s"$field is required"
  }

  final case class NotANumber(field: Field, value: String) extends Error {
    def message = s"Not a number: $value"
  }

  final case class InvalidNumber(field: Field, value: Long) extends Error {
    def message = s"Invalid number: $value"
  }
}
