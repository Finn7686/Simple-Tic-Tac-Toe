package tictactoe

import kotlin.math.absoluteValue

fun main() {
    val field = mutableListOf(
        mutableListOf('_', '_', '_'),
        mutableListOf('_', '_', '_'),
        mutableListOf('_', '_', '_')
    )
    printField(field)

    var activePlayer = 'X'
    var nonActivePlayer = 'O'
    while (true) {
        if (turn(field, activePlayer)) {
            activePlayer = nonActivePlayer.also { nonActivePlayer = activePlayer }
            printField(field)
            if (analyzeGame(field) != "Game not finished") {
                println(analyzeGame(field))
                break
            }
        }
    }
}

private fun turn(field: MutableList<MutableList<Char>>, player: Char): Boolean {
    try {
        placeMarker(field, player)
        return true
    } catch (e: NumberFormatException) {
        println("You should enter numbers!")
    } catch (e: ArrayIndexOutOfBoundsException) {
        println("Coordinates should be from 1 to 3!")
    } catch (e: Exception) {
        println(e.message)
    }
    return false
}

private fun placeMarker(field: MutableList<MutableList<Char>>, player: Char) {
    val (x, y) = readln().split(" ").map { it.toInt() - 1 }
    if (field[x][y] != '_') throw Exception("This cell is occupied! Choose another one!")
    field[x][y] = player
}

fun gameIsFinished(field: MutableList<MutableList<Char>>): Boolean {
    for (i in field)
        if ('_' in i) return false
    return true
}

fun analyzeGame(field: MutableList<MutableList<Char>>): String {
    val xWins = isWinner(field, 'X')
    val oWins = isWinner(field, 'O')

    var totalX = 0
    var totalO = 0
    for (i in field)
        for (j in i)
            when (j) {
                'X' -> totalX++
                'O' -> totalO++
            }
    if ((totalX - totalO).absoluteValue > 1) return "impossible"

    return when {
        xWins && oWins -> "Impossible"
        xWins -> "X wins"
        oWins -> "O wins"
        else -> if (gameIsFinished(field)) "Draw" else "Game not finished"
    }
}

fun isWinner(field: MutableList<MutableList<Char>>, player: Char): Boolean {
    if (MutableList(3) { player } in field) return true

    for (i in 0..2) {
        var col = ""
        for (j in 0..2) col += field[j][i]
        if (col == player.toString().repeat(3)) return true
    }

    var diagonal1 = ""
    var diagonal2 = ""
    for (i in 0..2) {
        diagonal1 += field[i][i]
        diagonal2 += field[i][(i - 2).absoluteValue]
    }
    if (diagonal1 == player.toString().repeat(3)
        || diagonal2 == player.toString().repeat(3)
    )
        return true

    return false
}

fun printField(field: MutableList<MutableList<Char>>) {
    println("---------")
    for (i in field) {
        print("| ")
        print(i.joinToString(" "))
        println(" |")
    }
    println("---------")
}
