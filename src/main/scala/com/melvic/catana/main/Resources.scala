package com.melvic.catana.main

object Resources {
  def style(name: String) = this.getClass.getResource(s"resources/styles/$name.css").toExternalForm
}
