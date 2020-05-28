package pa4;
import java.io.*;
import java.util.*;

class Node implements Comparable<Node>{
    String name; String company_name; String address;
    String zip; String phone;String email;

    public Node(String name, String company_name, String address,
                String zip, String phone, String email) {
        this.name = name; this.company_name = company_name;
        this.address = address; this.zip = zip;
        this.phone = phone; this.email = email;
    }

    @Override
    public int compareTo(Node n){ // n의 이름이 호출한 이름보다 앞에있으면
        if(n.name.compareTo(this.name) > 0)
            return -1;
        else
            return 1;
    }
    @Override
    public String toString(){
        return (name +'\n'+  "\tCompany: " + company_name +
                +'\n'+"\tAddress: " + address + +'\n'+"\tZipcode: " + zip
                +'\n'+ "\tPhones: " + phone + '\n'+"\tEmail: " + email);
    }
}

class BinaryTree {
    static TreeSet<Node> tree;

    public BinaryTree() {
        tree = new TreeSet<>();
    }
    public void readFile() {
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
                splitDataAdd(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void splitDataAdd(String data) {
        StringTokenizer st1 = new StringTokenizer(data,",");
        int index = data.indexOf(",");
        String na = st1.nextToken();
        String str = data.substring(index+1,data.length()-1); //이름 뒤부터 문자열 생성
        String str2 = str.replace(", "," ");

        StringTokenizer str3 = new StringTokenizer(str2,",\"");
        String c_name = str3.nextToken(); String adrs = str3.nextToken();
        String zip = str3.nextToken(); String ph = str3.nextToken(); String em = str3.nextToken();
        add(na,c_name,adrs,zip,ph,em);
    }
    public void readCommand() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("$ ");
            String command = sc.nextLine();
            StringTokenizer cmd = new StringTokenizer(command, " ");
            command = cmd.nextToken();
            if (command.equals("list")) {
                listInorder();
            } else if (command.equals("find")) {
                String name;
                name = cmd.nextToken();
                find(name); //찾고자 하는 단어 넘겨줌
            }
            else if(command.equals("trace")){
                String name;
                name = cmd.nextToken();
                trace(name);
            }
            else if (command.equals("delete")) {
                String name;
                name = cmd.nextToken();
                delete(name);   //삭제하고자 하는 이름 넘겨줌
            }
            else if(command.equals("save")){
                String fileName;
                fileName = cmd.nextToken();
                saveTree(fileName);
            }
            else if (command.equals("exit")) {
                break;
            }
        }
    }
    public static void listInorder(){
        for (Node x : tree) {
            System.out.println(x.toString());
        }
    }
    public static void find(String name) {
        Node x = findName(name);  //찾고자 하는 단어 검색
        if(x == null)
            System.out.println(name + " is No exist");
        else {
            System.out.println(x.toString());
        }
    }
    public static Node findName(String k) {
        return treeSearch(k);
    }
    public static void add(String name,String c_name,String adrs,
                           String zip,String phone,String email) {
        Node newNode = new Node(name,c_name,adrs,zip,phone, email);
        tree.add(newNode);
    }
    public static void delete(String name) {
        treeDelete(name);
    }
    public static Node treeSearch(String k) {
        for (Node x : tree) {
            if (x.name.equals(k))
                return x;
        }
        return null;
    }
    public static void treeDelete(String k) {
        tree.removeIf(x -> x.name.equals(k));
    }
    public static void trace(String name){
        traceSearch(name);
    }
    public static void traceSearch(String name){
        for (Node x : tree) {
            if (x.name.equals(name))
                break;
            else
                System.out.println(x.name);
        }
    }
    public static void saveTree(String fileName){
        String filePath = BinaryTree.class.getResource("").getPath();
        File file = new File(filePath+fileName);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter
                (file))) {
            for (Node copy : tree) {
                bw.write(copy.name + "," + copy.company_name + "," + copy.address+ ","
                        + copy.zip + "," + copy.phone + "," + copy.email + ",");
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class Main{
    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        tree.readFile();
        tree.readCommand();
    }
}