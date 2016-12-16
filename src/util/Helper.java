package util;

/**
 * Created by Fun on 2016/12/16.
 */
public class Helper {

    public static double maxOf(double... args) {
        double max = Double.MIN_VALUE;
        for (double d : args) {
            max = Math.max(max, d);
        }
        return max;
    }

    public static double minOf(double... args) {
        double min = Double.MAX_VALUE;
        for (double d : args) {
            min = Math.min(min, d);
        }
        return min;
    }
}
