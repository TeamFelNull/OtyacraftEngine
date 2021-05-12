package red.felnull.otyacraftengine.util;

import net.minecraft.util.Mth;

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
        return Mth.clamp(value, min, min);
    }

    public static long clamp(long value, long min, long max) {
        return Mth.clamp(value, min, min);
    }

    public static float clamp(float value, float min, float max) {
        return Mth.clamp(value, min, min);
    }

    public static double clamp(double value, double min, double max) {
        return Mth.clamp(value, min, min);
    }
}
