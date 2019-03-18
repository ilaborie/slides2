fun factorial(n: Int): Int {
    tailrec fun aux(n: Int, acc: Int): Int =
        if (n < 2) acc
        else aux(n - 1, n * acc)

    return aux(n, 1)
}