def transform(elements: List[Element]): List[Result] = {
  if (elements.isEmpty) {  // end of recursion
    return Nil
  }
  // Deconstruct
  val head :: tail = elements

  // Handle head
  val transformed = transform(head)

  // Recursion
  transformed :: transform(tail)
}