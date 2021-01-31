package com.melvic.catana.db

import com.melvic.catana.entities.Users
import com.melvic.catana.password.Common

object UsersDA {
  def addUser(user: Users)(implicit ctx: DBContext) = {
    import ctx._

    val securedUser = user.copy(password = Common.hashString(user.password))

    val quoted = quote {
      query[Users].insert(lift(securedUser))
    }
    ctx.runIO(quoted)
  }
}
