import kotlin.Metadata;

@Metadata(
        mv = {1, 1, 15},
        bv = {1, 0, 3},
        k = 2,
        d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u001a\u0006\u0010\u0000\u001a\u00020\u0001¨\u0006\u0002"},
        d2 = {"main", "", "kotlinDee"}
)
public final class My_appKt {
    public static final void main() {
        int var0 = LibKt.compute$default(0, 1, (Object)null);
        boolean var1 = false;
        System.out.println(var0);
    }

    // $FF: synthetic method
    public static void main(String[] var0) {
        main();
    }
}