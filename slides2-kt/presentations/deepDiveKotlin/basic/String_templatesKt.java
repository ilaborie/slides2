package _01_basic;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 2,
   d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003"},
   d2 = {"greeting", "", "who", "L_01_basic/Someone;"}
)
public final class String_templatesKt {
   public static final void greeting(@NotNull Someone who) {
      Intrinsics.checkParameterIsNotNull(who, "who");
      String var1 = "Hello " + who + '!';
      System.out.println(var1);
      var1 = "Hello " + who.getFirstName() + ' ' + who.getLastName() + '!';
      System.out.println(var1);
   }
}
