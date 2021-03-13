package red.felnull.otyacraftengine.util;

public class IKSGMath {
    public static final float ABSOLUTE_ZERO = -273.15f;

    public static float toKelvin(float celsius) {
        return celsius - ABSOLUTE_ZERO;
    }

    public static float toCelsius(float kelvin) {
        return kelvin + ABSOLUTE_ZERO;
    }

    public static int toKelvin(int celsius) {
        return celsius + 273;
    }

    public static int toCelsius(int kelvin) {
        return kelvin - 273;
    }

}
