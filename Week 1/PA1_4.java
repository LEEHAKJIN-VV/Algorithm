package Algosim;

import java.util.Scanner;

class PA1_4{
    static int com = 100000;
    static int near = 0;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int number = sc.nextInt();
        int[] data = new int[number];

        for (int i = 0; i <number ; i++)
            data[i] = sc.nextInt();
        int k = sc.nextInt();

        binarySearch(k,data,0,number-1);
        System.out.println(near);
    }
    private static void binarySearch(int key,int[] data,int begin,int end){
        if(begin > end)
            return;
        else{
            int middle = (begin+end) /2;
            if(com == (data[middle] - key)){
                if(near > data[middle])
                    near = data[middle];
            }
            else if(com > (data[middle] - key)){
                com = Math.abs(data[middle] - key);
                near = data[middle];
            }

            if(data[middle] > key){
                binarySearch(key,data,begin,middle-1);
            }
            else
                binarySearch(key,data,middle+1,end);
        }
    }
}