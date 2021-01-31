package com.melvic.catana.email

import org.apache.commons.validator.routines.EmailValidator

trait Common {
  def isValid(rawEmail: String): Boolean = {
    val validator = EmailValidator.getInstance()
    validator.isValid(rawEmail)
  }
}

object Common extends Common
