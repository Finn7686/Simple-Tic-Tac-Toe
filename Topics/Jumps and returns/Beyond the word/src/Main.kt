fun main() {
    val word = readln()
    for (i in 'a'..'z') {
        if (i in word) continue
        print(i)
    }
}