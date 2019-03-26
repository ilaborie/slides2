def monteCarloFor(count: Int): Double = {
    var inCircle = 0
    for (_ <- 1 to count) {
      val p = newPoint()

      if (p.inCircle()) {
        inCircle += 1
      }
    }
    compute(count, inCircle)
  }