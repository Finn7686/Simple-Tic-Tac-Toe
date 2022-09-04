fun main() {
    val input = readln()
    for (i in input) {
        if (i.isDigit()) {
            println(i)
            break
        }
    }
}