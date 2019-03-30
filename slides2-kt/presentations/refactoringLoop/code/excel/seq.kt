fun excelColumn(n: Int): String =
    generateSequence<Pair<Int?, String>>(n to "") { (n, acc) ->
        if (n == null) null
        else {
            val current = n - 1
            val result = ('A'.toInt() + current % 26).toChar() + acc

            if (current < 26) null to result
            else (current / 26) to result
        }
    }.last().second