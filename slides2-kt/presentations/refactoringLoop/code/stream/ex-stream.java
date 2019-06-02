List<Integer> ints = IntStream.range(0, 9)
        .flatMap(i -> IntStream.range(0, i))
        .filter(j -> j % 2 == 1)
        .map(j -> j * j)
        .boxed()
        .collect(Collectors.toList());