val s = SolarSystem.bodies.asSequence()
    .filterIsInstance<Planet>()
    .flatMap { planet -> planet.moons.asSequence() } // 😻
    .filterNot { it.name.startsWith("S/") }
    .map { it.name }
    .first()

println(s)
