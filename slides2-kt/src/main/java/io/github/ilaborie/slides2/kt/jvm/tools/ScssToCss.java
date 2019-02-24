package io.github.ilaborie.slides2.kt.jvm.tools;

import java.io.IOException;

public class ScssToCss {

    static {
        try {
            NativeUtils.loadLibraryFromJar("/resources/libHelloJNI.so");
        } catch (IOException e) {
            // This is probably not the best way to handle exception :-)
            e.printStackTrace();
        }
    }

    public static native String scssFileToCss(String file);

}
