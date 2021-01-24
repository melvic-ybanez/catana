package com.melvic.catana.validations

sealed trait Error

object Error {
  final case class Required(fieldName: String) extends Error
}
