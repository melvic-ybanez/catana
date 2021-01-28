package com.melvic.catana.entities

import java.time.LocalDateTime

final case class Users(
    id: Long,
    username: String,
    password: String,
    email: String,
    createdAt: LocalDateTime,
    name: String,
    age: Int,
    address: String
)

object Users {
  def default(
      username: Username,
      password: Password,
      email: Email,
      name: Name,
      address: Address,
      age: Int,
      createdAt: LocalDateTime
  ): Users = Users(
    0,
    username.value,
    password.value,
    email.value,
    createdAt,
    name.value,
    age,
    address.value
  )

  final case class Username(value: String) extends AnyVal
  final case class Password(value: String) extends AnyVal
  final case class Name(value: String) extends AnyVal
  final case class Email(value: String) extends AnyVal
  final case class Address(value: String) extends AnyVal

  case object Id extends Field
  case object Username extends Field
  case object Password extends Field
  case object Email extends Field
  case object Name extends Field
  case object Age extends Field
  case object Address extends Field
  case object CreateAt extends Field
}
