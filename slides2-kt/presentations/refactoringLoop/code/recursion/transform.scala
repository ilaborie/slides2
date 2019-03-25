def transformR(input: List[Element]): List[Result] = {
  if (input.isEmpty) {  // end of recursion
    return Nil
  }
  // Deconstruct
  val head :: tail = input

  // Handle head
  val transformed = transform(head)

  // Recursion
  transformed :: transformR(tail)
}