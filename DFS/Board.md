## 417. Pacific Atlantic Water Flow
Given an m x n matrix of non-negative integers representing the height of each unit cell in a continent, the "Pacific ocean" touches the left and top edges of the matrix and the "Atlantic ocean" touches the right and bottom edges.

Water can only flow in four directions (up, down, left, or right) from a cell to another one with height equal or lower.

Find the list of grid coordinates where water can flow to both the Pacific and Atlantic ocean.

Note:
The order of returned grid coordinates does not matter.
Both m and n are less than 150.
Example:

Given the following 5x5 matrix:
~~~~
 Pacific ~   ~   ~   ~   ~
      ~  1   2   2   3  (5) *
      ~  3   2   3  (4) (4) *
      ~  2   4  (5)  3   1  *
      ~ (6) (7)  1   4   5  *
      ~ (5)  1   1   2   4  *
         *   *   *   *   * Atlantic

Return:

[[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
~~~~

#### Solution
Use two boolean array to mark whether pacific and atlantic water can flow into a point matrix[i][j].
Use DFS to mark matrix just like water flow.
这题新颖的地方是
1. 保存两个visited数字
2. 每次dfs开始的点

~~~
public class Solution {
    public List<int[]> pacificAtlantic(int[][] matrix) {
        List<int[]> ans = new ArrayList<int[]>();
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return ans;
        int m = matrix.length, n = matrix[0].length;
        boolean[][] pacific = new boolean[m][n];
        boolean[][] atlantic = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            dfs(matrix, pacific, i, 0); // pacific starts from left
            dfs(matrix, atlantic, i, n - 1); // atlantic starts from right
        }

        for (int j = 0; j < n; j++) {
            dfs(matrix, pacific, 0, j); // pacific starts from up
            dfs(matrix, atlantic, m - 1, j); // atlantic starts from bottom
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (pacific[i][j] && atlantic[i][j]) {
                    int[] point = {i, j};
                    ans.add(point);
                }
            }
        }
        return ans;
    }

    private void dfs(int[][] matrix, boolean[][] visited, int i, int j) {
        int m = matrix.length, n = matrix[0].length;
        visited[i][j] = true;
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] dir : dirs) {
            int x = i + dir[0];
            int y = j + dir[1];
            if (x >= 0 && y >= 0 && x < m && y < n && matrix[x][y] >= matrix[i][j] && !visited[x][y]) {
                dfs(matrix, visited, x, y);
            }
        }
    }
}
~~~
