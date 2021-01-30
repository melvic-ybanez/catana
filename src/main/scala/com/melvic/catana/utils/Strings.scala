package com.melvic.catana.utils

object Strings {
  def displayText(input: String): String =
    input.foldLeft("") { (acc, c) =>
      if (c.isUpper) if (acc.isEmpty) c.toString else acc + " " + c
      else acc + c
    }

  def decapitalize(input: String) = input.head.toLower + input.tail
}
