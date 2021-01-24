package com.melvic.catana.main

import com.melvic.catana.view.LoginView
import jfxtras.styles.jmetro.{JMetro, Style}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.{Group, Scene}
import scalafx.Includes._
import scalafx.scene.layout.BorderPane

object Main extends JFXApp {
  val mainScene = new Scene {
    root = new BorderPane {
      center = new Group { children += LoginView() }
    }
    stylesheets += Resources.style("base")
  }

  stage = new PrimaryStage {
    title = "Catana"
    scene = mainScene
    maximized = true
  }

  val jMetro = new JMetro(Style.LIGHT)
  jMetro.setScene(mainScene)
  jMetro.setAutomaticallyColorPanes(true)
}
