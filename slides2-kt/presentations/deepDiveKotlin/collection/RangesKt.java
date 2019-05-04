package _11_collections_2;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 2,
   d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\u001a\u0019\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005"},
   d2 = {"main", "", "args", "", "", "([Ljava/lang/String;)V"}
)
public final class RangesKt {
   public static final void main(@NotNull String[] args) {
      Intrinsics.checkParameterIsNotNull(args, "args");
      int i = 1;

      byte var2;
      for(var2 = 3; i <= var2; ++i) {
         System.out.print(i);
      }

      i = 3;

      for(var2 = 1; i >= var2; --i) {
         System.out.print(i);
      }

      byte var4 = 1;
      IntProgression var10000 = kotlin.ranges.RangesKt.step((IntProgression)(new IntRange(var4, 5)), 2);
      i = var10000.getFirst();
      int var5 = var10000.getLast();
      int var3 = var10000.getStep();
      if (var3 > 0) {
         if (i > var5) {
            return;
         }
      } else if (i < var5) {
         return;
      }

      while(true) {
         System.out.print(i);
         if (i == var5) {
            return;
         }

         i += var3;
      }
   }
}