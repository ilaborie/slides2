package _08_extension;

import astronomy.AstronomicalBody;
import astronomy.SolarSystem;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
        mv = {1, 1, 9},
        bv = {1, 0, 2},
        k = 2,
        d1 = {"\u0000 \n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0019\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\u0002\u0010\n\u001a\n\u0010\u000b\u001a\u00020\t*\u00020\u0002\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004"},
        d2 = {"size", "", "Lastronomy/AstronomicalBody;", "getSize", "(Lastronomy/AstronomicalBody;)I", "main", "", "args", "", "", "([Ljava/lang/String;)V", "display"}
)
public final class ExtensionKt {
   public static final int getSize(@NotNull AstronomicalBody $receiver) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      return $receiver.getName().length();
   }

   @NotNull
   public static final String display(@NotNull AstronomicalBody $receiver) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      return "Body " + $receiver.getName() + ' ' + getSize($receiver);
   }

   public static final void main(@NotNull String[] args) {
      Intrinsics.checkParameterIsNotNull(args, "args");
      Iterable $receiver$iv = (Iterable)SolarSystem.INSTANCE.getBodies();
      Iterator var2 = $receiver$iv.iterator();

      while(var2.hasNext()) {
         Object element$iv = var2.next();
         AstronomicalBody it = (AstronomicalBody)element$iv;
         String var5 = display(it);
         System.out.println(var5);
      }

   }
}
