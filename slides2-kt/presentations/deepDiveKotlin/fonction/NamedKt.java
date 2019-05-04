package _03_fun;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 2,
   d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u0006\u0010\u0006\u001a\u00020\u0001"},
   d2 = {"buildString", "", "prefix", "who", "enhanced", "", "greetings"}
)
public final class NamedKt {
   @NotNull
   public static final String buildString(@NotNull String prefix, @NotNull String who, boolean enhanced) {
      Intrinsics.checkParameterIsNotNull(prefix, "prefix");
      Intrinsics.checkParameterIsNotNull(who, "who");
      String msg = "" + prefix + ' ' + who;
      if (enhanced) {
         msg = msg + '!';
      }

      return msg;
   }

   @NotNull
   public static final String greetings() {
      String var0 = "Hello";
      String var1 = "RivieraDev";
      boolean var2 = true;
      return buildString(var0, var1, var2);
   }
}