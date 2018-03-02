 import java.io.*;
import java.util.*;

public class Solution implements Runnable {
    public static void main(String[] args) {
        new Thread(null, new Solution(), "", 128 * 1024 * 1024).start();
    }

    public void run() {
        try {
            StreamTokenizer st = new StreamTokenizer(new BufferedReader(new FileReader("in.txt")));
            Tree tree = new Tree();
            while (st.nextToken() == StreamTokenizer.TT_NUMBER) {
                tree.insertion((int) st.nval);
            }
            List<Integer> max = new ArrayList<>();
            max.add(0);
            max = tree.backwash(tree.getRoot(), max);
            int keyVertexWithMaxSum = tree.findVertexWithBigestHalfWay(max);
            tree.deleteCentralAndRoot(keyVertexWithMaxSum);
            StringBuilder sb = new StringBuilder();
            sb = tree.circumvention(tree.getRoot(), new StringBuilder());
            FileWriter fw = new FileWriter("out.txt");
            fw.write(sb.toString());
            fw.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
class Tree {
    private Node root;

    public void insertion(int key) {
        if (root == null) {
            root = new Node(key);
            return;
        }
        Node node = root;
        while (true) {
            if (key < node.key) {
                if (node.left == null) {
                    node.left = new Node(key);
                    return;
                } else {
                    node = node.left;
                }
            } else if (key > node.key) {
                if (node.right == null) {
                    node.right = new Node(key);
                    return;
                } else {
                    node = node.right;
                }
            } else {
                return;
            }
        }
    }

    public void replaceChild(Node parent, Node son, Node newSon) {
        if (parent == null) {
            root = newSon;
        } else if (parent.left == son) {
            parent.left = newSon;
        } else if (parent.right == son) {
            parent.right = newSon;
        }

    }

    public void delete(int keyDelete) {
        Node parent = null;
        Node vertex = root;
        while (true) {
            if (vertex == null)
                return;
            if (keyDelete < vertex.key) {
                parent = vertex;
                vertex = vertex.left;
            } else if (keyDelete > vertex.key) {
                parent = vertex;
                vertex = vertex.right;
            } else {
                break;
            }
        }
        Node result = null;
        if (vertex.left == null) {
            result = vertex.right;
        } else if (vertex.right == null) {
            result = vertex.left;
        } else {
            Node minNode = vertex.right;
            Node minParentNode = vertex;
            while (minNode.left != null) {
                minParentNode = minNode;
                minNode = minNode.left;
            }
            result = vertex;
            vertex.key = minNode.key;
            replaceChild(minParentNode, minNode, minNode.right);
            return;
        }
        replaceChild(parent, vertex, result);
    }

    public StringBuilder circumvention(Node node, StringBuilder sb) throws IOException {
        if (node != null) {
            if (sb.length() == 0) {
                sb.append(node.key);
            } else {
                sb.append("\n").append(node.key);
            }
            circumvention(node.left, sb);
            circumvention(node.right, sb);
        }
        return sb;
    }

    public List<Integer> backwash(Node node, List<Integer>max) {
        if (node != null) {
            backwash(node.left, max);
            backwash(node.right, max);
            if (node.left == null && node.right == null) {
                node.height = 0;
                node.maxSLength = 0;
            } else if (node.left != null && node.right == null) {
                node.height = node.left.height + 1;
                node.maxSLength = node.left.height + 1;
                if (max.get(0) == node.maxSLength
                        && max.get(1) < node.key) {
                    max.add(node.key);
                }
                if (max.get(0) < node.maxSLength) {
                    max.clear();
                    max.add(node.maxSLength);
                    max.add(node.key);
                }
            } else if (node.left == null && node.right != null) {
                node.height = node.right.height + 1;
                node.maxSLength = node.right.height + 1;
                if (max.get(0) == node.maxSLength) {
                    max.add(node.key);
                }
                if (max.get(0) < node.maxSLength) {
                    max.clear();
                    max.add(node.maxSLength);
                    max.add(node.key);
                }
            } else {
                int maxHeightSon = 0;
                if (node.left.height > node.right.height) {
                    maxHeightSon = node.left.height;
                    node.height = maxHeightSon + 1;
                } else {
                    maxHeightSon = node.right.height;
                    node.height = maxHeightSon + 1;
                }
                node.maxSLength = node.left.height + node.right.height + 2;
                if (max.get(0) == node.maxSLength) {
                    max.add(node.key);
                }
                if (max.get(0) < node.maxSLength) {
                    max.clear();
                    max.add(node.maxSLength);
                    max.add(node.key);
                }
            }
        }
        return max;
    }
    public long sumNode(int key) {
        long sum = 0;
        Node node = root;
        while (true) {
            if (node == null)
                break;
            if (key < node.key) {
                node = node.left;
            } else if (key > node.key) {
                node = node.right;
            } else {
                break;
            }
        }
        key = node.key;
        //List<Integer>left = new ArrayList<>();
        //List<Integer>right = new ArrayList<>();
        List<Integer> common = new ArrayList<>();
        common.add(node.key);
        if (node.left != null) {
            common.addAll(keyMaxWay(node.left));
        }
        if (node.right != null) {
            common.addAll(keyMaxWay(node.right));
        }
        for (Integer item : common) {
            sum += item;
        }
        return sum;

    }
    public int findVertexWithBigestHalfWay(List<Integer>list){
        boolean isFirst = false;
        int max = 0;
        long maxSum = 0;
        long sum = 0;
        for(Integer item:list) {
            if(!isFirst){
                isFirst = true;
                max = list.get(1);
                maxSum = sumNode(list.get(1));
            }
            else if(isFirst && item!=list.get(1)){
                sum = sumNode(item);
                if(maxSum < sum){
                    maxSum = sum;
                    max = item;
                }
            }
        }
        return max;
    }
    public void deleteCentralAndRoot(int key) {
        Node node = root;
        while (true) {
            if (key < node.key) {
                node = node.left;
            } else if (key > node.key) {
                node = node.right;
            } else if (key == node.key) {
                break;
            }
        }
        if (node.maxSLength % 2 != 0) {
            delete(key);
        }
        else {
            List<Integer>leftPart = new ArrayList<>();
            List<Integer>rightPart = new ArrayList<>();
            List<Integer>MLS = new ArrayList<>();
            int rootKey = node.key;

            if(node.left!=null && node.right!=null) {
                leftPart = keyMaxWay(node.left);
                rightPart = keyMaxWay(node.right);
                if(leftPart.size() < rightPart.size()){
                    Collections.reverse(leftPart);
                    MLS = new ArrayList<Integer>(leftPart);
                    MLS.add(rootKey);
                    MLS.addAll(rightPart);
                }
                else{
                    Collections.reverse(rightPart);
                    MLS = new ArrayList<>(rightPart);
                    MLS.add(rootKey);
                    MLS.addAll(leftPart);
                }
            }
            else if(node.left!=null){
                MLS.add(rootKey);
                MLS.addAll(keyMaxWay(node.left));
            }
            else if(node.right!=null){
                MLS.add(rootKey);
                MLS.addAll(keyMaxWay(node.right));
            }
            int count = 0;
            for(Integer item:MLS){
                if(count == MLS.size()/2){
                    delete(item);
                    if(item!=rootKey){
                        delete(rootKey);
                    }
                }
                count++;
            }
        }

    }
    private List<Integer> keyMaxWay(Node node){
        List<Integer>v = new ArrayList<>();
        v.add(node.key);
        while(true){
            if(node.left!=null && node.right!=null) {
                if(node.left.height > node.right.height){
                    node = node.left;
                    v.add(node.key);
                }
                else if(node.left.height <= node.right.height){// (node.left.height == node.right.height) {
                    node = node.right;
                    v.add(node.key);
                }
            }
            else if(node.left!=null){
                node = node.left;
                v.add(node.key);
            }
            else if(node.right!=null){
                node = node.right;
                v.add(node.key);
            }
            else{
                break;
            }
        }
        return v;
    }
    public Node getRoot() {
        return root;
    }
}
class Node {
    public int key;
    public int height;
    public int maxSLength;
    public Node left;
    public Node right;

    public Node(int key) {
        this.key = key;
    }
}
