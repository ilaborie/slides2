def monteCarloStream(count: Int): Double = {
    val inCircle = Stream.fill(count) {
      newPoint()
    }
      .count(_.inCircle())

    compute(count, inCircle)
  }