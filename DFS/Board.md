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

~~~
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
~~~
