package com.example.demo.app

import tornadofx.Stylesheet
import tornadofx.box
import tornadofx.cssclass
import tornadofx.px

class Styles : Stylesheet() {
    companion object {
        val gameboard by cssclass()
    }

    init {
        gameboard {
            padding = box(10.px)
        }
    }
}