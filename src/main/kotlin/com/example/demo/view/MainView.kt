package com.example.demo.view

import com.example.demo.app.Styles
import javafx.scene.paint.Color
import tornadofx.*
import java.util.*

class MainView : View("TornadoFX-opoly") {

    var pos = BoardPositions.positions
    private val player = Player(
            id = 1,
            username = "Player 1",
            position = 1,
            money = Money()
    )

    override val root = pane {
        imageview("monopoly_board.jpg")

        pos.forEach {
            circle(it.centerX, it.centerY, 5.0) {
                fill = Color.RED
            }
        }

        imageview(player.icon).apply {
            x = pos[player.position - 1].centerX - 11.0
            y = pos[player.position - 1].centerY - 11.0
        }

        prefWidth = 732.0
        prefHeight = 732.0

        // roll dice and move animation
        val diceRoll = rollDice()
        animatePlayer(diceRoll)

    }.addClass(Styles.gameboard)

    private fun rollDice(): Int = (1..12).random()

    private fun animatePlayer(spaces: Int) {
        val originalPosition = player.position
        var newPosition = 0

        if (player.position + spaces > 40) {
            newPosition = spaces - (40 - originalPosition)
            // collect 200
            player.money.m100 += 2
        } else {
            newPosition = player.position + spaces
        }

        player.position = newPosition

        // animate piece
        when {
            originalPosition in 1..11 &&  newPosition in 1..11 -> TODO() // move up
            originalPosition in 11..21 &&  newPosition in 11..21 -> TODO() // move right
            originalPosition in 21..31 &&  newPosition in 21..31 -> TODO() // move down
            originalPosition in 31..40 &&  (newPosition in 31..40 || newPosition == 1) -> TODO() // move left
            originalPosition in 1 until 11 &&  newPosition in 12 until 21 -> TODO() // move up then right
            originalPosition in 11 until 21 &&  newPosition in 22 until 31 -> TODO() // move left then down
            originalPosition in 21 until 31 &&  newPosition in 32 until 41 -> TODO() // move down then right
            originalPosition in 31 until 40 &&  newPosition in 2 until 11 -> TODO() // move right then up
        }

    }

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

data class Player(val id: Int,
                  val username: String,
                  var position: Int = 0,
                  var money: Money,
                  var ownedProperties: List<Properties> = listOf(),
                  val icon: String = "player.png")

class Money(var m1: Int = 5,
            val m5: Int = 1,
            val m20: Int = 2,
            val m50: Int = 1,
            var m100: Int = 4,
            var m500: Int = 2) {
    fun get5sAmount(): Int = m5 * 5
    fun get20sAmount(): Int = m20 * 20
    fun get50sAmount(): Int = m50 * 50
    fun get100sAmount(): Int = m100 * 100
    fun get500sAmount(): Int = m500 * 500

    fun getTotal(): Int = m1 + get5sAmount() + get20sAmount() + get50sAmount() + get100sAmount() + get500sAmount()
}

data class Properties(val propertyName: String,
                      val propertyValue: Int,
                      var redHouses: List<RedHouse> = listOf(),
                      var greenHouses: List<GreenHouse> = listOf())

data class RedHouse(val taxValue: Int)
data class GreenHouse(val taxValue: Int)

data class Position(val position: Int, val centerX: Double, val centerY: Double)