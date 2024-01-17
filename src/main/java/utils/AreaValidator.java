package utils;

import models.Point;

public class AreaValidator {
    private static final double X_MAX = 1;
    private static final double X_MIN = -5;
    private static final double Y_MAX = 3;
    private static final double Y_MIN = -5;
    private static final double R_MAX = 5;
    private static final double R_MIN = 1;

    public static boolean checkArea(Point point) {
        double x = point.getX();
        double y = point.getY();
        double r = point.getR();
        if (x > X_MAX || x < X_MIN) {
            return false;
        }
        if (y > Y_MAX || y < Y_MIN) {
            return false;
        }
        if (r > R_MAX || r < R_MIN) {
            return false;
        }
        return isCircle(x, y, r) || isRectangle(x, y, r) || isTriangle(x, y, r);
    }

    private static boolean isRectangle(double x, double y, double r) {
        return y >= -r && x >= -r / 2 && x <= 0 && y <= 0;
    }

    private static boolean isCircle(double x, double y, double r) {
        return x * x + y * y <= (r * r) && x <= 0 && y >= 0;
    }

    private static boolean isTriangle(double x, double y, double r) {
        return x >= 0 && y >= 0 && y <= -x + r;
    }
}
