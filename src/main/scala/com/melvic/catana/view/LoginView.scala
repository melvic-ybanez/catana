package com.melvic.catana.view

import com.melvic.catana.db.{DBContext, UsersDA}
import com.melvic.catana.entities.Users
import com.melvic.catana.main.Resources
import com.melvic.catana.view.components._
import com.melvic.catana.view.users.RegisterView
import scalafx.Includes._
import scalafx.scene.control.{Button, Hyperlink, Label}
import scalafx.scene.layout.{BorderPane, HBox, Pane, VBox}
import scalafx.stage.Stage

object LoginView {
  def apply(implicit dbCtx: DBContext, stage: Stage): Pane = new BorderPane {
    center = new BorderPane {
      id = "controls"

      center = new VBox {
        children = List(
          promptField("Username"),
          defaultPasswordField
        )
        styleClass += "fields-pane"
      }
      bottom = new Button {
        text = "Login"
        maxWidth = Double.MaxValue
        defaultButton = true
      }
    }
    bottom = new HBox {
      id = "create-account-pane"

      children ++= List(
        new Label { text = "Not registered?" },
        new Hyperlink {
          id = "create-account"
          text = "Create an account"
          onAction = _ => RegisterView.apply.showAndWait() match {
            case Some(Some(user: Users)) =>
              val result = UsersDA.addUser(user).map { rowCount =>
                if (rowCount > 0) println("User successfully inserted")
                else println("Unable to insert user")
              }
              /*_*/ dbCtx.performIO(result) /*_*/
            case None => println("Nothing to do here.")
          }
        }
      )
    }
    id = "login-pane"
    stylesheets += Resources.style("login")
  }
}
