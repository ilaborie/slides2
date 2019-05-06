* ⚠️ Les contrôles de types génériques ne sont faits qu'au moment de la compilation
* Covariant: `out`, en java `? extends T`
* Contravariant: `in`, en java `? super T`
* Projection `*` correspondant à `Any?` ou `Nothing`

#### Borne supérieure

```kotlin
fun <T : Comparable<T>> sort(list: List<T>): List<T>
```

Les détails: <https://kotlinlang.org/docs/reference/generics.html>
