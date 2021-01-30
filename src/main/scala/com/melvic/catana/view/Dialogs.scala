package com.melvic.catana.view

import scalafx.scene.control.Dialog
import scalafx.stage.Screen
import scalafx.Includes._
import javafx.stage.{Stage => JStage}

object Dialogs {
  def center[D](implicit dialog: Dialog[D]): Unit = {
    val stage = dialog.dialogPane().getScene.getWindow.asInstanceOf[JStage]
    val bounds = Screen.primary.visualBounds
    stage.x = (bounds.width - stage.getWidth) / 2
    stage.y = (bounds.height - stage.getHeight) / 2
  }
}
