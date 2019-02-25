package io.github.ilaborie.slides2.kt.jvm.tools;

import java.io.IOException;

import static io.github.ilaborie.slides2.kt.jvm.tools.NativeUtils.loadLib;

public class ScssToCss {

    static {
        loadLib("slides2_compile_scss");
    }

    public static native String scssFileToCss(String file);

}
