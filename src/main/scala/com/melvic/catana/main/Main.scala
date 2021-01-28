package com.melvic.catana.main

import com.melvic.catana.db.DBContext
import com.melvic.catana.view.LoginView
import com.typesafe.config.ConfigFactory
import io.getquill.util.LoadConfig
import io.getquill.util.LoadConfig.getClass
import io.getquill.{PostgresJdbcContext, SnakeCase}
import jfxtras.styles.jmetro.{JMetro, Style}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.{Group, Scene}
import scalafx.Includes._
import scalafx.scene.layout.BorderPane
import scalafx.stage.Stage

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

  implicit lazy val dbContext: DBContext = new PostgresJdbcContext(SnakeCase, "ctx")
  implicit val stageContext: Stage = stage

  // Update the root only after JMetro has been applied
  // to the main scene
  mainScene.root = new BorderPane {
    center = new Group { children += LoginView.apply }
  }
}
