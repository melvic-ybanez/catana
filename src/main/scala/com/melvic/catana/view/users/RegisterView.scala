package com.melvic.catana.view.users

import java.time.{Instant, LocalDateTime, ZoneOffset}

import cats.data.Validated.{Invalid, Valid}
import com.melvic.catana.entities.Users
import com.melvic.catana.validators.UserValidator
import javafx.event.ActionEvent
import javafx.scene.control.Button
import scalafx.Includes._
import scalafx.beans.property.{LongProperty, StringProperty}
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.control.{ButtonType, Dialog, Label, PasswordField, TextField}
import scalafx.scene.layout.VBox
import scalafx.stage.{Stage, StageStyle}
import cats.implicits._
import com.melvic.catana.entities.Users.{Address, Email, Name, Password, Username}
import com.melvic.catana.validators.Error.{InvalidValue, NotANumber, Required}
import com.melvic.catana.passwords.{Password => PasswordUtils}
import com.melvic.catana.utils.Strings

class RegisterView {
  import RegisterView._

  val username = StringProperty("")
  val password = StringProperty("")
  val email = StringProperty("")
  val name = StringProperty("")
  val address = StringProperty("")
  val age = StringProperty("")
  val createdAt = LongProperty(Instant.now.toEpochMilli)

  val usernameError = StringProperty("")
  val passwordError = StringProperty("")
  val emailError = StringProperty("")
  val nameError = StringProperty("")
  val addressError = StringProperty("")
  val ageError = StringProperty("")

  val passwordFeedback = StringProperty("")

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

    setResultConverter(dialog)
    addEventFilter(dialog)

    def errorMsg(feedbackProp: StringProperty, fieldProp: StringProperty) = {
      fieldProp.onChange {
        if (fieldProp.value.nonEmpty) {
          feedbackProp.value = ""
          repaint(dialog)
        }
      }
      feedback(feedbackProp, "field-error-message")
    }

    def feedback(prop: StringProperty, className: String) = {
      new Label {
        styleClass += className
        text <==> prop
        visible <== prop.isNotEmpty
        managed <== visible
      }
    }

    clearErrors()

    dialog.dialogPane().content = new VBox {
      children = List(
        new TextField {
          promptText = "Username"
          text <==> username
        },
        errorMsg(usernameError, username),
        passwordField,
        errorMsg(passwordError, password),
        feedback(passwordFeedback, "feedback-text"),
        new TextField {
          promptText = "Email"
          styleClass += "email-field"
          text <==> email
        },
        errorMsg(emailError, email),
        new TextField { promptText = "Name"; text <==> name },
        errorMsg(nameError, name),
        new TextField { promptText = "Address"; text <==> address },
        errorMsg(addressError, address),
        new TextField { promptText = "Age"; text <==> age },
        errorMsg(ageError, age)
      )
      styleClass += "fields-pane"
    }

    dialog
  }

  def passwordField = new PasswordField {
    promptText = "Password"
    text <==> password
    onKeyReleased = { _ =>
      val strength = PasswordUtils.strengthCategory(password.value)
      val strengthLevel = Strings.displayText(strength.toString)
      passwordFeedback.value = s"Strength: $strengthLevel"
    }
  }

  private def setResultConverter(dialog: RegisterDialog): Unit =
    dialog.resultConverter = button =>
      if (button == registerButtonType) Some(
        Users.default(
          Username(username.value),
          Password(password.value),
          Email(email.value),
          Name(name.value),
          Address(address.value),
          age.value.toInt,
          LocalDateTime.now(ZoneOffset.UTC),
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
          clearErrors()
          errors.toList.foreach {
            case err @ Required(Username) => usernameError.value = err.message
            case err @ Required(Password) => passwordError.value = err.message
            case err @ Required(Email) => emailError.value = err.message
            case err @ Required(Name) => nameError.value = err.message
            case err @ NotANumber(_, _) => ageError.value = err.message
            case err @ InvalidValue(_, _) => ageError.value = err.message
            case err @ Required(Address) => addressError.value = err.message
          }
          repaint(dialog)
          event.consume()
        case Valid(user) =>
          age.value = user.age.toString
          createdAt.value = user.createdAt.toInstant(ZoneOffset.UTC).toEpochMilli
      }
    })
  }

  def clearErrors(): Unit = {
    usernameError.value = ""
    passwordError.value = ""
    emailError.value = ""
    nameError.value = ""
    ageError.value = ""
    addressError.value = ""
  }

  private def repaint(dialog: RegisterDialog): Unit = dialog.dialogPane().getScene.getWindow.sizeToScene()
}

object RegisterView {
  type RegisterDialog = Dialog[Option[Users]]

  def apply(implicit stage: Stage) = new RegisterView {}.apply
}
