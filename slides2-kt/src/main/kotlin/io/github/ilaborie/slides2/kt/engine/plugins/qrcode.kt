package io.github.ilaborie.slides2.kt.engine.plugins

import io.github.ilaborie.slides2.kt.dsl.ContainerBuilder
import io.github.ilaborie.slides2.kt.jvm.tools.QrCode


fun ContainerBuilder.qrCode(data: String) {
    val qr = QrCode.qrCode(data)
    html { "<pre class=\"qrcode\">$qr</pre>" }
}
