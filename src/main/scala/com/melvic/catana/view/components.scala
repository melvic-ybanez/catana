package com.melvic.catana.view

import javax.swing.{JButton, JTextField}
import scalafx.scene.control.{PasswordField, TextField}

object components {
  def promptField(prompt: String): TextField = new TextField { promptText = prompt }

  def promptPasswordField(prompt: String) = new PasswordField { promptText = prompt }

  def defaultPasswordField = promptPasswordField("Password")
}
