import java.util.Scanner;

public class Faren {
    public static void main(String[] args) {
        Scanner Faren = new Scanner(System.in);
        double F = Faren.nextDouble();
        System.out.println(F);
        double Celsius= (5.0/9.0)*(F-32);
        System.out.println(Celsius);
    }
}
