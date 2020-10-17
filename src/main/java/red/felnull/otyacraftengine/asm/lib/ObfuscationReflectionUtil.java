package red.felnull.otyacraftengine.asm.lib;

import red.felnull.otyacraftengine.util.IKSGReflectionUtil;

public class ObfuscationReflectionUtil {

    public static <T, E> T getPrivateValue(Class<? super E> classToAccess, E instance, String fieldName) {
        return IKSGReflectionUtil.getPrivateField(classToAccess, instance, fieldName);
    }

    public static <T, E> T getPrivateValue(Class<? super E> classToAccess, E instance, RefName fieldName) {
        return IKSGReflectionUtil.getPrivateField(classToAccess, instance, fieldName);
    }

    public static <T, E> T getPrivateValue(Class<? super E> classToAccess, String fieldName) {
        return IKSGReflectionUtil.getPrivateField(classToAccess, fieldName);
    }

    public static <T, E> T getPrivateValue(Class<? super E> classToAccess, RefName fieldName) {
        return IKSGReflectionUtil.getPrivateField(classToAccess, fieldName);
    }
}
