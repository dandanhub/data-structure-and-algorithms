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

    // 417. Pacific Atlantic Water Flow
    public List<int[]> pacificAtlantic(int[][] matrix) {
        List<int[]> ans = new ArrayList<int[]>();
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return ans;

        int r = matrix.length;
        int c = matrix[0].length;
        boolean[][] pacific = new boolean[r][c];
        boolean[][] atlantic = new boolean[r][c];

        for (int i = 0; i < r; i++) {
            dfs(matrix, pacific, i, 0, Integer.MIN_VALUE);
            dfs(matrix, atlantic, i, c - 1, Integer.MIN_VALUE);
        }

        for (int j = 0; j < c; j++) {
            dfs(matrix, pacific, 0, j, Integer.MIN_VALUE);
            dfs(matrix, atlantic, r - 1, j, Integer.MIN_VALUE);
        }

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (pacific[i][j] && atlantic[i][j]) {
                    int[] point = {i, j};
                    ans.add(point);
                }
            }
        }
        return ans;
    }

    int[][] directs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    private void dfs(int[][] matrix, boolean[][] visited, int i, int j, int height) {
        if (i < 0 || i >= matrix.length || j < 0 || j >= matrix[0].length || visited[i][j] || matrix[i][j] < height) return;
        visited[i][j] = true;
        for (int[] d : directs) {
            dfs(matrix, visited, i + d[0], j + d[1], matrix[i][j]);
        }
    }
}
