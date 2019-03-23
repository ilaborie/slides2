// input: List<Element> 
// Element#merge : (Element) -> Element

Element result = 
    input.stream()
        .reduce(
            new Element(),
            (elt1, elt2) -> elt1.merge(elt2)
        );