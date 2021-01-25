package com.melvic.catana

import cats.data.ValidatedNec

package object validators {
  type ValidationResult[A] = ValidatedNec[Error, A]
}
