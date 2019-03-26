public static double monteCarloRecursion(int count) {
    int inCircle = monteCarloRecursionAux(count, 0);
    return compute(count, inCircle);
}

private static int monteCarloRecursionAux(int count, int inCircle) {
    if (count == 0) {
        return inCircle;
    }
    Point p = newPoint();

    if (p.inCircle()) {
        return monteCarloRecursionAux(count - 1, inCircle + 1);
    } else {
        return monteCarloRecursionAux(count - 1, inCircle);
    }
}