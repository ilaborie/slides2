fun monteCarloRecursion(count: Int): Double {
    tailrec fun aux(count: Int, inCircle: Int): Int =
        if (count == 0) inCircle
        else {
            val p = newPoint()
            aux(count - 1, if (p.inCircle()) inCircle + 1 else inCircle)
        }

    val inCircle = aux(count, 0)
    return compute(count, inCircle)
}