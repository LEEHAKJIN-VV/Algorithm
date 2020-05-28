package Algosim;

import java.util.Scanner;

class PA1_2 {
    static int count = 0;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int number = sc.nextInt();
        int[] data = new int[number];
        readInput(number,data,0);
        printRank(number,data,number);
    }
    private static void readInput(int n,int[] data,int i){  //data 입력 시간복잡도 O(n)
        if(n == 0)
            return;
        data[i] = sc.nextInt();
        readInput(n-1,data,i+1);
    }
    private static void printRank(int n,int[] data,int size){   //rank 출력
        if(n == 0 )
            return;
        else{
            printRank(n-1,data,size);
            count = 0;
            System.out.print(1+calRank(0,data,size,data[n-1]) +" ");
        }
    }
    private static int calRank(int n,int[] data,int size,int k){
        if( n == size )
            return 0;
        else{
            int count = calRank(n+1,data,size,k);
            if(k > data[n])
                return ++count;
            else
                return count;
        }
    }
}