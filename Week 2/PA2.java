package Algosim;
import java.util.Scanner;

public class PA2_1 {
    static int[][] maze;
    static int N;
    static int k;
    static int count = 0;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(true) {
            N = sc.nextInt(); //미로의 크기 입력
            if (N > 16) {
                System.out.println("16 이하로 입력하세요.");
                continue;
            }
            break;
        }
        maze = new int[N][N];
        readMaze(); //미로 읽음
        k = sc.nextInt();   //k값 입력
        mazePath(0, 0, 0);
        System.out.println(count);
    }
    private static void mazePath(int x, int y, int len) {
        if (x < 0 || y < 0 || x >= N || y >= N || maze[x][y] != 0)
            return;
        else if (x == N - 1 && y == N - 1) {
            if (len <= k)
                count++;
            return;
        } else {
            maze[x][y] = 2;
            mazePath(x - 1, y, len + 1);
            mazePath(x, y + 1, len + 1);
            mazePath(x + 1, y, len + 1);
            mazePath(x, y - 1, len + 1);
            maze[x][y] = 0;
            return;
        }
    }
    private static void readMaze() {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j <maze[i].length; j++) {
                maze[i][j] = sc.nextInt();
            }
        }
    }
}

class PA2_2 {
    static int[][] maze;
    static int N;
    static int dir = 1; // "0" 이면 x값 증가 (밑으로) "1" 이면 y값 증가(앞으로)
    static int k = 0;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        N = sc.nextInt();
        maze = new int[N][N];
        readMaze();
        mazePath(0,0);
        if(k == 1)
            System.out.println("Yes");
        else
            System.out.println("No");
    }

    private static int mazePath(int x, int y) {
        if (x < 0 || y < 0 || x >= N || y >= N || maze[x][y] != 0) {
            dir++;
            return -1;
        }
        else if (x == N - 1 && y == N - 1) {
            k = 1;
            return 0;
        } else {
           maze[x][y] = 3; //왔던길 되돌아가기 방지
            if (dir % 2 == 1) {  //홀수이면  y값 증가
                int s = mazePath(x, y + 1);
                if (s < 0) {
                    mazePath(x + 1, y);
                }
                mazePath(x-1,y);    //위로 가는지도 검사
                return 0;
            }
            if (dir % 2 == 0) { //짝수이면 x 값 증가
                int s = mazePath(x + 1, y);
                if (s < 0) {
                    mazePath(x, y + 1);
                }
                mazePath(x-1,y);    //위로 가는지도 검사
            }
        }
        return 0;
    }
    private static void readMaze() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                maze[i][j] = sc.nextInt();
            }
        }
    }
}