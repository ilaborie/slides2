package _01_basic;

import kotlin.Metadata;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 2,
   d1 = {"\u0000\u0006\n\u0000\n\u0002\u0010\u0002\u001a\u0006\u0010\u0000\u001a\u00020\u0001"},
   d2 = {"tryNumeric", ""}
)
public final class NumericKt {
   public static final void tryNumeric() {
      int anInt = true;
      long aLong = 42L;
      Double aDouble = (Double)null;
   }
}
