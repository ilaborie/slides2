package _02_null_safety;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
        mv = {1, 1, 9},
        bv = {1, 0, 2},
        k = 2,
        d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0019\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005\u001a\b\u0010\u0006\u001a\u0004\u0018\u00010\u0004"},
        d2 = {"main", "", "args", "", "", "([Ljava/lang/String;)V", "something"}
)
public final class NullSafetyKt {
   public static final void main(@NotNull String[] args) {
      Intrinsics.checkParameterIsNotNull(args, "args");
      String somethingNotNull = "aString";
      int length = somethingNotNull.length();
      String something = (String)null;
      int length = false;
      String var10000 = something();
      length = var10000 != null ? var10000.length() : 0;
      var10000 = something();
      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      length = var10000.length();
      something = "aString";
      length = something.length();
   }

   @Nullable
   public static final String something() {
      return null;
   }
}
