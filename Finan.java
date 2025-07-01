import java.util.Scanner;
public class Finan {
    public static void main(String[] args){
        Scanner Money = new Scanner(System.in);
        double M = Money.nextDouble();
        double inter = Money.nextDouble();
        double interest = M * (inter / 1200);
        System.out.println(interest);
    }
}
