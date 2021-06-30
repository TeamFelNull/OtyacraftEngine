package red.felnull.otyacraftengine.util;

public class IKSGUtil {
    public static boolean isInstanceofClass(Class<?> target, Class<?> instanceofCls) {
        if (target.getSuperclass() == null)
            return false;

        if (target.getSuperclass() == instanceofCls || target == instanceofCls)
            return true;

        return isInstanceofClass(target.getSuperclass(), instanceofCls);
    }
}
