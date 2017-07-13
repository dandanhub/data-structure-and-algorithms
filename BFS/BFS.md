## 317. Shortest Distance from All Buildings
You want to build a house on an empty land which reaches all buildings in the shortest amount of distance. You can only move up, down, left and right. You are given a 2D grid of values 0, 1 or 2, where:

Each 0 marks an empty land which you can pass by freely.
Each 1 marks a building which you cannot pass through.
Each 2 marks an obstacle which you cannot pass through.
For example, given three buildings at (0,0), (0,4), (2,2), and an obstacle at (0,2):

1 - 0 - 2 - 0 - 1
|   |   |   |   |
0 - 0 - 0 - 0 - 0
|   |   |   |   |
0 - 0 - 1 - 0 - 0
The point (1,2) is an ideal empty land to build a house, as the total travel distance of 3+3+1=7 is minimal. So return 7.

Note:
There will be at least one building. If it is not possible to build such house according to the above rules, return -1.

#### Solution
Method 1: BFS 395ms 效果不够好 <br>
从所有空地开始BFS，计算空地到所有buildings的距离
~~~
public class Solution {
    class Res {
        int dist;
        int count;

        public Res(int dist, int count) {
            this.dist = dist;
            this.count = count;
        }
    }

    class Point {
        int x;
        int y;
        int dist;

        public Point(int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }
    }

    public int shortestDistance(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) return -1;

        int m = grid.length, n = grid[0].length;
        int num = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) num++;
            }
        }

        int ans = -1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    Res res = bfs(grid, i, j);
                    if (res.count == num && (ans == -1 || res.dist < ans)) {
                        ans = res.dist;
                    }
                }
            }
        }

        return ans;
    }

    // private bfs helper return the distance to reach all building
    private Res bfs(int[][] grid, int row, int col) {     
        int m = grid.length, n = grid[0].length;

        int totalDist = 0;
        int totalCount = 0;

        boolean[][] visited = new boolean[m][n];
        Queue<Point> queue = new LinkedList<Point>();
        Point start = new Point(row, col, 0);
        queue.offer(start);
        visited[row][col] = true;

        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        while (!queue.isEmpty()) {
            Point point = queue.poll();
            for (int[] dir : dirs) {
                int i = point.x + dir[0];
                int j = point.y + dir[1];
                int dist = point.dist + 1;
                if (i >= 0 && i < m && j >= 0 && j < n && !visited[i][j]) {
                    visited[i][j] = true;
                    if (grid[i][j] == 1) {
                        totalCount++;
                        totalDist += dist;
                    }
                    else if (grid[i][j] == 0) {
                        Point next = new Point(i, j, dist);
                        queue.offer(next);
                    }
                }   
            }
        }

        Res res = new Res(totalDist, totalCount);
        return res;
    }
}
~~~

Method 2: 14ms <br>
从所有的building开始遍历，用distance[][]数组记录从每个building到空白地方的距离 <br>
需要注意的点是要确保空地能达到所有的building，所以引入count[][]数组记录空地能到达的building的个数，并且可以用count[][]数组作为每轮bfs的visited数组来判断每个点是否被访问过
~~~
public class Solution {
    class Point {
        int x;
        int y;
        int dist;

        public Point(int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }
    }

    public int shortestDistance(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) return -1;

        int m = grid.length, n = grid[0].length;
        int[][] distance = new int[m][n];
        int[][] count = new int[m][n];
        int num = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    bfs(grid, count, distance, i, j, num);
                    num++;
                }
            }
        }

        int ans = -1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (count[i][j] == num && (ans == -1 || ans > distance[i][j])) ans = distance[i][j];
            }
        }

        return ans;
    }

    private void bfs(int[][] grid, int[][] count, int[][] distance, int row, int col, int num) {     
        int m = grid.length, n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        Queue<Point> queue = new LinkedList<Point>();
        Point start = new Point(row, col, 0);
        queue.offer(start);
        visited[row][col] = true;

        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        while (!queue.isEmpty()) {
            Point point = queue.poll();
            for (int[] dir : dirs) {
                int i = point.x + dir[0];
                int j = point.y + dir[1];
                int dist = point.dist + 1;
                if (i >= 0 && i < m && j >= 0 && j < n && count[i][j] == num && grid[i][j] == 0) {
                    count[i][j]++;
                    distance[i][j] += dist;
                    Point next = new Point(i, j, dist);
                    queue.offer(next);
                }
            }
        }
    }
}
~~~
