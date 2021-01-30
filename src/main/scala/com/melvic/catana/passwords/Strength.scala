package com.melvic.catana.passwords

sealed trait Strength

object Strength {
  case object VeryWeak extends Strength
  case object Weak extends Strength
  case object Normal extends Strength
  case object Strong extends Strength
  case object VeryStrong extends Strength

  def category(points: Int): Strength =
    if (points > 8) VeryStrong
    else if (points > 6) Strong
    else if (points > 4) Normal
    else if (points > 2) Weak
    else VeryWeak
}
