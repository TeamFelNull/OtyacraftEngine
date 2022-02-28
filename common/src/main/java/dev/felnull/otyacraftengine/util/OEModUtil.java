package dev.felnull.otyacraftengine.util;

import dev.felnull.otyacraftengine.impl.OEExpectPlatform;

import java.util.List;

public class OEModUtil {

    public static <T> List<T> getEntryPoints(String name, Class<?> annotationClass, Class<T> interfaceClass) {
        return OEExpectPlatform.getEntryPoints(name, annotationClass, interfaceClass);
    }
}
