package io.github.ilaborie.slides2.kt.engine.contents

import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.Emphasis
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.Keyboard
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.Mark
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.Pre
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.Strong
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.UnderLine


fun <T : Content> T.header(level: Int): Title =
    Title(level, this)

val <T : Content> T.h1: Title
    get() = header(1)

val <T : Content> T.h2: Title
    get() = header(2)

val <T : Content> T.h3: Title
    get() = header(3)

val <T : Content> T.h4: Title
    get() = header(4)

val <T : Content> T.h5: Title
    get() = header(5)

val <T : Content> T.h6: Title
    get() = header(6)


val <T : Content> T.p: Paragraph
    get() = Paragraph(this)


val <T : Content> T.strong: StyledText
    get() = StyledText(Strong, this)

val <T : Content> T.em: StyledText
    get() = StyledText(Emphasis, this)

val <T : Content> T.u: StyledText
    get() = StyledText(UnderLine, this)

val <T : Content> T.mark: StyledText
    get() = StyledText(Mark, this)

val <T : Content> T.kbd: StyledText
    get() = StyledText(Keyboard, this)

val <T : Content> T.pre: StyledText
    get() = StyledText(Pre, this)


val <T : List<Content>> T.ul: UnorderedList
    get() = UnorderedList(this)

val <T : List<Content>> T.ol: OrderedList
    get() = OrderedList(this)