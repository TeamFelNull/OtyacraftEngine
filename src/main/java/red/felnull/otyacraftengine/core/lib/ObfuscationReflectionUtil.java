package red.felnull.otyacraftengine.core.lib;

import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class ObfuscationReflectionUtil {

    public static <T, E> T getPrivateValue(Class<? super E> classToAccess, E instance, String fieldName) {
        return ObfuscationReflectionHelper.getPrivateValue(classToAccess, instance, fieldName);
    }

    public static <T, E> T getPrivateValue(Class<? super E> classToAccess, E instance, RefName fieldName) {
        return getPrivateValue(classToAccess, instance, fieldName.name());
    }

    public static <T, E> T getPrivateValue(Class<? super E> classToAccess, String fieldName) {
        return getPrivateValue(classToAccess, null, fieldName);
    }

    public static <T, E> T getPrivateValue(Class<? super E> classToAccess, RefName fieldName) {
        return getPrivateValue(classToAccess, fieldName.name());
    }
}
