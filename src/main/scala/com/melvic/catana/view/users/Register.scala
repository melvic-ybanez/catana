package com.melvic.catana.view.users

import java.time.Instant

import cats.data.Validated.{Invalid, Valid}
import com.melvic.catana.entities.User
import com.melvic.catana.validators.UserValidator
import com.melvic.catana.view.components._
import javafx.event.ActionEvent
import javafx.scene.control.Button
import scalafx.Includes._
import scalafx.beans.property.{IntegerProperty, LongProperty, StringProperty}
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.control.{ButtonType, Dialog, PasswordField, TextField}
import scalafx.scene.layout.VBox
import scalafx.stage.{Stage, StageStyle}

class Register {
  import Register._

  val username = StringProperty("")
  val password = StringProperty("")
  val email = StringProperty("")
  val name = StringProperty("")
  val address = StringProperty("")
  val age = StringProperty("")
  val createdAt = LongProperty(Instant.now.toEpochMilli)

  val registerButtonType = new ButtonType("Register", ButtonData.OKDone)

  def buildDialog(implicit stage: Stage) = new RegisterDialog {
    initOwner(stage)
    initStyle(StageStyle.Undecorated)
    title = "Registration Form"
    headerText = "Registration Form"
  }

  def apply(implicit stage: Stage) = {
    val dialog = buildDialog

    dialog.dialogPane().buttonTypes = List(
      registerButtonType,
      ButtonType.Cancel
    )

    addResultConverter(dialog)
    addEventFilter(dialog)

    dialog.dialogPane().content = new VBox {
      children = List(
        new TextField {
          promptText = "Email"
          styleClass += "email-field"
          text <==> username
        },
        new PasswordField { promptText = "Password"; text <==> password },
        new TextField {
          promptText = "Email"
          styleClass += "email-field"
          text <==> email
        },
        new TextField { promptText = "Name"; text <==> name },
        new TextField { promptText = "Address"; text <==> address },
        new TextField { promptText = "Age"; text <==> age }
      )
      styleClass += "fields-pane"
    }

    dialog
  }

  private def addResultConverter(dialog: RegisterDialog): Unit =
    dialog.resultConverter = button =>
      if (button == registerButtonType) Some(
        User(
          "",
          username.value,
          password.value,
          email.value,
          name.value,
          age.value.toInt,
          address.value,
          Instant.ofEpochMilli(createdAt.value)
        )
      ) else None

  private def addEventFilter(dialog: RegisterDialog): Unit = {
    val registerButton = dialog.getDialogPane
      .lookupButton(registerButtonType).asInstanceOf[Button]

    registerButton.addEventFilter(ActionEvent.ACTION, { event: ActionEvent =>
      val userData = (
        username.value,
        password.value,
        email.value,
        name.value,
        age.value,
        address.value
      )
      UserValidator.register(userData) match {
        case Invalid(errors) =>
          event.consume()
        case Valid(user) =>
          age.value = user.age.toString
          createdAt.value = user.createdAt.toEpochMilli
      }
    })
  }
}

object Register {
  type RegisterDialog = Dialog[Option[User]]

  def apply(implicit stage: Stage) = new Register {}.apply
}
