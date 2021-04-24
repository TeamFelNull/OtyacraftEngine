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

    public static int clamp(int value, int min, int max) {
        return Math.max(Math.min(value, max), min);
    }

    public static long clamp(long value, long min, long max) {
        return Math.max(Math.min(value, max), min);
    }

    public static float clamp(float value, float min, float max) {
        return Math.max(Math.min(value, max), min);
    }

    public static double clamp(double value, double min, double max) {
        return Math.max(Math.min(value, max), min);
    }

}
