interface AstronomicalBody {
    val name: String
}

data class Planet(override val name: String,
                  val moons: List<Moon> = emptyList()) : AstronomicalBody {
    init {
        require(name.isNotEmpty())
    }

    operator fun plus(moon: Moon): Planet {
        return this.copy(moons = moons + moon)
    }
}

data class Moon(override val name: String) : AstronomicalBody

object SolarSystem {
    val earth = Planet(name = "Earth")
    val moon = Moon(name = "Moon")

    val bodies: List<AstronomicalBody> = listOf(
        earth,
        Planet(name = "Jupiter")
    )
}

enum class PlanetKind {
    Terrestrial, GasGiant, IceGiant;

    companion object {
        fun fromName(name: String): PlanetKind {
            return PlanetKind.valueOf(name) // enumValueOf(name)
        }
    }
}

fun getMoons(planet: Planet): List<Moon> {
    val (_, moons) = planet
    return moons
}