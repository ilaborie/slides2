fun <T> timed(block: () -> T) {
    val startTime = System.currentTimeMillis()
    val result = block()
    val endTime = System.currentTimeMillis()
    val duration = endTime - startTime
    val readable = String.format("%d min %02d seconds", duration / 60000, (duration % 60000) / 1000)
    println("$result in $readable")
}

fun main(args: Array<String>) {
    fun Long.toMB(): String =
        "${this / 1048576}MB"

    fun Runtime.usedMemory(): String =
        (totalMemory() - freeMemory()).toMB()

    fun Runtime.getMemoryInfo(): String =
        "used: ${usedMemory()}, total: ${totalMemory().toMB()}"

    timed {
        (0..1_000_000)
            .asSequence()
            .onEach {
                if (it % 10_000 == 0) {
                    println(Runtime.getRuntime().getMemoryInfo())
                }
                println(it)
            }
            .map { Moon(it.toString()) }
            .first()
    }
}