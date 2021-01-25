package com.melvic.catana.entities

import java.time.Instant

final case class User(
    id: String,
    username: String,
    password: String,
    email: String,
    name: String,
    age: Int,
    address: String,
    createdAt: Instant
)

object User {
  case object Id extends Field
  case object Username extends Field
  case object Password extends Field
  case object Email extends Field
  case object Name extends Field
  case object Age extends Field
  case object Address extends Field
  case object CreateAt extends Field
}
