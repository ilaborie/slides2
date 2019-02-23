package io.github.ilaborie.slides2.kt.cli


typealias Style = (String) -> String

// from https://github.com/ziggy42/kolor
private const val ESCAPE = '\u001B'
private const val RESET = "$ESCAPE[0m"
private const val BG_JUMP = 10

internal enum class Color(baseCode: Int) {
    BLACK(30),
    RED(31),
    GREEN(32),
    YELLOW(33),
    BLUE(34),
    MAGENTA(35),
    CYAN(36),
    LIGHT_GRAY(37),

    DARK_GRAY(90),
    LIGHT_RED(91),
    LIGHT_GREEN(92),
    LIGHT_YELLOW(93),
    LIGHT_BLUE(94),
    LIGHT_MAGENTA(95),
    LIGHT_CYAN(96),
    WHITE(97);

    internal val fg: Style = { "$ESCAPE[${baseCode}m$it$RESET" }

    internal val bg: Style = { "$ESCAPE[${baseCode + BG_JUMP}m$it$RESET" }
}

object Styles {

    val highlight: Style = Color.LIGHT_BLUE.fg
    val debug: Style = Color.GREEN.fg
    val info: Style = Color.BLUE.fg
    val warn: Style = Color.YELLOW.fg
    val error: Style = Color.RED.fg
    val stacktrace: Style = Color.LIGHT_GRAY.fg
    val time: Style = Color.CYAN.fg


}
