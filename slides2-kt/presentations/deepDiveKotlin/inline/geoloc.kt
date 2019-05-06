inline class Latitude(val value: Double)
inline class Longitude(val value: Double)

data class Geoloc(val lat: Latitude, val lng: Longitude) {

    infix fun distanceTo(other: Geoloc): Double = TODO()
}

fun main() {
    val toulouse = Geoloc(Latitude(43.60426), Longitude(1.44367))
    val nice = Geoloc(Latitude(43.70313), Longitude(7.26608))

    val travel = toulouse distanceTo nice
    println(travel)
}