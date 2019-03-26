def monteCarloRecursion(count: Int): Double = {
    @tailrec
    def aux(count: Int, inCircle: Int): Int =
      if (count == 0) inCircle
      else {
        val p = newPoint()
        aux(count - 1, inCircle + (if (p.inCircle()) 1 else 0))
      }

    val inCircle = aux(count, 0)
    compute(count, inCircle)
  }