import java.io.*;

public class Solution {
    public static void main(String[] args) throws IOException {
        StreamTokenizer st = new StreamTokenizer(new BufferedReader(new FileReader("input.txt")));
        Tree tree = new Tree();
        while (st.nextToken() == StreamTokenizer.TT_NUMBER) {
            tree.insertion((int)st.nval);
        }
        StringBuilder sb = new StringBuilder();
        FileWriter fw = new FileWriter("output.txt");
        sb = tree.circumvention(tree.getRoot(),new StringBuilder());
        fw.write(sb.toString());
        fw.close();
    }
}
class Tree{
    private Node root;
    public void insertion(int key) {
        if (root == null) {
            root = new Node(key);
            return;
        }
        Node node = root;
        while(true) {
            if (key < node.key) {
                if (node.left == null) {
                    node.left = new Node(key);
                    return;
                } else {
                    node = node.left;
                }
            } else if(key > node.key)  {
                if (node.right == null) {
                    node.right = new Node(key);
                    return;
                } else {
                    node = node.right;
                }
            }
            else{
                return;
            }
        }
    }
    public StringBuilder circumvention(Node node, StringBuilder sb) throws IOException {
        if (node != null) {
            if(sb.length() == 0) {
                sb.append(node.key);
            }
            else{
                sb.append("\n").append(node.key);
            }
            circumvention(node.left,sb);
            circumvention(node.right,sb);
        }
        return sb;
    }

    public Node getRoot() {
        return root;
    }
}
class Node{
    public int key;
    public Node left;
    public Node right;
    public Node(int key){
        this.key = key;
    }
}