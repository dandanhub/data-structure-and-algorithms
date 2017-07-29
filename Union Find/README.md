# Union Find
两个操作find和union函数，比较简单。但是worst case操作find和union需要O(n)，优化需要union by rank和path compression.
1. union by rank 就是额外维护一个 rank数组，在union的时候把rank小的树合并到rank大的树上，rank可以是树height也可以是树的size
2. path compression在union的时候进行，其实就是在find的时候多加一行代码parent[p] = parent[parent[p]];

## 200. Number of Islands
Given a 2d grid map of '1's (land) and '0's (water), count the number of islands. An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.

Example 1:
~~~
11110
11010
11000
00000
Answer: 1
~~~

Example 2:
~~~
11000
11000
00100
00011
Answer: 3
~~~

#### Solution

~~~
public class Solution {
    int[] roots;
    int[] ranks;
    int count;

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;

        int M = grid.length;
        int N = grid[0].length;
        roots = new int[M * N];
        for (int i = 0; i < M * N; i++) {
            roots[i] = i;
            if (grid[i / N][i % N] == '1') count++;
        }
        ranks = new int[M * N];

        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (grid[i][j] == '1') {
                    for (int[] dir : dirs) {
                        int x = i + dir[0];
                        int y = j + dir[1];
                        if (x >= 0 && x < M && y >= 0 && y < N && grid[x][y] == '1') {
                            int p1 = i * N + j;
                            int p2 = x * N + y;
                            union(p1, p2);
                        }
                    }
                }
            }
        }
        return count;
    }

    private int find(int i) {
        while (i != roots[i]) {
            roots[i] = roots[roots[i]]; // path compression
            i = roots[i];
        }
        return i;
    }

    private void union(int i, int j) {
        int ri = find(i);
        int rj = find(j);
        if (ri == rj) return;
        if (ranks[ri] > ranks[rj]) {
            roots[rj] = ri;
        }
        else {
            roots[ri] = rj;
            if (ranks[ri] == ranks[rj]) {
                ranks[rj]++;
            }
        }
        count--;
    }
}
~~~

## 305. Number of Islands II
A 2d grid map of m rows and n columns is initially filled with water. We may perform an addLand operation which turns the water at position (row, col) into a land. Given a list of positions to operate, count the number of islands after each addLand operation. An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.

Example:

Given m = 3, n = 3, positions = [[0,0], [0,1], [1,2], [2,1]].
Initially, the 2d grid grid is filled with water. (Assume 0 represents water and 1 represents land).

#### Solution
Refer to the [leetcode discussion](https://discuss.leetcode.com/topic/29613/easiest-java-solution-with-explanations/2)
.
- Union: O(1)
- Find: 取决于树有多高，最坏情况下一次find可以有O(n), n为operation的个数，avg情况是O(nlogn)
- Path compression加快union find的速度，在find的过程中，压缩树长度

~~~
public class Solution {
    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        List<Integer> list = new ArrayList<Integer>();
        if (positions == null || positions.length == 0 || positions[0].length == 0) return list;

        int[] roots = new int[m * n];
        Arrays.fill(roots, -1);

        int count = 0;
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (int[] position : positions) {
            int root = position[0] * n + position[1];
            roots[root] = root;
            count++;

            // check four neighbors to do union
            for (int[] dir : dirs) {
                int x = position[0] + dir[0];
                int y = position[1] + dir[1];
                int index = x * n + y;
                if (x >= 0 && x < m && y >= 0 && y < n && roots[index] != -1) {
                    int neighRoot = findRoot(roots, index);
                    if (neighRoot != root) {
                        roots[neighRoot] = root;
                        count--;
                    }
                }
            }

            list.add(count);
        }

        return list;
    }

    private int findRoot(int[] roots, int index) {
        while (roots[index] != index) {
            roots[index] = roots[roots[index]];
            index = roots[index];
        }
        return index;
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
Union Find with rank and path compression.
~~~
public class Solution {
    int[] roots;
    int[] ranks;
    int count;

    public int findCircleNum(int[][] M) {
        if (M == null || M.length == 0 || M[0].length == 0) return 0;

        int N = M.length;
        roots = new int[N];
        for (int i = 0; i < N; i++) roots[i] = i;
        ranks = new int[N];
        count = N;

        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (M[i][j] == 1) {
                    union(i, j);
                }
            }
        }
        return count;
    }

    private int find(int i) {
        while (i != roots[i]) {
            roots[i] = roots[roots[i]]; // path compression
            i = roots[i];
        }
        return i;
    }

    private void union(int i, int j) {
        int ri = find(i);
        int rj = find(j);
        if (ri == rj) {
            return;
        }

        if (ranks[ri] > ranks[rj]) {
            roots[rj] = roots[ri];
        }
        else {
            roots[ri] = roots[rj];
            if (ranks[ri] == ranks[rj]) {
                ranks[rj]++;
            }
        }
        count--;
    }
}
~~~
