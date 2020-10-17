package red.felnull.otyacraftengine.util;

import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import red.felnull.otyacraftengine.asm.lib.RefName;

import java.lang.reflect.Method;

public class IKSGReflectionUtil {
    public static <T, E> T getPrivateField(Class<? super E> classToAccess, E instance, String fieldName) {
        return ObfuscationReflectionHelper.getPrivateValue(classToAccess, instance, fieldName);
    }

    public static <T, E> T getPrivateField(Class<? super E> classToAccess, E instance, RefName fieldName) {
        return getPrivateField(classToAccess, instance, fieldName.name());
    }

    public static <T, E> T getPrivateField(Class<? super E> classToAccess, String fieldName) {
        return getPrivateField(classToAccess, null, fieldName);
    }

    public static <T, E> T getPrivateField(Class<? super E> classToAccess, RefName fieldName) {
        return getPrivateField(classToAccess, fieldName.name());
    }

    public static <T, E> void setPrivateField(Class<? super T> classToAccess, T instance, E value, String fieldName) {
        ObfuscationReflectionHelper.setPrivateValue(classToAccess, instance, value, fieldName);
    }

    public static <T, E> void setPrivateField(Class<? super T> classToAccess, T instance, E value, RefName fieldName) {
        setPrivateField(classToAccess, instance, value, fieldName.name());
    }

    public static <T, E> void setPrivateField(Class<? super T> classToAccess, E value, String fieldName) {
        setPrivateField(classToAccess, null, value, fieldName);
    }

    public static <T, E> void setPrivateField(Class<? super T> classToAccess, E value, RefName fieldName) {
        setPrivateField(classToAccess, value, fieldName.name());
    }

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

    public static <T, E> T getPrivateMethod(Class<? super E> classToAccess, E instance, RefName fieldName, Class<?>[] classs, Object... args) {
        return getPrivateMethod(classToAccess, instance, fieldName.name(), classs, args);
    }

    public static <T, E> T getPrivateMethod(Class<? super E> classToAccess, String fieldName, Class<?>[] classs, Object... args) {
        return getPrivateMethod(classToAccess, null, fieldName, classs, args);
    }

    public static <T, E> T getPrivateMethod(Class<? super E> classToAccess, RefName fieldName, Class<?>[] classs, Object... args) {
        return getPrivateMethod(classToAccess, fieldName.name(), classs, args);
    }
}
