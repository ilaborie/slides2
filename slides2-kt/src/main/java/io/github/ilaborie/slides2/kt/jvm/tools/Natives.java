package io.github.ilaborie.slides2.kt.jvm.tools;

import static io.github.ilaborie.slides2.kt.jvm.tools.NativeUtils.loadLib;

public class Natives {

    static {
        loadLib("slides2_rust");
    }

    public static native String scssFileToCss(String file);
    public static native String qrCode(String data);
    public static native String markdownToHtml(String markdown);

}
