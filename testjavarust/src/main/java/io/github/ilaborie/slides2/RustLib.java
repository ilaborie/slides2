package io.github.ilaborie.slides2;

public class RustLib {

    static native String ploper(String s);

    static {
        String lib = "test_rust_ffi";
        String[] property = System.getProperty("java.library.path")
                .split(":");
        for (String path : property) {
            System.out.println(path);
//
//            File f = new File(path).toPath().resolve(lib + ".dylib").toFile();

        }
        System.loadLibrary(lib);
    }
}
