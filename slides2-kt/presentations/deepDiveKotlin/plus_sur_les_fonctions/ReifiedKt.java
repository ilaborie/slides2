package _13_advanced_function;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
        mv = {1, 1, 9},
        bv = {1, 0, 2},
        k = 2,
        d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\u001a\u0019\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005"},
        d2 = {"main", "", "args", "", "", "([Ljava/lang/String;)V"}
)
public final class ReifiedKt {
    public static final void main(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        Pojo p1 = new Pojo();
        p1.setName("Plop1");
        System.out.println(p1);
        JavaBeanBuilder this_$iv = JavaBeanBuilder.INSTANCE;
        Pojo p2 = (Pojo)this_$iv.createBean(Pojo.class);
        p2.setName("Plop2");
        System.out.println(p2);
    }
}