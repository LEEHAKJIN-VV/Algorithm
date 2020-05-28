package Algosim;

import java.util.Scanner;

class PA1_3{
    static int count = 0;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int number = sc.nextInt();
        int[] data = new int[number];
        readInput(number,data,0);
        int k = sc.nextInt();
        selectOne(data,number,k,number,number);
        System.out.println(1 + count);
    }

    private static void readInput(int n,int[] data,int i){  //data 입력 시간복잡도 O(n)
        if(n == 0)
            return;
        data[i] = sc.nextInt();
        readInput(n-1,data,i+1);
    }
    private static void selectOne(int[] data,int n,int key,int size,int number){
        if(n == 0)
            return;
        else{
            selectOne(data,n-1,key,size,number);    //0까지 호출한후 돌아가면서 n-1값 넘겨줌
            selectTwo(data,n,key,data[n-1],size,number);    //첫번쨰 값 돌려줌
        }
    }
    private static void selectTwo(int[] data, int n,int key,int one,int size,int number){
        if(size == n-1)
            return;
        else{
            selectTwo(data,n,key,one,size-1,number);
            if(size == number)  //인덱스 에러 방지
                return;
            selectThree(data,size+1,key,one,data[size],number);
        }
    }
    private static void selectThree(int[] data, int n,int key,int one, int two,int size){
        if(n == size)
            return;
        else{
            selectThree(data,n+1,key,one,two,size);
            if(key == one + two + data[n])
                count++;
        }
    }
}