## 286. Walls and Gates
You are given a m x n 2D grid initialized with these three possible values.

1. -1 - A wall or an obstacle.
2. 0 - A gate.
3. INF - Infinity means an empty room. We use the value 231 - 1 = 2147483647 to represent INF as you may assume that the distance to a gate is less than 2147483647.

Fill each empty room with the distance to its nearest gate. If it is impossible to reach a gate, it should be filled with INF.

For example, given the 2D grid:
~~~
INF  -1  0  INF
INF INF INF  -1
INF  -1 INF  -1
  0  -1 INF INF
~~~

After running your function, the 2D grid should be:
~~~
  3  -1   0   1
  2   2   1  -1
  1  -1   2  -1
  0  -1   3   4
~~~

#### Solution
Method 1: BFS
~~~
class Solution {
    public void wallsAndGates(int[][] rooms) {
        if (rooms == null || rooms.length == 0 || rooms[0].length == 0) return;

        int m = rooms.length;
        int n = rooms[0].length;

        Queue<int[]> queue = new LinkedList<int[]>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (rooms[i][j] == 0) {
                    queue.offer(new int[]{i, j});
                }
            }
        }

        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        while (!queue.isEmpty()) {
            int[] node = queue.poll();
            for (int[] dir : dirs) {
                int x = node[0] + dir[0];
                int y = node[1] + dir[1];
                if (x >= 0 && x < m && y >= 0 && y < n
                    && rooms[x][y] == Integer.MAX_VALUE) {
                    rooms[x][y] = rooms[node[0]][node[1]] + 1;
                    queue.offer(new int[]{x, y});
                }
            }
        }
    }
}
~~~

Method 2: DFS
~~~
class Solution {
    public void wallsAndGates(int[][] rooms) {
        if (rooms == null || rooms.length == 0 || rooms[0].length == 0) return;

        int m = rooms.length;
        int n = rooms[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (rooms[i][j] == 0) {
                    dfs(rooms, i, j);
                }
            }
        }
    }

    private void dfs(int[][] rooms, int i, int j) {
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : dirs) {
            int x = i + dir[0];
            int y = j + dir[1];
            if (x >= 0 && x < rooms.length && y >= 0 && y < rooms[0].length
                && rooms[x][y] > rooms[i][j] + 1) {
                rooms[x][y] = rooms[i][j] + 1;
                dfs(rooms, x, y);
            }
        }
    }
}
~~~

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
