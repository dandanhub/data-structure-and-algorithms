public class Solution {
    // 323. Number of Connected Components in an Undirected Graph
    public int countComponents(int n, int[][] edges) {
        // TODO: exception handling
        if (n < 0) {
            return 0;
        }
        if (edges == null || edges.length == 0 || edges[0].length == 0) {
            return n;
        }

        boolean[] visited = new boolean[n];
        int count = 0;
        for (int i = 0; i < n; i++) {
            if(!visited[i]) {
                count++;
                dfs(i, edges, visited);
            }
        }
        return count;
    }

    private void dfs(int src, int[][] edges, boolean[] visited) {
        if (visited[src]) return;

        visited[src] = true;
        for (int i = 0; i < edges.length; i++) {
            if (edges[i][0] == src) {
                int dst = edges[i][1];
                dfs(dst, edges, visited);
            }
            if (edges[i][1] == src) {
                int dst = edges[i][0];
                dfs(dst, edges, visited);
            }
        }
    }

    
}
