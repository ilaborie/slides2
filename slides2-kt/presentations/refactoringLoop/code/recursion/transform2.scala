def transform(elements: List[ElementScala]): List[Result] =
  elements match {
    case Nil          => Nil
    case head :: tail => transform(head) :: transform(tail)
  }