package com.melvic.catana.passwords

sealed trait Criterion

object Criterion {
  case object Length extends Criterion
  case object Alpha extends Criterion
  case object Letter extends Criterion
  case object Number extends Criterion
  case object Special extends Criterion
  case object Upper extends Criterion

  lazy val points: Map[Criterion, Int] = Map(
    Length -> 2,
    Letter -> 1,
    Number -> 1,
    Special -> 2,
    Upper -> 2
  )
}
