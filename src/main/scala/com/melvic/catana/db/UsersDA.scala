package com.melvic.catana.db

import com.melvic.catana.entities.Users
import com.melvic.catana.password._
import io.getquill.{EntityQuery, Query}

object UsersDA {
  def addUser(user: Users)(implicit ctx: DBContext) = {
    import ctx._

    val securedUser = user.copy(password = hashString(user.password))

    val quoted = quote {
      query[Users].insert(lift(securedUser)).returningGenerated(_.id)
    }

    ctx.runIO(quoted)
  }

  def byEmail(email: String)(implicit ctx: DBContext) = {
    import ctx._

    quote {
      query[Users].filter(_.email == lift(email))
    }
  }

  def byUsername(username: String)(implicit ctx: DBContext) = {
    import ctx._

    quote {
      query[Users].filter(_.username == lift(username))
    }
  }

  def empty(ctx: DBContext)(quoted: ctx.Quoted[EntityQuery[Users]]) = {
    import ctx._
    
    val result = ctx.runIO(quoted)
    val notExists = result.map(_.isEmpty)
    ctx.performIO(notExists)
  }
}

