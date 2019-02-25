package io.github.ilaborie.slides2.kt.jvm.tools;

import static io.github.ilaborie.slides2.kt.jvm.tools.NativeUtils.loadLib;

public class MarkdownToHtml {

    static {
        loadLib("slides2_md_to_str");
    }

    public static native String markdownToHtml(String markdown);

}
