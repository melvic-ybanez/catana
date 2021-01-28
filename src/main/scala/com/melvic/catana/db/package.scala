package com.melvic.catana

import io.getquill._

package object db {
  type DBContext = PostgresJdbcContext[SnakeCase]
}
