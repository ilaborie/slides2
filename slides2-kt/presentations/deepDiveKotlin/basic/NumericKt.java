import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

@Metadata(
        mv = {1, 1, 15},
        bv = {1, 0, 3},
        k = 2,
        d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\"\u001e\u0010\u0000\u001a\u0004\u0018\u00010\u0001X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u0006\u001a\u0004\b\u0002\u0010\u0003\"\u0004\b\u0004\u0010\u0005\"\u0014\u0010\u0007\u001a\u00020\bX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\"\u0014\u0010\u000b\u001a\u00020\fX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"},
        d2 = {"aDouble", "", "getADouble", "()Ljava/lang/Double;", "setADouble", "(Ljava/lang/Double;)V", "Ljava/lang/Double;", "aLong", "", "getALong", "()J", "anInt", "", "getAnInt", "()I", "kotlinDee"}
)
public final class NumericKt {
   private static final int anInt = 42;
   private static final long aLong = 42L;
   @Nullable
   private static Double aDouble;

   public static final int getAnInt() {
      return anInt;
   }

   public static final long getALong() {
      return aLong;
   }

   @Nullable
   public static final Double getADouble() {
      return aDouble;
   }

   public static final void setADouble(@Nullable Double var0) {
      aDouble = var0;
   }
}
