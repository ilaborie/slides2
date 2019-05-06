import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

@Metadata(
        mv = {1, 1, 15},
        bv = {1, 0, 3},
        k = 2,
        d1 = {"\u0000\n\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\"\u0015\u0010\u0000\u001a\u0004\u0018\u00010\u0001¢\u0006\n\n\u0002\u0010\u0004\u001a\u0004\b\u0002\u0010\u0003\"\u0011\u0010\u0005\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"},
        d2 = {"nullable", "", "getNullable", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "other", "getOther", "()I", "kotlinDee"}
)
public final class LetKt {
   private static final int other;
   @Nullable
   private static final Integer nullable;

   public static final int getOther() {
      return other;
   }

   @Nullable
   public static final Integer getNullable() {
      return nullable;
   }

   static {
      int var0 = FunctionKt.sumf(1, 2);
      boolean var1 = false;
      boolean var2 = false;
      int var4 = false;
      other = var0 + 1;
      Integer var10000 = PlopKt.maybeAnInt();
      if (var10000 != null) {
         Integer var5 = var10000;
         var1 = false;
         var2 = false;
         int it = ((Number)var5).intValue();
         var4 = false;
         var10000 = it + 1;
      } else {
         var10000 = null;
      }

      nullable = var10000;
   }
}