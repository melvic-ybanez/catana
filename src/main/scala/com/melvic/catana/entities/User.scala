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
