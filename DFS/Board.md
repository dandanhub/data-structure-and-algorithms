## 200. Number of Islands
Given a 2d grid map of '1's (land) and '0's (water), count the number of islands. An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.

Example 1:
~~~
11110
11010
11000
00000
~~~
Answer: 1

Example 2:
~~~
11000
11000
00100
00011
~~~
Answer: 3

#### Solution
2D board DFS经典题，有点类似于求connected component的个数

~~~
public class Solution {
    public int numIslands(char[][] grid) {
        // edge cases
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;

        boolean[][] visited = new boolean[grid.length][grid[0].length];
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1' && !visited[i][j]) {
                    count++;
                    dfs(grid, visited, i, j);
                }
            }
        }
        return count;
    }

    private void dfs(char[][] grid, boolean[][] visited, int row, int col) {
        visited[row][col] = true;
        int[][] dirs = {{0, -1}, {0, 1}, {1, 0}, {-1, 0}};
        for (int[] dir : dirs) {
            int x = row + dir[0];
            int y = col + dir[1];
            if (x >= 0 && x < grid.length && y >= 0 && y < grid[0].length
                    && !visited[x][y] && grid[x][y] == '1') {
                dfs(grid, visited, x, y);
            }
        }
    }
}
~~~
