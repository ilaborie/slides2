
```kotlin
interface Function<in T, out U>
```

```kotlin
Function<*, String> // correspond à Function<in Nothing, String>
```

```kotlin
Function<Int, *> // correspond à Function<Int, out Any?>
```

```kotlin
Function<*, *> // correspond à Function<in Nothing, out Any?>
```