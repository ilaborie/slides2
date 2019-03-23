// a, b, c, d, e : T 
T firstNotNull = Stream.of(a, b, c, d, e)
    .filter(Objects::nonNull)
    .findFirst()
    .orElse(null);
