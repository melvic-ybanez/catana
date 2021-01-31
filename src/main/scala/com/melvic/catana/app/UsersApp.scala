package com.melvic.catana.app

import com.melvic.catana.db.{DBContext, UsersDA}
import com.melvic.catana.entities.Users

object UsersApp {
  def add(user: Users)(implicit ctx: DBContext): Boolean = {
    val result = UsersDA.addUser(user).map(_ > 0)   /*_*/
    ctx.performIO(result)   /*_*/
  }
}
