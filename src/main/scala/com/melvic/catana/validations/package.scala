package com.melvic.catana

import cats.data.ValidatedNec

package object validations {
  type ValidationResult[A] = ValidatedNec[Error, A]
}
