package com.melvic.catana

import com.melvic.catana.view.LoginView
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene

object Main extends JFXApp {
  stage = new PrimaryStage {
    scene = new Scene {
      root = LoginView()
    }
  }
}