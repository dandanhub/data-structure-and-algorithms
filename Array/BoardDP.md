# DP
## 64. Minimum Path Sum (Medium)
Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right which minimizes the sum of all numbers along its path.

Note: You can only move either down or right at any point in time.

#### Solution
1. Naiive 2D DP
2. 1D DP
3. DP without extra space when we don't need to retain input matrix

Version 1 <br>
Attempts: 2 (typo bug)
~~~
public class Solution {
    public int minPathSum(int[][] grid) {
        // edge cases
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;

        int m = grid.length, n = grid[0].length;
        int[][] dp = new int[m][n];

        // init
        dp[0][0] = grid[0][0];
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }

        for (int j = 1; j < n; j++) {
            // dp[0][j] = dp[0][j - 1] + dp[0][j]; // bug typo
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }

        return dp[m - 1][n - 1];
    }
}
~~~

Version 2 <br>
Attempts: 2 (bug when setting initial value of prev for each row)
~~~
public class Solution {
    public int minPathSum(int[][] grid) {
        // edge cases
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;

        int m = grid.length, n = grid[0].length;
        int[] dp = new int[n];

        // init
        dp[0] = grid[0][0];
        for (int j = 1; j < n; j++) {
            dp[j] = dp[j - 1] + grid[0][j];
        }

        for (int i = 1; i < m; i++) {
            // int prev = grid[i][0]; // bug
            int prev = grid[i][0] + dp[0];
            dp[0] = prev;
            for (int j = 1; j < n; j++) {
                int curr = Math.min(dp[j], prev) + grid[i][j];
                // update dp array and prev
                dp[j] = curr;
                prev = curr;
            }
        }

        return dp[n - 1];
    }
}
~~~

Version 3 <br>
Attempts: 2 (minor typo bug)
~~~
public class Solution {
    public int minPathSum(int[][] grid) {
        // edge cases
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;

        int m = grid.length, n = grid[0].length;

        // init
        for (int i = 1; i < m; i++) {
            grid[i][0] += grid[i - 1][0];
        }

        for (int j = 1; j < n; j++) {
            grid[0][j] += grid[0][j - 1];
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                grid[i][j] += Math.min(grid[i - 1][j], grid[i][j - 1]);
            }
        }

        return grid[m - 1][n - 1];
    }
}
~~~

~~~
SNAPCHAT的面经题
但不同的是LC上只要求你给出MIN PATH SUM是多少就行， 而SNAPCHAT貌似要你输出所有可能的MINIMUM PATH SUM路径。这个题怎么做？
链接: https://instant.1point3acres.com/thread/198644
来源: 一亩三分地
~~~
