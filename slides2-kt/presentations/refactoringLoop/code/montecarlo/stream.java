public static double monteCarloStream(int count) {
    int inCircle = (int) Stream.generate(Point::newPoint)
            .limit(count)
            .filter(Point::inCircle)
            .count();

    return compute(count, inCircle);
}