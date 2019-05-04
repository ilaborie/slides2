package _04_lamda;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KFunction;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 2,
   d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\u001a0\u0010\u000e\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u00012\u0018\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u0010\u001a\u0016\u0010\u0011\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u0001\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0011\u0010\u0004\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0003\"A\u0010\u0006\u001a2\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r"},
   d2 = {"sum5", "", "getSum5", "()I", "sum6", "getSum6", "sumLam", "Lkotlin/reflect/KFunction2;", "Lkotlin/ParameterName;", "name", "x", "y", "getSumLam", "()Lkotlin/reflect/KFunction;", "apply", "operation", "Lkotlin/Function2;", "sumf"}
)
public final class FunctionKt {
   private static final int sum5;
   @NotNull
   private static final KFunction sumLam;
   private static final int sum6;

   public static final int apply(int x, int y, @NotNull Function2 operation) {
      Intrinsics.checkParameterIsNotNull(operation, "operation");
      return ((Number)operation.invoke(x, y)).intValue();
   }

   public static final int sumf(int x, int y) {
      return x + y;
   }

   public static final int getSum5() {
      return sum5;
   }

   @NotNull
   public static final KFunction getSumLam() {
      return sumLam;
   }

   public static final int getSum6() {
      return sum6;
   }

   static {
      sum5 = apply(2, 3, (Function2)null.INSTANCE);
      sumLam = null.INSTANCE;
      sum6 = apply(1, 5, (Function2)sumLam);
   }
}