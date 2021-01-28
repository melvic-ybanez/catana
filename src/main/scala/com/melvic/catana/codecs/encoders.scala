package com.melvic.catana.codecs

import java.time.Instant

import com.melvic.catana.db.DBContext
import com.melvic.catana.entities.Users
import io.getquill.MappedEncoding

trait encoders {
  implicit val encodeInstant: MappedEncoding[Instant, Long] =
    MappedEncoding[Instant, Long](_.toEpochMilli)

  implicit val decodeInstant: MappedEncoding[Long, Instant] =
    MappedEncoding[Long, Instant](Instant.ofEpochMilli)
}

object encoders extends encoders
