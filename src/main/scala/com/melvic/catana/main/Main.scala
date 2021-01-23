package com.melvic.catana.main

import com.melvic.catana.view.LoginView
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.{Group, Scene}
import scalafx.Includes._
import scalafx.scene.layout.BorderPane

object Main extends JFXApp {
  stage = new PrimaryStage {
    scene = new Scene {
      title = "Catana"
      root = new BorderPane {
        center = new Group { children += LoginView() }
      }
      stylesheets += Resources.style("base")
    }
    maximized = true
  }
}
