package io.github.ilaborie.slides2.kt.engine.contents

import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.Emphasis
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.Keyboard
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.Mark
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.Pre
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.Strong
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.UnderLine


val String.raw: Text
    get() = Text(this, escape = false)

fun String.header(level: Int): Title =
    Title(level, Text(this))

val String.h1: Title
    get() = header(1)

val String.h2: Title
    get() = header(2)

val String.h3: Title
    get() = header(3)

val String.h4: Title
    get() = header(4)

val String.h5: Title
    get() = header(5)

val String.h6: Title
    get() = header(6)


val String.p: Paragraph
    get() = Paragraph(Text(this))


val String.strong: StyledText
    get() = StyledText(Strong, Text(this))

val String.em: StyledText
    get() = StyledText(Emphasis, Text(this))

val String.u: StyledText
    get() = StyledText(UnderLine, Text(this))

val String.mark: StyledText
    get() = StyledText(Mark, Text(this))

val String.kbd: StyledText
    get() = StyledText(Keyboard, Text(this))

val String.pre: StyledText
    get() = StyledText(Pre, Text(this))