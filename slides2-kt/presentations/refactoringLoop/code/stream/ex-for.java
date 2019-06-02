List<Integer> ints = new ArrayList<>();
for (int i = 0; i < 9; i++) {
    for (int j = 0; j < i; j++) {
        if (j % 2 == 1) {
            ints.add(j * j);
        }
    }
}