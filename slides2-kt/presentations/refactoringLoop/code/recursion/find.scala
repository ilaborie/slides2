def find(elements: List[Element]): Option[Element] =
  if (elements.isEmpty)
    None
  else if (isSomething(elements))
    Some(elements.head)
  else
    filterAlt(elements.tail)
