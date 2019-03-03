package io.github.ilaborie.slides2.kt.engine.contents

import io.github.ilaborie.slides2.kt.engine.Content
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.Del
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.Emphasis
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.Ins
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.Keyboard
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.Mark
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.Pre
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.Strong
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.Sub
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.Sup
import io.github.ilaborie.slides2.kt.engine.contents.TextStyle.UnderLine


fun <T : Content> T.header(level: Int): Title =
    Title(level, this, emptySet())

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
    get() = Paragraph(this, emptySet())


val <T : Content> T.strong: StyledText
    get() = StyledText(Strong, this, emptySet())

val <T : Content> T.em: StyledText
    get() = StyledText(Emphasis, this, emptySet())

val <T : Content> T.u: StyledText
    get() = StyledText(UnderLine, this, emptySet())

val <T : Content> T.mark: StyledText
    get() = StyledText(Mark, this, emptySet())

val <T : Content> T.kbd: StyledText
    get() = StyledText(Keyboard, this, emptySet())

val <T : Content> T.pre: StyledText
    get() = StyledText(Pre, this, emptySet())

val <T : Content> T.ins: StyledText
    get() = StyledText(Ins, this, emptySet())

val <T : Content> T.del: StyledText
    get() = StyledText(Del, this, emptySet())

val <T : Content> T.sub: StyledText
    get() = StyledText(Sub, this, emptySet())

val <T : Content> T.sup: StyledText
    get() = StyledText(Sup, this, emptySet())


val <T : List<Content>> T.ul: UnorderedList
    get() = UnorderedList(this, classes = emptySet())

val <T : List<Content>> T.ol: OrderedList
    get() = OrderedList(this, classes = emptySet())