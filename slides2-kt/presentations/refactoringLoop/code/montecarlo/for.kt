fun monteCarloFor(count: Int): Double {
    var inCircle = 0
    for (i in 0 until count) {
        val p = newPoint()

        if (p.inCircle()) {
            inCircle++
        }
    }
    return compute(count, inCircle)
}