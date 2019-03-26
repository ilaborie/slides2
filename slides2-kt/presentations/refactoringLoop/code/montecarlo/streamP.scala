def monteCarloStreamParallel(count: Int): Double = {
    val inCircle = Stream.fill(count) {
      newPoint()
    }
      .par
      .count(_.inCircle())

    compute(count, inCircle)
  }