fun monteCarloSequenceParallel(count: Int): Double {
    val inCircle = sequence {
        yieldAll(generateSequence { newPoint() })
    }
        .take(count)
        .count { it.inCircle() }

    return compute(count, inCircle)
}