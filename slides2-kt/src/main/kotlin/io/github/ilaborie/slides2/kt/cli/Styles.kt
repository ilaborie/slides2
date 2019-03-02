package io.github.ilaborie.slides2.kt.cli


object Styles {

    val highlight: (String) -> String = Color.LIGHT_BLUE.fg
    val debug: (String) -> String = Color.GREEN.fg
    val info: (String) -> String = Color.BLUE.fg
    val warn: (String) -> String = Color.YELLOW.fg
    val error: (String) -> String = Color.RED.fg
    val stacktrace: (String) -> String = Color.LIGHT_GRAY.fg
    val time: (String) -> String = Color.CYAN.fg

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

        internal val fg: (String) -> String = { "$ESCAPE[${baseCode}m$it$RESET" }

        internal val bg: (String) -> String = { "$ESCAPE[${baseCode + BG_JUMP}m$it$RESET" }
    }

}
