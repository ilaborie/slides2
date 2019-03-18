def factorial(n: Int): Int = {
  @tailrec
  def aux(n: Int, acc: Int): Int =
    if (n < 2) acc
    else aux(n - 1, n * acc)

  aux(n, 1)
}