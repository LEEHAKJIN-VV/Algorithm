package Algosim;

import java.io.*;
import java.util.*;

//insertVertex 에서 list의 맨처음을 각 정점으로 하는 node로 구성했다.
class GraphNode {
    String city;
    GraphNode link, pre;
    double longitude, latitude, distance, dis = -1;
    boolean check = false;

    public GraphNode(String city, double longitude, double latitude, double distance) {
        this.city = city;
        this.longitude = longitude;
        this.distance = distance;
        this.latitude = latitude;
        this.link = null;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}

public class Graph {
    private static ArrayList<GraphNode> list;
    static Scanner sc = new Scanner(System.in);

    public Graph() {
        list = new ArrayList<>();
    }

    void readFile() {
        String fileName1 = "alabama.txt";
        String fileName2 = "roadlist.txt";
        String filePath = Graph.class.getResource("").getPath();
        File file1 = new File(filePath + fileName1);
        File file2 = new File(filePath + fileName2);
        try {
            BufferedReader br1 = new BufferedReader(new FileReader(file1));
            BufferedReader br2 = new BufferedReader(new FileReader(file2));
            String str;
            while (true) {    //지명,경도,위도 읽음
                str = br1.readLine();
                if (str == null)
                    break;
                insertVertex(str);
            }
            while (true) {    //도로연결정보 읽음
                str = br2.readLine();
                if (str == null)
                    break;
                add(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void readCommand() {
        String str;
        Scanner sc = new Scanner(System.in);
        label:
        while (true) {
            System.out.print("$ ");
            str = sc.nextLine();
            switch (str) {
                case "BFS":     //BFS 입력시 10 hop이내 모든 지점 출력
                    bfs();
                    reset();
                    break;
                case "DFS":
                    System.out.print("시작하는 하는 도시 이름을 입력하세요: ");
                    String city = sc.nextLine();        //출발할 도시 이름 입력
                    GraphNode x = graphSearch(city);    //출발할 도시 찾음
                    dfsAll(x);
                    reset();
                    break;
                case "Dijkstra":
                    String ct1, ct2; //두 지점

                    System.out.print("출발 도시 입력: ");
                    ct1 = sc.nextLine();
                    System.out.print("목적지 도시 입력: ");
                    ct2 = sc.nextLine();   // 두지점 입력

                    dijkstra(ct1, ct2);
                    reset();
                    break;
                case "break":
                    break label;
                default:
                    System.out.println("잘못된 명령어입니다 다시 입력해주세요.");
                    break;
            }
        }
    }

    private static void GraphInsert(String city1, String city2) { //지점과 도로를 연결하는 메소드
        GraphNode x = graphSearch(city1);
        if (x == null) return;  //city1의 이름으로 Node x를 찾음
        GraphNode y = graphSearch(city2);
        if (y == null) return; //city2의 위도,경도 도시 이름을 불러오기 위한 노드

        double distance = calDistance(x.getLatitude(), x.getLongitude(), y.getLatitude(), y.getLongitude());
        GraphNode newNode1 = new GraphNode(city2, y.getLongitude(), y.getLatitude(), distance); //인접리스트에 연결한 node 만듬
        GraphNode newNode2 = new GraphNode(city1, x.getLongitude(), x.getLatitude(), distance); //인접리스트에 연결한 node 만듬

        if (x.link == null)  //제일 처음 연결
            x.link = newNode1;
        else {   //제일 처음 연결이 아님
            newNode1.link = x.link;
            x.link = newNode1;
        }

        if (y.link == null)  //제일 처음 연결
            y.link = newNode2;
        else {   //제일 처음 연결이 아님
            newNode2.link = y.link;
            y.link = newNode2;
        }
    }

    private static void insertVertex(String str) {    //정점 추가
        String[] st = str.split("\t");
        double b1 = Double.parseDouble(st[1]);
        double b2 = Double.parseDouble(st[2]);
        GraphNode newNode = new GraphNode(st[0], b1, b2, 0); //city 와 경도,위도를 이용하여 정점 만듬
        newNode.link = null;
        list.add(newNode);
    }

    private static void add(String str) {   //두번쨰 들어온 입력 데이터 값으로 Node을 만듬
        String[] st = str.split("\t");
        GraphInsert(st[0], st[1]);   //연결한 도시 정보를 넘김
    }

    private static void bfs() {
        System.out.print("찾고자 하는 도시 이름을 입력하세요: ");
        String name = sc.nextLine();    // bfs할 도시 이름
        GraphNode x = graphSearch(name); //시작 지점 노드
        GraphNode y;                     //x를 한칸 뒤 따라갈 Node

        if (x == null) return;
        x.dis = 0;
        x.check = true;

        Queue<GraphNode> que = new LinkedList<>();  //q를 만듬
        que.offer(x);   //que에 시작할 노드 x 삽입
        while (que.peek() != null) {  //q가 비면 null 반환
            y = que.poll();
            GraphNode z = y.link;
            while (z != null) {
                GraphNode u = graphSearch(z.city);  //z의 city로 시작하는 node 찾음
                if (!u.check) { //아직 방문하지 않았다.  방문 한 경우는 그대로 둠
                    u.dis = y.dis + 1;    //edge + 1 을해준다
                    if (u.dis <= 10 && u.dis > 0)
                        printVertex(u.city);
                    u.check = true;
                    que.offer(u);
                }
                z = z.link;
            }
        }
    }

    private static void dfsAll(GraphNode x) {   //dfs all
        printVertex(x.city);
        dfs(x);
        for (int i = 0; i < list.size(); i++)
            if (!list.get(i).check)
                dfs(list.get(i));
    }

    private static void dfs(GraphNode x) {  //dfs
        x.check = true;
        GraphNode u = x.link;
        while (u != null) {
            GraphNode z = graphSearch(u.city);
            if (!z.check) {
                printVertex(z.city);
                dfs(z);
            }
            u = u.link;
        }
    }

    private static void dijkstra(String ct1, String ct2) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).dis = 1000000;
            list.get(i).pre = null;
        }

        GraphNode src = graphSearch(ct1);
        GraphNode dest = graphSearch(ct2);  //출발지,목적지
        src.dis = 0;    // 출발 노드 dis[s] = 0 만듬
        PriorityQueue<GraphNode> graphNodePriorityQueue = getPriorityQueue(ct1);

        while (!graphNodePriorityQueue.isEmpty()) {
            GraphNode x = graphNodePriorityQueue.poll();
            x.check = true;   // 집합 s에 넣음
            GraphNode y = x.link;
            while (y != null) {   // 집합x 가 아니고 인접합 지점들의 div[]를 계산
                GraphNode z = graphSearch(y.city);
                double div = findWeight(x, z);
                relax(graphNodePriorityQueue, x, z, div);
                y = y.link;
            }

        }
        System.out.print(src.city + " -> ");
        printPath(src, dest);
        System.out.println(dest.city);
        System.out.println("거리: " + graphSearch(ct2).dis);
    }

    private static PriorityQueue<GraphNode> getPriorityQueue(String str) {
        PriorityQueue<GraphNode> graphNodePriorityQueue = new PriorityQueue<>();
        graphNodePriorityQueue.offer(graphSearch(str));
        return graphNodePriorityQueue;
    }

    private static void relax(PriorityQueue<GraphNode> graphNodePriorityQueue, GraphNode u, GraphNode v, double distance) {
        if (!(v.check) && v.dis > u.dis + distance) {  //relaxation
            graphNodePriorityQueue.remove(v);
            graphNodePriorityQueue.offer(v);
            v.dis = u.dis + distance;
            v.pre = u;
        }
    }

    private static void printPath(GraphNode src, GraphNode dest) {   // 경로를 출력 출발,목적지 노드 빼고 나머지 노드출력
        GraphNode x = dest.pre;
        if (x == null || x.city.equals(src.city)) return;
        printPath(src, x);
        System.out.print(x.city + " -> ");
    }

    private static double findWeight(GraphNode x, GraphNode y) {  // 두지점사이의 거리 구함
        return calDistance(x.getLatitude(), x.getLongitude(), y.getLatitude(), y.getLongitude());
    }

    private static GraphNode graphSearch(String name) { // 지점 이름으로 인접리스트 배열 요소 반환
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).city.equals(name))
                return list.get(i);
        }
        return null;
    }

    private static void printVertex(String name) {
        GraphNode x = graphSearch(name);
        if (x == null) return;
        System.out.print(name + " ");
        System.out.print(x.latitude + " ");
        System.out.print(x.longitude + " ");
        System.out.println();
    }

    private static void reset() {    //방문 체크 reset 해줌
        for (int i = 0; i < list.size(); i++)
            list.get(i).check = false;
    }

    // 매개변수는 첫번째 지점의 위도(lat1), 경도(lon1), 두번째 지점의 위도(lat2), 경도(lon2) 순서이다.
    public static double calDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta, dist;
        theta = lon1 - lon2;
        dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344; // 단위 mile 에서 km 변환.
        dist = dist * 1000.0; // 단위 km 에서 m 로 변환
        return dist;
    }

    // 주어진 도(degree) 값을 라디언으로 변환
    private static double deg2rad(double deg) {
        return (double) (deg * Math.PI / (double) 180);
    }

    // 주어진 라디언(radian) 값을 도(degree) 값으로 변환
    private static double rad2deg(double rad) {
        return (double) (rad * (double) 180 / Math.PI);
    }
}

class Main {
    public static void main(String[] args) {
        Graph ph = new Graph();
        ph.readFile();
        ph.readCommand();
    }
}


