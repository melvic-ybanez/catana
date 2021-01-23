package com.melvic.catana.view

import scalafx.scene.control.{Button, PasswordField, TextField}
import scalafx.scene.layout.{BorderPane, Pane, VBox}

object LoginView {
  def apply(): Pane = new BorderPane {
    center = new VBox {
      children = List(
        new TextField { promptText = "Username" },
        new PasswordField { promptText = "Password" }
      )
    }
    bottom = new BorderPane {
      right = new Button { text = "Login" }
    }
  }
}
