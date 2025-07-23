package Lab4;
import java.util.*;
class AscendSortFreq {
    private double[] A;

    public AscendSortFreq(double[] A) {
        this.A = A;
    }

    public double[] AscendSort() {
   
        double[] temp = A.clone();

        // Bubble Sort
        for (int i = 0; i < temp.length - 1; i++) {
            for (int j = 0; j < temp.length - 1 - i; j++) {
                if (temp[j] > temp[j + 1]) {
                    double swap = temp[j];
                    temp[j] = temp[j + 1];
                    temp[j + 1] = swap;
                }
            }
        }

 
        int uniqueCount = 1;
        for (int i = 1; i < temp.length; i++) {
            if (temp[i] != temp[i - 1]) {
                uniqueCount++;
            }
        }

        double[] B = new double[uniqueCount];
        B[0] = temp[0];
        int index = 1;
        for (int i = 1; i < temp.length; i++) {
            if (temp[i] != temp[i - 1]) {
                B[index++] = temp[i];
            }
        }

        return B;
    }

    public int[] SortCommuFreq(double[] B) {
        int[] C = new int[B.length];

        for (int i = 0; i < B.length; i++) {
            int count = 0;
            for (double num : A) {
                if (num <= B[i]) {
                    count++;
                }
            }
            C[i] = count;
        }

        return C;
    }
}

public class No4 {
     public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        double[] data = new double[n];

        for (int i = 0; i < n; i++) {
            data[i] = sc.nextDouble();
        }

        AscendSortFreq sorter = new AscendSortFreq(data);
        double[] sorted = sorter.AscendSort();
        int[] cumulativeFreq = sorter.SortCommuFreq(sorted);

        for (double num : sorted) {
            System.out.print((int)num + " ");
        }
        System.out.println();

        for (int freq : cumulativeFreq) {
            System.out.print(freq + " ");
        }
        System.out.println();
    }
}
