def find(input: List[Element]): Option[Element] =
  if (input.isEmpty) None
  else if (isSomething(input)) Some(input.head)
  else find(input.tail)