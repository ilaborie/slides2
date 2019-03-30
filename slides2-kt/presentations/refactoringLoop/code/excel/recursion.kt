fun excelColumn(n: Int): String {
    tailrec fun aux(n: Int, acc: String): String {
        val current = n - 1
        val rest = current % 26
        val result = ('A'.toInt() + rest).toChar() + acc
        return if (current < 26) result
        else aux(current / 26, result)
    }
    return aux(n, "")
}