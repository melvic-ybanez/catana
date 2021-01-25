package com.melvic.catana.view.users

import cats.data.Validated.{Invalid, Valid}
import com.melvic.catana.entities.User
import com.melvic.catana.validators.UserValidator
import com.melvic.catana.view.components._
import jfxtras.styles.jmetro.FlatDialog
import scalafx.Includes._
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.control.{ButtonType, Dialog, TextField}
import scalafx.scene.layout.VBox
import scalafx.stage.{Stage, StageStyle}

object Register {
  def buildDialog(implicit stage: Stage) = new Dialog[Option[User]] {
    initOwner(stage)
    initStyle(StageStyle.Undecorated)
    title = "Registration Form"
    headerText = "Registration Form"
  }

  def apply(implicit stage: Stage) = {
    val registerButtonType = new ButtonType("Register", ButtonData.OKDone)
    val dialog = buildDialog

    dialog.dialogPane().buttonTypes = List(
      registerButtonType,
      ButtonType.Cancel
    )

    val usernameField = promptField("Username")
    val passwordField = defaultPasswordField
    val emailField = new TextField {
      promptText = "Email"
      styleClass += "email-field"
    }
    val nameField = promptField("Name")
    val addressField = promptField("Address")
    val ageField = promptField("Age")

    dialog.resultConverter = button =>
      if (button == registerButtonType) {
        val userData = (
          usernameField.text(),
          passwordField.text(),
          emailField.text(),
          nameField.text(),
          ageField.text(),
          addressField.text()
        )
        UserValidator.register(userData) match {
          case Valid(user) => Some(user)
          case Invalid(errors) => None
        }
      } else None

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
