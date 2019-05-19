package io.github.ilaborie.slides2.kt.jvm.tools;

import static io.github.ilaborie.slides2.kt.jvm.tools.NativeUtils.loadLib;

public class QrCode {

    static {
        loadLib("slides2_qrcode");
    }

    public static native String qrCode(String data);

}
