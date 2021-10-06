package red.felnull.otyacraftengine.util;

import net.minecraft.util.math.BlockPos;

public class IKSGMath {
    @Deprecated
    public static int convertStringToInteger(String name) {
        return name.hashCode();
    }

    public static int averageInt(int... n) {
        int r = 0;
        for (int a : n) {
            r += a;
        }
        r /= n.length;
        return r;
    }

    public static float averageFloat(float... n) {
        float r = 0;
        for (float a : n) {
            r += a;
        }
        r /= n.length;
        return r;
    }

    public static long averageLong(long... n) {
        long r = 0;
        for (long a : n) {
            r += a;
        }
        r /= n.length;
        return r;
    }

    public static double averageDouble(double... n) {
        double r = 0;
        for (double a : n) {
            r += a;
        }
        r /= n.length;
        return r;
    }
@Deprecated
    public static int positiveInt(int n) {
        int r = n;
        if (r < 0)
            r *= -1;
        return r;
    }
    @Deprecated
    public static float positiveFloat(float n) {
        float r = n;
        if (r < 0)
            r *= -1;
        return r;
    }
    @Deprecated
    public static long positiveLong(long n) {
        long r = n;
        if (r < 0)
            r *= -1;
        return r;
    }
    @Deprecated
    public static double positiveDouble(double n) {
        double r = n;
        if (r < 0)
            r *= -1;
        return r;
    }
    @Deprecated
    public static int negativeInt(int n) {
        int r = n;
        if (r > 0)
            r *= -1;
        return r;
    }
    @Deprecated
    public static float negativeFloat(float n) {
        float r = n;
        if (r > 0)
            r *= -1;
        return r;
    }
    @Deprecated
    public static long negativeLong(long n) {
        long r = n;
        if (r > 0)
            r *= -1;
        return r;
    }
    @Deprecated
    public static double negativeDouble(double n) {
        double r = n;
        if (r > 0)
            r *= -1;
        return r;
    }
    @Deprecated
    public static int differenceInt(int n1, int n2) {
        int r = n1 - n2;
        if (r < 0)
            r *= -1;
        return r;
    }
    @Deprecated
    public static float differenceFloat(float n1, float n2) {
        float r = n1 - n2;
        if (r < 0)
            r *= -1;
        return r;
    }
    @Deprecated
    public static long differenceLong(long n1, long n2) {
        long r = n1 - n2;
        if (r < 0)
            r *= -1;
        return r;
    }
    @Deprecated
    public static double differenceDouble(double n1, double n2) {
        double r = n1 - n2;
        if (r < 0)
            r *= -1;
        return r;
    }

    public static int mostInt(int... nums) {
        int most = 0;
        for (int num : nums) {
            if (most < num) {
                most = num;
            }
        }
        return most;
    }

    public static float mostFloat(float... nums) {
        float most = 0;
        for (float num : nums) {
            if (most < num) {
                most = num;
            }
        }
        return most;
    }

    public static long mostLong(long... nums) {
        long most = 0;
        for (long num : nums) {
            if (most < num) {
                most = num;
            }
        }
        return most;
    }

    public static double mostDouble(double... nums) {
        double most = 0;
        for (double num : nums) {
            if (most < num) {
                most = num;
            }
        }
        return most;
    }

    public static int leastInt(int... nums) {
        int most = nums[0];
        for (int num : nums) {
            if (most > num) {
                most = num;
            }
        }
        return most;
    }

    public static float leastFloat(float... nums) {
        float most = nums[0];
        for (float num : nums) {
            if (most > num) {
                most = num;
            }
        }
        return most;
    }

    public static long leastLong(long... nums) {
        long most = nums[0];
        for (long num : nums) {
            if (most > num) {
                most = num;
            }
        }
        return most;
    }

    public static double leastDouble(double... nums) {
        double most = nums[0];
        for (double num : nums) {
            if (most > num) {
                most = num;
            }
        }
        return most;
    }

    public static float coordinateDistance(int x1, int y1, int x2, int y2) {

        double xk = positiveFloat(x1 - x2);
        double yk = positiveFloat(y1 - y2);

        return (float) Math.sqrt((xk * xk) + (yk * yk));
    }

    public static float coordinateDistance(int x1, int y1, int z1, int x2, int y2, int z2) {

        double xk = positiveFloat(x1 - x2);
        double yk = positiveFloat(y1 - y2);
        double zk = positiveFloat(z1 - z2);

        double d2d = Math.sqrt((xk * xk) + (zk * zk));

        return (float) Math.sqrt((d2d * d2d) + (yk * yk));
    }

    public static float coordinateDistance(BlockPos pos1, BlockPos pos2) {
        return coordinateDistance(pos1.getX(), pos1.getY(), pos1.getZ(), pos2.getX(), pos2.getY(), pos2.getZ());
    }

    public static float roundDown(float num, int dwon) {

        for (int c = 0; c < dwon + 2; c++) {
            if (String.valueOf(num).toCharArray().length >= dwon + 2 - c) {
                return Float.valueOf(String.valueOf(num).substring(0, dwon + 2 - c));
            }
        }
        return 0;
    }

    public static int[] getStringInts(String st) {

        int[] ints = new int[st.toCharArray().length];

        for (int i = 0; i < st.toCharArray().length; i++) {
            ints[i] = st.toCharArray()[i];
        }

        return ints;
    }

    public static int[] getStringIntArry(String st) {
        String[] sot = st.split(":");
        int[] ot = new int[sot.length];
        for (int i = 0; i < sot.length; i++) {
            ot[i] = Integer.valueOf(sot[i]);
        }
        return ot;
    }
}
