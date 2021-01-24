package com.melvic.catana.view.users

import com.melvic.catana.entities.User
import com.melvic.catana.view.components._
import jfxtras.styles.jmetro.FlatDialog
import scalafx.Includes._
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.control.{ButtonType, Dialog}
import scalafx.scene.layout.VBox
import scalafx.stage.Stage

object Register {
  def buildDialog(implicit stage: Stage) = new Dialog[User] {
    initOwner(stage)
    title = "Registration Form"
    headerText = "Registration Form"
  }

  def apply(implicit stage: Stage) = {
    val registerButton = new ButtonType("Register", ButtonData.OKDone)
    val dialog = buildDialog

    dialog.dialogPane().buttonTypes = List(
      registerButton,
      ButtonType.Cancel
    )

    val usernameField = promptField("Username")
    val passwordField = defaultPasswordField
    val emailField = promptField("Email")
    val nameField = promptField("Name")
    val addressField = promptField("Address")
    val ageField = promptField("Age")

    dialog.dialogPane().content = new VBox {
      children = List(
        usernameField,
        passwordField,
        emailField,
        nameField,
        addressField,
        ageField
      )
      styleClass += "fields-pane"
    }

    dialog
  }
}