package _04_lamda;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 2,
   d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0011\u0010\u0004\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0003\"#\u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t"},
   d2 = {"sum3", "", "getSum3", "()I", "sum4", "getSum4", "suml", "Lkotlin/Function2;", "getSuml", "()Lkotlin/jvm/functions/Function2;"}
)
public final class LambdaKt {
   @NotNull
   private static final Function2 suml;
   private static final int sum3;
   private static final int sum4;

   @NotNull
   public static final Function2 getSuml() {
      return suml;
   }

   public static final int getSum3() {
      return sum3;
   }

   public static final int getSum4() {
      return sum4;
   }

   static {
      suml = (Function2)null.INSTANCE;
      sum3 = FunctionKt.apply(1, 2, suml);
      sum4 = FunctionKt.apply(1, 3, (Function2)null.INSTANCE);
   }
}
