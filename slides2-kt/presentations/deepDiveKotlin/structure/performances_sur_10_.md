
> Ne croyez pas les benchmarks, faites les vous-même !

<https://github.com/MonkeyPatchIo/kotlin-perf>

| Benchmark              | Mode  | Cnt |         Score |         Error | Units |
|------------------------|-------|-----|---------------|---------------|-------|
| factorialJavaFor       | thrpt | 200 | 433372258.508 | ± 1218796.228 | ops/s |
| factorialKotlinFor     | thrpt | 200 | 374900724.013 | ± 1836466.839 | ops/s |
| factorialJavaRec       | thrpt | 200 |  71945600.003 | ± 1621282.609 | ops/s |
| factorialKotlinRec     | thrpt | 200 |  75889169.327 | ±  803516.130 | ops/s |
| factorialJavaTailRec   | thrpt | 200 |  74708348.540 | ±  385285.112 | ops/s |
| factorialKotlinTailRec | thrpt | 200 | 432005903.950 | ± 2558012.821 | ops/s |
| factorialJavaReduce    | thrpt | 200 |  21560855.907 | ±  586144.742 | ops/s |
| factorialKotlinReduce  | thrpt | 200 |  99169022.775 | ± 2711794.007 | ops/s |
