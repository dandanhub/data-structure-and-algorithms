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

## 221. Maximal Square
Given a 2D binary matrix filled with 0's and 1's, find the largest square containing only 1's and return its area.

For example, given the following matrix:
~~~
1 0 1 0 0
1 0 1 1 1
1 1 1 1 1
1 0 0 1 0
~~~
Return 4.

#### Solution

Method 1: 2D DP
~~~
public class Solution {
    public int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;
        int m = matrix.length, n = matrix[0].length;
        int[][] dp = new int[m + 1][n + 1];
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    dp[i + 1][j + 1] = Math.min(Math.min(dp[i][j], dp[i + 1][j]), dp[i][j + 1]) + 1;
                    ans = Math.max(ans, dp[i + 1][j + 1]);
                }
            }
        }
        return ans * ans;
    }
}
~~~

Method 2: 1D DP
~~~
public class Solution {
    public int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;
        int m = matrix.length, n = matrix[0].length;
        int[] dp = new int[n + 1];
        int ans = 0;
        int prev = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int temp = dp[j + 1];
                if (matrix[i][j] == '1') {
                    dp[j + 1] = Math.min(Math.min(dp[j], dp[j + 1]), prev) + 1;
                    ans = Math.max(ans, dp[j + 1]);
                }
                else { //容易出错点，注意这里一定要更新dp[j + 1]为0
                    dp[j + 1] = 0;
                }
                prev = temp;
            }
        }
        return ans * ans;
    }
}
~~~

## 85. Maximal Rectangle
Given a 2D binary matrix filled with 0's and 1's, find the largest rectangle containing only 1's and return its area.

For example, given the following matrix:
~~~
1 0 1 0 0
1 0 1 1 1
1 1 1 1 1
1 0 0 1 0
~~~
Return 6.

#### Solution
Method 1: 借用84. Largest Rectangle in Histogram计算最大面积矩阵 <br>
O(mn) 但是由于出栈入栈速度不是很理想 13.55%
~~~
public class Solution {
    public int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;

        int m = matrix.length, n = matrix[0].length;
        int[] nums = new int[n];
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    nums[j] += 1;
                }
                else {
                    nums[j] = 0;
                }
            }
            ans = Math.max(ans, maxArea(nums));
        }

        return ans;
    }

    private int maxArea(int[] nums) {
        Stack<Integer> stack = new Stack<Integer>();
        int ans = 0;
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            if (stack.isEmpty() || nums[stack.peek()] <= nums[i]) {
                stack.push(i);
            }
            else {
                while (!stack.isEmpty() && nums[stack.peek()] > nums[i]) {
                    int h = nums[stack.pop()];
                    int area = stack.isEmpty() ? h * i : h * (i - stack.peek() - 1);
                    ans = Math.max(ans, area);
                }
                stack.push(i);
            }
        }

        while (!stack.isEmpty()) {
            int h = nums[stack.pop()];
            int area = stack.isEmpty() ? h *  len: h * (len - stack.peek() - 1);
            ans = Math.max(ans, area);
        }

        return ans;
    }
}
~~~

Method 2: DP O(mn) 80% <br> 
Attempts 1 bug: forgot to init rights array <br>
- 一行一行处理，计算出三个数组
  - 当前的heights
  - 当前点高度，向左能reach到的最大宽度lefts
  - 当前点高度，向右能reach到的最大宽度rights
- 根据计算出的数组，更新最大面积
~~~
public class Solution {
    public int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;
        int m = matrix.length, n = matrix[0].length;
        int ans = 0;

        int[] heights = new int[n];
        int[] lefts = new int[n];
        int[] rights = new int[n];

        Arrays.fill(rights, n); // 这里注意初始化

        for (int i = 0; i < m; i++) {
            int lp = 0;
            int rp = n;

            // update heights
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') heights[j] = heights[j] + 1;
                else heights[j] = 0;
            }

            // update lefts
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    lefts[j] = Math.max(lp, lefts[j]);
                }
                else {
                    lefts[j] = 0;
                    lp = j + 1;
                }
            }

            // update rights
            for (int j = n - 1; j >= 0; j--) {
                if (matrix[i][j] == '1') {
                    rights[j] = Math.min(rp, rights[j]);
                }
                else {
                    rights[j] = n;
                    rp = j;
                }
            }

            // calculate area
            for (int j = 0; j < n; j++) {
                ans = Math.max(ans, heights[j] * (rights[j] - lefts[j]));
            }
        }
        return ans;
    }
}
~~~

## 84. Largest Rectangle in Histogram
Given n non-negative integers representing the histogram's bar height where the width of each bar is 1, find the area of largest rectangle in the histogram.

Above is a histogram where width of each bar is 1, given height = [2,1,5,6,2,3].

The largest rectangle is shown in the shaded area, which has area = 10 unit.

For example,
Given heights = [2,1,5,6,2,3],
return 10.

#### Solution
对于每个histogram, 需要找出其向左和向右最长的递增序列，假设向左最长递增到l，向右最长递增到r，那么这个histogram能构成的最大的长方形面积为(r-l+1) * heights[i]<br>
所以可以借助Stack遍历数组：
1. 如果heights[stack.peek()] <= heights[i]，那么直接把当前数字压入栈
2. 否则的话，说明我们已经到了向右递增的尾部，我们要处理栈内元素。因为我们保证栈内元素是递增的，所以对于每一个pop出来的元素，其在栈内的上一个元素就是其向左递增的尾部，我们可以得到面积为heights[index] * (i - stack.peek() - 1).

Method 1: Brute force, O(n^2), TLE.
~~~
public class Solution {
    public int largestRectangleArea(int[] heights) {
        if (heights == null || heights.length == 0) return 0;
        int ans = 0;
        int len = heights.length;
        for (int i = 0; i < len; i++) {
            int h = heights[i];
            int l = i, r = i;
            while (l >= 0 && heights[l] >= heights[i]) l--;
            while (r < len && heights[r] >= heights[i]) r++;
            int area = h * (r - l - 1);
            ans = Math.max(ans, area);
        }

        return ans;
    }
}
~~~

Method 2: Using stack, O(n)
~~~
public class Solution {
    public int largestRectangleArea(int[] heights) {
        if (heights == null || heights.length == 0) return 0;
        Stack<Integer> stack = new Stack<Integer>();
        int ans = 0;
        int len = heights.length;
        for (int i = 0; i < len; i++) {
            if (stack.isEmpty() || heights[stack.peek()] <= heights[i]) { // if stack is empty or in increase seq
                stack.push(i);
            }
            else {
                while (!stack.isEmpty() && heights[stack.peek()] > heights[i]) {
                    int index = stack.pop();
                    int area = stack.isEmpty() ? heights[index] * i : heights[index] * (i - 1 - stack.peek());
                    ans = Math.max(ans, area);
                }
                stack.push(i);
            }
        }

        while (!stack.isEmpty()) {
            int index = stack.pop();
            int area = stack.isEmpty() ? heights[index] * len : heights[index] * (len - 1 - stack.peek());
            ans = Math.max(ans, area);
        }

        return ans;
    }
}
~~~
