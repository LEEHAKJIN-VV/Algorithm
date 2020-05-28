package Algosim;

import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

 class Node {
    String name; String company_name; String address;
    String zip; String phone;String email;
    Node left;Node right;Node parent;

    public Node(String name, String company_name, String address,
                String zip, String phone, String email) {
        this.name = name; this.company_name = company_name;
        this.address = address; this.zip = zip;
        this.phone = phone; this.email = email;
        left = null;right = null;
        parent = null;
    }
} class BinaryTree {
    Node root;
    int size=0;
    static int n = 0;
    static String[] cp = new String[3000];
    public BinaryTree() {
        root = null;
    }
    public BinaryTree(Node root) {
        this.root = root;
    }
    public static boolean compareTo(String str1, String str2) {
        if (str1.compareTo(str2) > 0)  //str1이 str2보다 큰경우
            return true;
        else
            return false;    //str1이 str2보다 작은경우
    }
    public static Node treeSearch(Node x, String k) {
        if (x == null || k.equals(x.name))
            return x;
        if (compareTo(x.name, k)) {     //찾을려는 k 왼쪽에있는경우
            System.out.println(x.name);
            return treeSearch(x.left, k);
        }
        else {
            System.out.println(x.name);
            return treeSearch(x.right, k);
        }
    }
    public static Node treeMinimum(Node x) {
        while (x.left != null)
            x = x.left;
        return x;
    }
    public static Node treeSuccessor(Node x) {
        Node y;
        if (x.right != null)
            return treeMinimum(x.right);
        y = x.parent;
        while (y != null && x == y.right) {
            x = y;
            y = y.parent;
        }
        return y;
    }
    public static void treeInsert(BinaryTree t, Node z) {
        Node y = null;
        Node x;
        x = t.root;
        while (x != null) {
            y = x;
            if (compareTo(x.name, z.name))    //x.name is big z.name
                x = x.left;
            else
                x = x.right;
        }
        z.parent = y;
        if (y == null)
            t.root = z;
        else if (compareTo(y.name, z.name))
            y.left = z;
        else
            y.right = z;
    }
    public static void treeDelete(BinaryTree t, Node z) {
        Node y;
        Node x;
        if (z.left == null || z.right == null)
            y = z;
        else
            y = treeSuccessor(z);
        if (y.left != null)
            x = y.left;
        else
            x = y.right;
        if (x != null)
            x.parent = y.parent;
        if (y.parent == null)
            t.root = x;
        else if (y == y.parent.left)
            y.parent.left = x;
        else
            y.parent.right = x;
        if (y != z)
            z.name = y.name;
    }
    public static void readFile(BinaryTree t) {
        System.out.print("$ read ");
        Scanner sc = new Scanner(System.in);
        String fileName = sc.nextLine();
        String filePath =BinaryTree.class.getResource("").getPath();
        File file = new File(filePath+fileName);
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String str;
            br.readLine();
            while(true) {
                str = br.readLine();
                if(str == null)
                    break;
                splitDataAdd(t,str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void splitDataAdd(BinaryTree t,String data) {
        StringTokenizer st1 = new StringTokenizer(data,",");
        int index = data.indexOf(",");
        String na = st1.nextToken();
        String str = data.substring(index+1,data.length()-1); //이름 뒤부터 문자열 생성
        String str2 = str.replace(", "," ");

        StringTokenizer str3 = new StringTokenizer(str2,",\"");
        String c_name = str3.nextToken(); String adrs = str3.nextToken();
        String zip = str3.nextToken(); String ph = str3.nextToken(); String em = str3.nextToken();
        add(t,na,c_name,adrs,zip,ph,em);
    }
    public static void readCommand(BinaryTree t) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("$ ");
            String command = sc.nextLine();
            StringTokenizer cmd = new StringTokenizer(command, " ");
            command = cmd.nextToken();
            if (command.equals("list")) {
                listInorder(t.root);
            } else if (command.equals("find")) {
                String name;
                name = cmd.nextToken();
                find(t, name); //찾고자 하는 단어 넘겨줌
            }
            else if(command.equals("trace")){
                String name;
                name = cmd.nextToken();
                trace(t,name);
            }
            else if (command.equals("delete")) {
                String name;
                name = cmd.nextToken();
                delete(t, name);   //삭제하고자 하는 이름 넘겨줌
            }
            else if(command.equals("save")){
                String fileName;
                fileName = cmd.nextToken();
                saveTree(t,fileName);
            }
            else if (command.equals("exit")) {
                break;
            }
        }
    }
    public static void printNode(Node x){
        System.out.println(x.name);
        System.out.println("    Company: " + x.company_name);
        System.out.println("    Address: " + x.address);
        System.out.println("    Zipcode: " + x.zip);
        System.out.println("    Phones: " + x.phone);
        System.out.println("    Email: " + x.email);
    }
    public static void listInorder(Node x){
        if(x != null){
            listInorder(x.left);
            printNode(x);
            listInorder(x.right);
        }
    }
    public static void find(BinaryTree t, String name) {
        Node x = findName(t.root, name);  //찾고자 하는 단어 검색
        if(x == null)
            System.out.println(name + " is No exist");
        else {
            printNode(x);
        }
    }
    public static Node findName(Node x, String k) {
        if (x == null || k.equals(x.name))
            return x;
        if (compareTo(x.name, k)) {     //찾을려는 k 왼쪽에있는경우
            return findName(x.left, k);
        }
        else {
            return findName(x.right, k);
        }
    }
    public static void add(BinaryTree t,String name,String c_name,String adrs,
                           String zip,String phone,String email) {
        Node newNode = new Node(name,c_name,adrs,zip,phone, email);
        treeInsert(t, newNode); t.size++;
    }
    public static void delete(BinaryTree t, String name) {
        Node x = treeSearch(t.root, name);
        treeDelete(t, x); t.size--;
        if (x == null) System.out.println("Not found");  //if x is null no exist name
        else System.out.println("Delete successfully");
    }
    public static void trace(BinaryTree t,String name){
        Node x = t.root;
        treeSearch(x,name);
        System.out.println(name);
    }
    public static void saveTree(BinaryTree t,String fileName){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter
                ("C:\\Users\\hagji\\Desktop\\"+fileName))) {
                 int i = 0;
                 save(t.root);
                 while(true) {
                     StringTokenizer st = new StringTokenizer(cp[i],",");
                     String st1=st.nextToken(); String st2=st.nextToken();
                     String st3=st.nextToken(); String st4=st.nextToken();
                     String st5=st.nextToken(); String st6=st.nextToken();
                     bw.write(st1+","+st2+","+st3+","+st4+","+st5+","+st6+",");
                     bw.newLine();
                     i++;
                     if(i==n-1)
                         break;
                 }
             } catch (IOException e) {
                 e.printStackTrace();
             }
    }
    public static void save(Node x){
            if(x != null){
                save(x.left);
                copy(x);
                save(x.right);
            }
    }
    public static void copy(Node x){
        if(x==null) {
            return;
        }
        else {
            cp[n] = x.name + "," + x.company_name + "," + x.address + "," +
                    x.zip + "," + x.phone + "," + x.email;
            n++;
            return;
        }
    }
    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        readFile(tree);
        readCommand(tree);
    }
}
