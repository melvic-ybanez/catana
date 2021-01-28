package com.melvic.catana.db

import com.melvic.catana.codecs.encoders._
import com.melvic.catana.entities.Users

object UsersDA {
  def addUser(user: Users)(implicit ctx: DBContext) = {
    import ctx._

    val quoted = quote {
      query[Users].insert(lift(user))
    }
    ctx.runIO(quoted)
  }
}
