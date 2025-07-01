import java.util.Scanner;

public class Financial{
    public static void main(String[] args){
        Scanner Money = new Scanner(System.in);
        int M = Money.nextInt();
        System.out.println(M);
        double fee = 0;
        for (int i = 0; i < 6; i++) {
            double month = (M + fee)* (1 + 0.00417);
            fee = month;
        }
        System.out.printf("After the sixth month, the account value is %.2f\n", fee);
    }
}
