package com.example.demo.view

import com.example.demo.app.Styles
import javafx.scene.paint.Color
import tornadofx.*
import java.util.*

class MainView : View("TornadoFX-opoly") {
    override val root = pane {
        imageview("monopoly_board.jpg")

        BoardPositions.positions.forEach {
            circle(it.centerX, it.centerY, 5.0) {
                fill = Color.RED
            }
        }

        prefWidth = 732.0
        prefHeight = 732.0
    }.addClass(Styles.gameboard)

    private fun rollDice(): Int = (1..6).random()

    private fun ClosedRange<Int>.random() = Random().nextInt(endInclusive - start) + start
}

object BoardPositions {
    private val positionsById = BoardPositions::class.java.getResource("/positions.csv").readText().lines()
            .asSequence()
            .map { it.split(",") }
            .map { Position(it[0].toInt(), it[1].toDouble(), it[2].toDouble()) }
            .map { it.position to it }
            .toMap()

    val positions = positionsById.values.toList()
}

data class Player(val username: String, val id: Int, val position: Int)
data class Position(val position: Int, val centerX: Double, val centerY: Double)