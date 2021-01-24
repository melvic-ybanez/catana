package com.melvic.catana.view

import scalafx.Includes._
import com.melvic.catana.main.Resources
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, Hyperlink, Label, PasswordField, TextField}
import scalafx.scene.layout.{BorderPane, HBox, Pane, VBox}
import scalafx.scene.text.Font

object LoginView {
  def apply(): Pane = new BorderPane {
    center = new BorderPane {
      id = "controls"
      styleClass += "base-pane"

      center = new VBox {
        children = List(
          new TextField {
            promptText = "Username"
          },
          new PasswordField {
            promptText = "Password"
          }
        )
        id = "fields"
        styleClass += "base-pane"
      }
      bottom = new Button {
        text = "Login"
        maxWidth = Double.MaxValue
        defaultButton = true
      }
    }
    bottom = new HBox {
      id = "create-account-pane"
      styleClass += "base-pane"

      children ++= List(
        new Label { text = "Not registered?" },
        new Hyperlink {
          id = "create-account"
          text = "Create an account"
        }
      )
    }
    id = "login-pane"
    stylesheets += Resources.style("login")
    styleClass += "base-pane"
  }
}
