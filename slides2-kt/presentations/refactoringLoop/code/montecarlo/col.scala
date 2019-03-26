def monteCarloCollection(count: Int): Double = {
    val inCircle = (1 to count)
      .map(_ => newPoint())
      .count(_.inCircle())

    compute(count, inCircle)
  }