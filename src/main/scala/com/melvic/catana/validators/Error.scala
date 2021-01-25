package com.melvic.catana.validators

sealed trait Error

object Error {
  final case class Required(fieldName: String) extends Error
  final case class NotANumber(value: String) extends Error
  final case class InvalidNumber(value: Long) extends Error
}
