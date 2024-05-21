import models.Point;
import utils.AreaValidator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        var point = new Point();
        System.out.println("Enter point:");
        System.out.print("x = ");
        point.setX(sc.nextDouble());
        System.out.print("y = ");
        point.setY(sc.nextDouble());
        System.out.print("r = ");
        point.setR(sc.nextDouble());
        sc.close();
        boolean result = AreaValidator.checkArea(point);
        if (result) {
            System.out.println("Hit!");
        } else {
            System.out.println("Miss!");
        }
    }
}
