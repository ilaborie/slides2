package slides2.io

actual class File actual constructor() : FsElement {
    override val name: String
        get() = TODO("not implemented")
}

actual class Folder actual constructor() : FsElement {
    override val name: String
        get() = TODO("not implemented")

    actual fun children(): List<FsElement> {
        TODO("not implemented")
    }
}