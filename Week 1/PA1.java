package Algosim;
import java.util.Scanner;

 class PA1_1 {
    static int k = 0;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int number = sc.nextInt();
        System.out.println(1 + calRank(number));
    }
    private static int calRank(int n){
        if(n == 0) {    //Base case
            k = sc.nextInt();
            return 0;
        }
        else{   //Recursive case
            int a = sc.nextInt();
            int rankSize = calRank(n-1);
            if(k > a)   //k가 더 클 경우
                return ++rankSize;
            else    //k가 더 작은 경우
                return rankSize;
        }
    }
}

















