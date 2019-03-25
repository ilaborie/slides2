def transformR(input: List[Element]): List[Result] =
  input match {
    case Nil          => Nil
    case head :: tail => transform(head) :: transformR(tail)
  }