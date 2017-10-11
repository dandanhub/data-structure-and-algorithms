## 323. Number of Connected Components in an Undirected Graph
Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes), write a function to find the number of connected components in an undirected graph.

Example 1:
~~~~
     0          3
     |          |
     1 --- 2    4
~~~~
Given n = 5 and edges = [[0, 1], [1, 2], [3, 4]], return 2.

Example 2:
~~~~
     0           4
     |           |
     1 --- 2 --- 3
~~~~
Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [3, 4]], return 1.

Note:
You can assume that no duplicate edges will appear in edges. Since all edges are undirected, [0, 1] is the same as [1, 0] and thus will not appear together in edges

#### Solution
DFS
~~~
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
~~~

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

## 547. Friend Circles
There are N students in a class. Some of them are friends, while some are not. Their friendship is transitive in nature. For example, if A is a direct friend of B, and B is a direct friend of C, then A is an indirect friend of C. And we defined a friend circle is a group of students who are direct or indirect friends.

Given a N*N matrix M representing the friend relationship between students in the class. If M[i][j] = 1, then the ith and jth students are direct friends with each other, otherwise not. And you have to output the total number of friend circles among all the students.

Example 1:
~~~
Input:
[[1,1,0],
 [1,1,0],
 [0,0,1]]
Output: 2
Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
The 2nd student himself is in a friend circle. So return 2.
~~~

Example 2:
~~~
Input:
[[1,1,0],
 [1,1,1],
 [0,1,1]]
Output: 1
Explanation:The 0th and 1st students are direct friends, the 1st and 2nd students are direct friends,
so the 0th and 2nd students are indirect friends. All of them are in the same friend circle, so return 1.
~~~

Note:
N is in range [1,200].
M[i][i] = 1 for all students.
If M[i][j] = 1, then M[j][i] = 1.

#### Solution

DFS
~~~
public class Solution {
    public int findCircleNum(int[][] M) {
        if (M == null || M.length == 0 || M[0].length == 0) return 0;

        boolean[] visited = new boolean[M.length];
        int count = 0;
        for (int i = 0; i < M.length; i++) {
            if (!visited[i]) {
                dfs(M, visited, i);
                count++;
            }
        }
        return count;
    }

    private void dfs(int[][] M, boolean[] visited, int i) {
        visited[i] = true;
        for (int j = 0; j < M.length; j++) {
            if (M[i][j] == 1 && !visited[j]) {
                dfs(M, visited, j);
            }
        }
    }
}
~~~
