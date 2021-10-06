package red.felnull.otyacraftengine.util;

import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import red.felnull.otyacraftengine.asm.lib.RefName;

import java.lang.reflect.Method;

public class IKSGReflectionUtil {
    @Deprecated
    public static <T, E> T getPrivateField(Class<? super E> classToAccess, E instance, String fieldName) {
        return ObfuscationReflectionHelper.getPrivateValue(classToAccess, instance, fieldName);
    }

    @Deprecated
    public static <T, E> T getPrivateField(Class<? super E> classToAccess, E instance, RefName fieldName) {
        return getPrivateField(classToAccess, instance, fieldName.name());
    }

    @Deprecated
    public static <T, E> T getPrivateField(Class<? super E> classToAccess, String fieldName) {
        return getPrivateField(classToAccess, null, fieldName);
    }

    @Deprecated
    public static <T, E> T getPrivateField(Class<? super E> classToAccess, RefName fieldName) {
        return getPrivateField(classToAccess, fieldName.name());
    }

    @Deprecated
    public static <T, E> void setPrivateField(Class<? super T> classToAccess, T instance, E value, String fieldName) {
        ObfuscationReflectionHelper.setPrivateValue(classToAccess, instance, value, fieldName);
    }

    @Deprecated
    public static <T, E> void setPrivateField(Class<? super T> classToAccess, T instance, E value, RefName fieldName) {
        setPrivateField(classToAccess, instance, value, fieldName.name());
    }

    @Deprecated
    public static <T, E> void setPrivateField(Class<? super T> classToAccess, E value, String fieldName) {
        setPrivateField(classToAccess, null, value, fieldName);
    }

    @Deprecated
    public static <T, E> void setPrivateField(Class<? super T> classToAccess, E value, RefName fieldName) {
        setPrivateField(classToAccess, value, fieldName.name());
    }

    @Deprecated
    public static <T, E> T getPrivateMethod(Class<? super E> classToAccess, E instance, String fieldName, Class<?>[] classs, Object... args) {
        Method method = ObfuscationReflectionHelper.findMethod(classToAccess, fieldName, classs);
        method.setAccessible(true);
        try {
            return (T) method.invoke(instance, args);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Deprecated
    public static <T, E> T getPrivateMethod(Class<? super E> classToAccess, E instance, RefName fieldName, Class<?>[] classs, Object... args) {
        return getPrivateMethod(classToAccess, instance, fieldName.name(), classs, args);
    }

    @Deprecated
    public static <T, E> T getPrivateMethod(Class<? super E> classToAccess, String fieldName, Class<?>[] classs, Object... args) {
        return getPrivateMethod(classToAccess, null, fieldName, classs, args);
    }

    @Deprecated
    public static <T, E> T getPrivateMethod(Class<? super E> classToAccess, RefName fieldName, Class<?>[] classs, Object... args) {
        return getPrivateMethod(classToAccess, fieldName.name(), classs, args);
    }
}
