public static double monteCarloFor(int count) {
    int inCircle = 0;
    for (int i = 0; i < count; i++) {
        Point p = newPoint();

        if (p.inCircle()) {
            inCircle++;
        }
    }
    return compute(count, inCircle);
}