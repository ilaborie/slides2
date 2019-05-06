import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

@Metadata(
        mv = {1, 1, 15},
        bv = {1, 0, 3},
        k = 2,
        d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\u001a\b\u0010\u0000\u001a\u0004\u0018\u00010\u0001\u001a\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0004"},
        d2 = {"createSomething", "", "main", "", "kotlinDee"}
)
public final class Null_safetyKt {
   public static final void main() {
      String somethingNotNull = "aString";
      int length = somethingNotNull.length();
      String something = (String)null;
      int length = false;
      String var10000 = createSomething();
      length = var10000 != null ? var10000.length() : 0;
      something = "aString";
      length = something.length();
   }

   // $FF: synthetic method
   public static void main(String[] var0) {
      main();
   }

   @Nullable
   public static final String createSomething() {
      return null;
   }
}