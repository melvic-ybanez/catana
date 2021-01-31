package com.melvic.catana.db

import com.melvic.catana.entities.Users
import com.melvic.catana.password._

object UsersDA {
  def addUser(user: Users)(implicit ctx: DBContext) = {
    import ctx._

    val securedUser = user.copy(password = hashString(user.password))

    val quoted = quote {
      query[Users].insert(lift(securedUser)).returningGenerated(_.id)
    }

    ctx.runIO(quoted)
  }

  def fromEmail(email: String)(implicit ctx: DBContext) = {
    import ctx._

    val quoted = quote {
      query[Users].filter(_.email == lift(email))
    }

    ctx.runIO(quoted)
  }
}

