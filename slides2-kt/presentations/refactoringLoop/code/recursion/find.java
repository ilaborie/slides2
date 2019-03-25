public Element find(List<Element> input) {
    if (input.isEmpty()) {
        return null;
    }
    Element head = input.get(0);
    if (isSomething(head)) {
        return head;
    }
    List<Element> tail = input.subList(1, input.size());
    return find(tail);
}