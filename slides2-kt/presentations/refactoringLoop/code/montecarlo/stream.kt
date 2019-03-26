fun monteCarloSequence(count: Int): Double {
    val inCircle = generateSequence { newPoint() }
        .take(count)
        .count { it.inCircle() }

    return compute(count, inCircle)
}