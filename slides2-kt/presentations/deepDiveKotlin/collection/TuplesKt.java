package _11_collections_2;

import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
        mv = {1, 1, 9},
        bv = {1, 0, 2},
        k = 2,
        d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\u001a\u0019\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005"},
        d2 = {"main", "", "args", "", "", "([Ljava/lang/String;)V"}
)
public final class TuplesKt {
   public static final void main(@NotNull String[] args) {
      Intrinsics.checkParameterIsNotNull(args, "args");
      Pair aPair = kotlin.TuplesKt.to("Earth", "Moon");
      String var2 = (String)aPair.component1();
      String moon = (String)aPair.component2();
      Triple aTriple = new Triple("Voyager 1", 1977, CollectionsKt.listOf(new String[]{"Jupiter", "Saturn"}));
      String var5 = (String)aTriple.component1();
      int var6 = ((Number)aTriple.component2()).intValue();
      List flyOver = (List)aTriple.component3();
   }
}