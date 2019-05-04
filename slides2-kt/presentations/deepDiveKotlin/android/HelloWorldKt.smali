.class public final L_00_helloworld/HelloWorldKt;
.super Ljava/lang/Object;
.source "HelloWorld.kt"

.annotation system Ldalvik/annotation/SourceDebugExtension;
  value = "SMAP\nHelloWorld.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HelloWorld.kt\n_00_helloworld/HelloWorldKt\n*L\n1#1,5:1\n*E\n"
.end annotation
.annotation runtime Lkotlin/Metadata;
  bv = {
    1,
    0,
    2
  }
  d1 = {
    "\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\u001a\u0019\u0010\u0000\u001a\u00020\u00012\u000c\u0010\u0002\u001a\u0008\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0005"
  }
  d2 = {
    "main",
    "",
    "args",
    "",
    "",
    "([Ljava/lang/String;)V"
  }
  k = 2
  mv = {
    1,
    1,
    9
  }
.end annotation

.method public final static main([Ljava/lang/String;)V
  .parameter # [Ljava/lang/String;
    .annotation build Lorg/jetbrains/annotations/NotNull;
    .end annotation
  .end parameter
  .registers 2
    const-string v0, "args"
    invoke-static { p0, v0 }, Lkotlin/jvm/internal/Intrinsics;->checkParameterIsNotNull(Ljava/lang/Object;Ljava/lang/String;)V
    const-string p0, "Hello RivieraDev"
  .line 4
    sget-object v0, Ljava/lang/System;->out:Ljava/io/PrintStream;
    invoke-virtual { v0, p0 }, Ljava/io/PrintStream;->println(Ljava/lang/Object;)V
    return-void
.end method