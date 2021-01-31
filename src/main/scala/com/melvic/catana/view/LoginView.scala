package com.melvic.catana.view

import com.melvic.catana.app.UsersApp
import com.melvic.catana.db.DBContext
import com.melvic.catana.entities.Users
import com.melvic.catana.main.Resources
import com.melvic.catana.view.Components._
import com.melvic.catana.view.users.RegisterView
import scalafx.Includes._
import scalafx.scene.control.{Button, Hyperlink, Label}
import scalafx.scene.layout.{BorderPane, HBox, Pane, VBox}
import scalafx.stage.Stage

object LoginView {
  def apply(implicit ctx: DBContext, stage: Stage): Pane = new BorderPane {
    center = new BorderPane {
      id = "controls"

      center = new VBox {
        children = List(
          promptField("Username"),
          defaultPasswordField
        )
        styleClass += "fields-pane"
      }
      bottom = new VBox {
        children ++= List(
          new Button {
            text = "Login"
            maxWidth = Double.MaxValue
            defaultButton = true
          },
          new Hyperlink {
            id = "forgot-password"
            text = "Forgot Password"
          }
        )
      }
    }
    bottom = createAccountPane
    id = "login-pane"
    stylesheets += Resources.style("login")
  }

  private def createAccountPane(implicit ctx: DBContext, stage: Stage) = new VBox {
    val messageField = new Label { id = "create-account-success" }

    children ++= List(
      new HBox {
        id = "create-account-pane"

        children ++= List(
          new Label { text = "Not registered?" },
          new Hyperlink {
            id = "create-account"
            text = "Create an account"
            onAction = _ => RegisterView.apply.showAndWait() match {
              case Some(Some(user: Users)) => toggleMessage(UsersApp.add(user))
              case Some(_) => ()
              case None => toggleMessage(false)
            }
          }
        )
      },
      messageField
    )

    def toggleMessage(success: Boolean): Unit =
      if (success) {
        messageField.text = "User successfully registered"
        messageField.id = "create-account-success"
      } else {
        messageField.text = "Unable to register user"
        messageField.id = "create-account-error"
      }
  }
}
