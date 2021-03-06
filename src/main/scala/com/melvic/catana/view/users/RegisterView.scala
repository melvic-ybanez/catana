package com.melvic.catana.view.users

import java.time.{Instant, LocalDateTime, ZoneOffset}

import cats.data.Validated.{Invalid, Valid}
import cats.implicits._
import com.melvic.catana.db.DBContext
import com.melvic.catana.entities.Users
import com.melvic.catana.entities.Users._
import com.melvic.catana.main.Resources
import com.melvic.catana.password._
import com.melvic.catana.utils.Strings
import com.melvic.catana.validators
import com.melvic.catana.validators.Error._
import com.melvic.catana.validators.UserValidator
import com.melvic.catana.view.Dialogs
import javafx.event.ActionEvent
import javafx.scene.control.Button
import scalafx.Includes._
import scalafx.beans.property.{IntegerProperty, LongProperty, StringProperty}
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.control._
import scalafx.scene.layout.{HBox, VBox}
import scalafx.stage.{Stage, StageStyle}

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
  val passwordPoints = IntegerProperty(0)

  val registerButtonType = new ButtonType("Register", ButtonData.OKDone)

  def buildDialog(implicit stage: Stage) = new RegisterDialog {
    initOwner(stage)
    initStyle(StageStyle.Undecorated)
    title = "Registration Form"
    headerText = "Registration Form"
    dialogPane().stylesheets += Resources.style("register")
  }

  def apply(implicit stage: Stage, ctx: DBContext) = {
    implicit val dialog: RegisterDialog = buildDialog

    dialog.dialogPane().buttonTypes = List(
      registerButtonType,
      ButtonType.Cancel
    )

    setResultConverter
    addEventFilter
    clearErrors()

    def errorMsg(feedbackProp: StringProperty, fieldProp: StringProperty) = {
      fieldProp.onChange {
        if (fieldProp.value.nonEmpty) feedbackProp.value = ""
      }
      feedback(feedbackProp, "field-error-message")
    }

    def feedback(prop: StringProperty, className: String) = {
      prop.onChange(repaint(dialog))

      new Label {
        styleClass += className
        text <==> prop
        visible <== prop.isNotEmpty
        managed <== visible
      }
    }

    dialog.dialogPane().content = new VBox {
      children = List(
        new TextField {
          promptText = "Username"
          text <==> username
        },
        errorMsg(usernameError, username),
        passwordField,
        errorMsg(passwordError, password),
        passwordFeedback(feedback(passwordFeedback, "feedback-warning")),
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
      val points = strengthPoints(password.value)
      val strength = Strength.category(points)
      passwordFeedback.value = Strings.displayText(strength.toString)
      passwordPoints.value = points
    }
  }

  def passwordFeedback(label: Label): HBox = {
    passwordPoints.onChange {
      val points = passwordPoints.value
      label.styleClass = label.styleClass.filter(!_.startsWith("feedback")) :+ {
        if (points < 5) "feedback-warning"
        else if (points < 7) "feedback-normal"
        else "feedback-good"
      }
    }

    new HBox {
      visible <== passwordFeedback.isNotEmpty
      managed <== passwordFeedback.isNotEmpty
      children ++= List(
        new Label { text = "Password strength:"; styleClass += "feedback-text"; },
        label
      )
      spacing = 5
    }
  }

  private def setResultConverter(implicit dialog: RegisterDialog): Unit =
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

  private def addEventFilter(implicit dialog: RegisterDialog, stage: Stage, ctx: DBContext): Unit = {
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

      UserValidator.register.apply(userData) match {
        case Invalid(errors) =>
          clearErrors()

          def error(prop: StringProperty, error: validators.Error): Unit =
            prop.value = error.message

          errors.toList.foreach {
            case err @ (Required(Username) | AlreadyExists(Username)) => error(usernameError, err)
            case err @ Required(Password) => error(passwordError, err)
            case err @ (InvalidFormat(Email, _) | AlreadyExists(Email)) => error(emailError, err)
            case err @ Required(Name) => error(nameError, err)
            case err @ (NotANumber(_, _) | InvalidValue(_, _)) => error(ageError, err)
            case err @ Required(Address) => addressError.value = err.message
          }
          repaint
          Dialogs.center
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

  private def repaint(implicit dialog: RegisterDialog): Unit =
    dialog.dialogPane().getScene.getWindow.sizeToScene()
}

object RegisterView {
  type RegisterDialog = Dialog[Option[Users]]

  def apply(implicit stage: Stage, ctx: DBContext) = new RegisterView {}.apply
}
