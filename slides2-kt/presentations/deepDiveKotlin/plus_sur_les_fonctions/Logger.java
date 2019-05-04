package _13_advanced_function;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\rB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0014\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\nJ\u001f\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u00062\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\nH\u0082\bR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000"},
   d2 = {"L_13_advanced_function/Logger;", "", "name", "", "(Ljava/lang/String;)V", "level", "L_13_advanced_function/Logger$Level;", "info", "", "message", "Lkotlin/Function0;", "log", "lvl", "Level"}
)
public final class Logger {
   private final Logger.Level level;
   private final String name;

   public final void info(@NotNull Function0 message) {
      Intrinsics.checkParameterIsNotNull(message, "message");
      Logger.Level lvl$iv = Logger.Level.INFO;
      if (access$getLevel$p(this).compareTo((Enum)lvl$iv) >= 0) {
         String var4 = '[' + access$getLevel$p(this).name() + "] " + access$getName$p(this) + " - " + (String)message.invoke();
         System.out.println(var4);
      }

   }

   private final void log(Logger.Level lvl, Function0 message) {
      if (access$getLevel$p(this).compareTo((Enum)lvl) >= 0) {
         String var4 = '[' + access$getLevel$p(this).name() + "] " + access$getName$p(this) + " - " + (String)message.invoke();
         System.out.println(var4);
      }

   }

   public Logger(@NotNull String name) {
      Intrinsics.checkParameterIsNotNull(name, "name");
      super();
      this.name = name;
      this.level = Logger.Level.INFO;
   }

   // $FF: synthetic method
   @NotNull
   public static final Logger.Level access$getLevel$p(Logger $this) {
      return $this.level;
   }

   // $FF: synthetic method
   @NotNull
   public static final String access$getName$p(Logger $this) {
      return $this.name;
   }

   @Metadata(
      mv = {1, 1, 9},
      bv = {1, 0, 2},
      k = 1,
      d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0082\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\b"},
      d2 = {"L_13_advanced_function/Logger$Level;", "", "(Ljava/lang/String;I)V", "TRACE", "DEBUG", "INFO", "WARN", "ERROR", "FATAL"}
   )
   private static enum Level {
      TRACE,
      DEBUG,
      INFO,
      WARN,
      ERROR,
      FATAL;

      protected Level() {
      }
   }
}