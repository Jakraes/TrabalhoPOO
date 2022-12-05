package pt.iscte.poo.util;

public class Utils {
    public static double halfRound(double i) {
        double min = (int) i;
        double max = min + 1;
        double middle = min + 0.5;
        double result;

        if (i >= middle) {
            double dist1 = Math.abs(i - middle);
            double dist2 = Math.abs(max - i);
            result = (dist1 > dist2) ? max : middle;
        } else {
            double dist1 = Math.abs(middle - i);
            double dist2 = Math.abs(i - min);
            result = (dist1 > dist2) ? min : middle;
        }
        return result;
    }
}
