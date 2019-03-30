fun excelColumn(n: Int): String {
    var chars = ""
    var current = n

    while (true) {
        current--
        chars = ('A'.toInt() + current % 26).toChar() + chars
        if (current < 26) break
        current /= 26
    }
    return chars
}