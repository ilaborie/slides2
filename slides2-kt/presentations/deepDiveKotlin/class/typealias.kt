interface Entity

typealias Id = String
typealias Version = Int
typealias EntityKey = Pair<Id, Version>
typealias Entities = List<Entity>

// fun getAllEntities(): Map<Pair<String, Int>, List<Entity>> = emptyMap()
fun getAllEntities(): Map<EntityKey, Entities> = emptyMap()