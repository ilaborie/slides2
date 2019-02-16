package slides2.io


interface FsElement {
    val name: String
}

expect class File() : FsElement

expect class Folder() : FsElement {
    fun children(): List<FsElement>
}