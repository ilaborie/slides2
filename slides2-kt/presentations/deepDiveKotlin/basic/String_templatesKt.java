import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
        mv = {1, 1, 15},
        bv = {1, 0, 3},
        k = 2,
        d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0004"},
        d2 = {"greeting", "", "who", "LSomeone;", "kotlinDee"}
)
public final class GreetingKt {
   public static final void greeting(@NotNull Someone who) {
      Intrinsics.checkParameterIsNotNull(who, "who");
      String var1 = "Hello " + who + '!';
      boolean var2 = false;
      System.out.println(var1);
      var1 = "Hello " + who.getFirstName() + ' ' + who.getLastName() + '!';
      var2 = false;
      System.out.println(var1);
      var1 = StringsKt.trimIndent("\n        multi\n        line \"" + who + "\"\n        string");
      var2 = false;
      System.out.println(var1);
   }
}