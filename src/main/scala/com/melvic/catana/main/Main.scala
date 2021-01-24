package com.melvic.catana.main

import com.melvic.catana.view.Login
import jfxtras.styles.jmetro.{JMetro, Style}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.{Group, Scene}
import scalafx.Includes._
import scalafx.scene.layout.BorderPane

object Main extends JFXApp {
  stage = new PrimaryStage {
    title = "Catana"
    scene = mainScene
    maximized = true
  }

  lazy val mainScene = new Scene {
    stylesheets += Resources.style("base")
  }

  val jMetro = new JMetro(Style.DARK)
  jMetro.setScene(mainScene)
  jMetro.setAutomaticallyColorPanes(true)

  // Update the root only after JMetro has been applied
  // to the main scene
  mainScene.root = new BorderPane {
    center = new Group { children += Login(stage) }
  }
}
