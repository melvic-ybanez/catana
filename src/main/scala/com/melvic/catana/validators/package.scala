package com.melvic.catana

import cats.data.ValidatedNec

package object validators extends Common {
  type ValidationResult[A] = ValidatedNec[Error, A]
}
