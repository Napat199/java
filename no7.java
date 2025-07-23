package Lab3;
import java.util.Scanner;

class MyTriangle {

    public boolean isValid(double side1, double side2, double side3) {
        return (side1 + side2 > side3) &&
               (side1 + side3 > side2) &&
               (side2 + side3 > side1);
    }

    public double area(double side1, double side2, double side3) {
        double s = (side1 + side2 + side3) / 2.0;
        return Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));
    }
}
public class No7 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double side1 = sc.nextDouble();
        double side2 = sc.nextDouble();
        double side3 = sc.nextDouble();

        MyTriangle triangle = new MyTriangle();

        if (triangle.isValid(side1, side2, side3)) {
            System.out.println("1");
            System.out.printf("%.2f\n", triangle.area(side1, side2, side3));
        } else {
            System.out.println("0");
        }
    }
}
