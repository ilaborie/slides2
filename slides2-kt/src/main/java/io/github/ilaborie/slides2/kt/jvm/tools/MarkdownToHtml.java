package io.github.ilaborie.slides2.kt.jvm.tools;

import java.io.IOException;

public class MarkdownToHtml {

    static {
        String libname = "slides2_md_to_str";
        String lib = System.mapLibraryName(libname);
        try {
            NativeUtils.loadLibraryFromJar("/native/" + lib);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot find library " + lib, e);
        }
    }

    public static native String markdownToHtml(String markdown);

}
