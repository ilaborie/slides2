import kotlin.Metadata;

@Metadata(
        mv = {1, 1, 15},
        bv = {1, 0, 3},
        k = 2,
        d1 = {"\u0000\n\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u001a\u0010\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u0001¨\u0006\u0003"},
        d2 = {"compute", "", "result", "kotlinDee"}
)
public final class LibKt {
    public static final int compute(int result) {
        return result;
    }

    // $FF: synthetic method
    public static int compute$default(int var0, int var1, Object var2) {
        if ((var1 & 1) != 0) {
            var0 = 42;
        }

        return compute(var0);
    }
}
