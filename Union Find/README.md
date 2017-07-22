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
