package com.melvic.catana.view

import com.melvic.catana.main.Resources
import com.melvic.catana.view.components._
import com.melvic.catana.view.users.Register
import scalafx.Includes._
import scalafx.scene.control.{Button, Hyperlink, Label}
import scalafx.scene.layout.{BorderPane, HBox, Pane, VBox}
import scalafx.stage.Stage

object Login {
  def apply(implicit stage: Stage): Pane = new BorderPane {
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
          onAction = _ => Register.apply.showAndWait()
        }
      )
    }
    id = "login-pane"
    stylesheets += Resources.style("login")
  }
}
