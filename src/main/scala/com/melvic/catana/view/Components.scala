package com.melvic.catana.view

import scalafx.scene.control.{PasswordField, TextField}

object Components {
  def promptField(prompt: String): TextField = new TextField { promptText = prompt }

  def promptPasswordField(prompt: String) = new PasswordField { promptText = prompt }

  def defaultPasswordField = promptPasswordField("Password")
}
