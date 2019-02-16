package slides2.io



enum class ModificationKind {
    Updated, Added, Deleted
}

interface Watcher {
    // see https://github.com/AlexeySoshin/KotlinNativeFileWatcher
    fun watch(resource: Folder, action: (ModificationKind) -> Unit)
}