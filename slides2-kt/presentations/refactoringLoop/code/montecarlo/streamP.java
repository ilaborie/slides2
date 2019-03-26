public static double monteCarloStreamParallel(int count) {
    int inCircle = (int) Stream.generate(Point::newPoint)
            .unordered()
            .parallel()
            .limit(count)
            .filter(Point::inCircle)
            .count();

    return compute(count, inCircle);
}