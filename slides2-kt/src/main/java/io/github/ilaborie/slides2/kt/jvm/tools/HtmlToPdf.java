package io.github.ilaborie.slides2.kt.jvm.tools;

import static io.github.ilaborie.slides2.kt.jvm.tools.NativeUtils.loadLib;

public class HtmlToPdf {

    static {
        loadLib("slides2_html_to_pdf");
    }

    public static native void htmlToPdf(String title, String html, String outputFile);

}
