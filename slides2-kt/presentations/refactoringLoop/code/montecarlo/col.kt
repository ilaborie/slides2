fun monteCarloCollection(count: Int): Double {
    val inCircle = (1..count)
        .map { newPoint() }
        .count { it.inCircle() }

    return compute(count, inCircle)
}