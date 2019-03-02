package io.github.ilaborie.slides2.kt.term
sealed class TimeUnit {
    abstract val unit: String
    abstract fun fromNanos(value: Double): Double
    abstract fun toNanos(value: Double): Double

    companion object {

        object Nanos : TimeUnit() {
            override val unit = "ns"
            override fun fromNanos(value: Double): Double =
                value

            override fun toNanos(value: Double): Double =
                value
        }

        object Micros : TimeUnit() {
            override val unit = "μs"
            override fun fromNanos(value: Double): Double =
                Nanos.fromNanos(value / 1000)

            override fun toNanos(value: Double): Double =
                Nanos.toNanos(value * 1000)
        }

        object Millis : TimeUnit() {
            override val unit = "ms"
            override fun fromNanos(value: Double): Double =
                Micros.fromNanos(value / 1000)

            override fun toNanos(value: Double): Double =
                Micros.toNanos(value * 1000)
        }

        object Seconds : TimeUnit() {
            override val unit = "s"
            override fun fromNanos(value: Double): Double =
                Millis.fromNanos(value / 1000)

            override fun toNanos(value: Double): Double =
                Millis.toNanos(value * 1000)
        }

        object Minutes : TimeUnit() {
            override val unit = "min"
            override fun fromNanos(value: Double): Double =
                Seconds.fromNanos(value / 60)

            override fun toNanos(value: Double): Double =
                Seconds.toNanos(value * 60)
        }

        object Hours : TimeUnit() {
            override val unit = "h"
            override fun fromNanos(value: Double): Double =
                Minutes.fromNanos(value / 60)

            override fun toNanos(value: Double): Double =
                Minutes.toNanos(value * 60)
        }

        object Days : TimeUnit() {
            override val unit = "days"
            override fun fromNanos(value: Double): Double =
                Hours.fromNanos(value / 24)

            override fun toNanos(value: Double): Double =
                Hours.toNanos(value * 24)
        }
    }

}
