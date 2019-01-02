package com.example.demo.app

import javafx.scene.text.FontWeight
import tornadofx.Stylesheet
import tornadofx.box
import tornadofx.cssclass
import tornadofx.px

class Styles : Stylesheet() {
    companion object {
        val heading by cssclass()
        val gameboard by cssclass()
    }

    init {
        gameboard {
            padding = box(10.px)
        }
    }
}