package Google;

import java.util.ArrayList;
import java.util.List;

public class PruferTree {
    public static void main(String[] args) {
        int[] prufer = {4, 1, 3, 4};
        List<int[]> edges = printTreeEdge(prufer);
        for (int[] edge : edges) {
            System.out.println(edge[0] + "->" + edge[1]);
        }
    }

    public static List<int[]> printTreeEdge(int[] prufer) {
        List<int[]> list = new ArrayList<int[]>();
        int n = prufer.length + 2;
        int[] treeNodes = new int[n];

        // initialization
        for (int i = 0; i < prufer.length; i++) {
            int node = prufer[i] - 1;
            treeNodes[node]++;
        }

        for (int i = 0; i < prufer.length; i++) {
            int parent = prufer[i] - 1;
            for (int j = 0; j < n; j++) {
                if (treeNodes[j] == 0) {
                    treeNodes[j] = -1; // mark node j as removed
                    int[] edge = {parent + 1, j + 1};
                    list.add(edge);
                    treeNodes[parent]--;
                    break;
                }
            }
        }

        // last edge
        int s = -1, e = -1;
        for (int i = 0; i < n; i++) {
            if (treeNodes[i] == 0) {
                if (s == -1) s = i + 1;
                else e = i + 1;
            }
        }
        int[] edge = {s, e};
        list.add(edge);

        return list;
    }
}
