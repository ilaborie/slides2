List<String> fruits = Arrays.asList("apple", "banana", "orange", "coconut");

Predicate<String> isMonkeyLike = s -> "🍌".equals(s) || "🥥".equals(s);

String food = fruits.stream()
        .peek(System.out::println) // just "apple", "banana"
        .map(this::emojify) // 🍎, 🍌, 🍊, 🥥
        .filter(isMonkeyLike) // 🍌, 🥥
        .findFirst() // 🍌
        .orElseThrow(AngryMonkeyException::new);

feedMonkey(food); // 🍌