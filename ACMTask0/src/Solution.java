import java.io.*;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class Solution {
    public static void main(String[] args) throws Exception {
        StreamTokenizer st = new StreamTokenizer(new BufferedReader(new FileReader("input.txt")));
        Set<Integer> set = new TreeSet<>();
        while (st.nextToken() == StreamTokenizer.TT_NUMBER) {
            set.add((int) st.nval);
        }
        Iterator<Integer> it = set.iterator();
        int top = 0;
        int iter = 0;
        while (it.hasNext()) {
            if(iter == 0){
                top = it.next();
                iter++;
            }
            else{

                iter++;
            }
        }
        PrintWriter pw = new PrintWriter("output.txt");
        String sumString = sum.toString();
        pw.write(sumString);
        pw.close();*/
    }
}
