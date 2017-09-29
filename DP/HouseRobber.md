## 198. House Robber
You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed, the only constraint stopping you from robbing each of them is that adjacent houses have security system connected and it will automatically contact the police if two adjacent houses were broken into on the same night.

Given a list of non-negative integers representing the amount of money of each house, determine the maximum amount of money you can rob tonight without alerting the police.

#### Solution
Method 1: O(n) time and O(n) space
~~~
class Solution {
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int len = nums.length;
        int[] dp = new int[len + 1];
        dp[0] = 0;
        dp[1] = nums[0];
        int max = dp[1];
        for (int i = 2; i < len + 1; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i - 1]);
            max = Math.max(max, dp[i]);
        }
        return max;
    }
}
~~~

Method 2: O(n) time and O(1) space
~~~
class Solution {
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int prev = 0;
        int curr = 0;
        for (int i = 0; i < nums.length; i++) {
            int temp = curr;
            curr = Math.max(prev + nums[i], curr);
            prev = temp;
        }
        return curr;
    }
}
~~~

## 213. House Robber II
Note: This is an extension of House Robber.

After robbing those houses on that street, the thief has found himself a new place for his thievery so that he will not get too much attention. This time, all houses at this place are arranged in a circle. That means the first house is the neighbor of the last one. Meanwhile, the security system for these houses remain the same as for those in the previous street.

Given a list of non-negative integers representing the amount of money of each house, determine the maximum amount of money you can rob tonight without alerting the police.

#### Solution
Method 1: Two pass
~~~
class Solution {
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        // edge case 不要忘记处理这个edge case
        if (nums.length == 1) return nums[0];

        int prev = 0;
        int curr = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            int temp = curr;
            curr = Math.max(prev + nums[i], curr);
            prev = temp;
        }

        int ans = curr;

        prev = 0;
        curr = 0;
        for (int i = 1; i < nums.length; i++) {
            int temp = curr;
            curr = Math.max(prev + nums[i], curr);
            prev = temp;
        }

        ans = Math.max(ans, curr);

        return ans;
    }
}
~~~

~~~
Clean code 就是把函数封装起来
~~~

## 337. House Robber III
The thief has found himself a new place for his thievery again. There is only one entrance to this area, called the "root." Besides the root, each house has one and only one parent house. After a tour, the smart thief realized that "all houses in this place forms a binary tree". It will automatically contact the police if two directly-linked houses were broken into on the same night.

Determine the maximum amount of money the thief can rob tonight without alerting the police.

Example 1:
~~~
     3
    / \
   2   3
    \   \
     3   1
~~~
Maximum amount of money the thief can rob = 3 + 3 + 1 = 7.

Example 2:
~~~
     3
    / \
   4   5
  / \   \
 1   3   1
~~~
Maximum amount of money the thief can rob = 4 + 5 = 9.

#### Solution
To improve the code:
1. Add hashmap mem
2. How to make the code more elegant?
~~~
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public int rob(TreeNode root) {
        if (root == null) return 0;
        return Math.max(helper(root, true), helper(root, false));
    }

    private int helper(TreeNode root, boolean included) {
        int sum =  0;
        if (included) {
            sum += root.val;
            if (root.left != null) sum += helper(root.left, false);
            if (root.right != null) sum += helper(root.right, false);
        }
        else {
            if (root.left != null) {
                sum += Math.max(helper(root.left, true), helper(root.left, false));
            }
            if (root.right != null) {
                sum += Math.max(helper(root.right, true), helper(root.right, false));
            }
        }
        return sum;
    }
}
~~~

## 256. Paint House
There are a row of n houses, each house can be painted with one of the three colors: red, blue or green. The cost of painting each house with a certain color is different. You have to paint all the houses such that no two adjacent houses have the same color.

The cost of painting each house with a certain color is represented by a n x 3 cost matrix. For example, costs[0][0] is the cost of painting house 0 with color red; costs[1][2] is the cost of painting house 1 with color green, and so on... Find the minimum cost to paint all houses.

Note:
All costs are positive integers.

#### Solution
Method 1: m - number of house, n - number of color <br>
Time O(mn^2), Space O(mn)
~~~
class Solution {
    public int minCost(int[][] costs) {
        if (costs == null || costs.length == 0 || costs[0].length == 0) return 0;

        int h = costs.length;
        int c = costs[0].length;
        int[][] dp = new int[h][c];
        for (int j = 0; j < c; j++) dp[0][j] = costs[0][j];

        for (int i = 1; i < h; i++) {
            for (int j = 0; j < c; j++) {
                dp[i][j] = Integer.MAX_VALUE;
                for (int k = 0; k < c; k++) {
                    if (j == k) continue;
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][k] + costs[i][j]);
                }
            }
        }

        int min = Integer.MAX_VALUE;
        for (int j = 0; j  < c; j++) min = Math.min(min, dp[h - 1][j]);
        return min;
    }
}
~~~

Method 2: limited to the question <br>
Time O(m), Space O(1)
~~~
class Solution {
    public int minCost(int[][] costs) {
        if (costs == null || costs.length == 0 || costs[0].length == 0) return 0;

        int lastR = costs[0][0];
        int lastG = costs[0][1];
        int lastB = costs[0][2];

        for (int i = 1; i < costs.length; i++) {
            int currR = Math.min(lastG, lastB) + costs[i][0];
            int currG = Math.min(lastR, lastB) + costs[i][1];
            int currB = Math.min(lastR, lastG) + costs[i][2];

            lastR = currR;
            lastG = currG;
            lastB = currB;
        }

        return Math.min(lastR, Math.min(lastG, lastB));
    }
}
~~~

## 265. Paint House II
There are a row of n houses, each house can be painted with one of the k colors. The cost of painting each house with a certain color is different. You have to paint all the houses such that no two adjacent houses have the same color.

The cost of painting each house with a certain color is represented by a n x k cost matrix. For example, costs[0][0] is the cost of painting house 0 with color 0; costs[1][2] is the cost of painting house 1 with color 2, and so on... Find the minimum cost to paint all houses.

Note:
All costs are positive integers.

Follow up:
Could you solve it in O(nk) runtime?

#### Solution
Time: O(nk) time, Space: O(1)
~~~
class Solution {
    public int minCostII(int[][] costs) {
        if (costs == null || costs.length == 0 || costs[0].length == 0) return 0;

        int n = costs.length;
        int k = costs[0].length;

        int lastMin = 0;
        int lastSec = 0;
        int lastIndex = -1;

        for (int i = 0; i < n; i++) {
            int curIndex = -1;
            int curMin = Integer.MAX_VALUE;
            int curSec = Integer.MAX_VALUE;
            for (int j = 0; j < k; j++) {
                int cost = (j == lastIndex ? lastSec : lastMin) + costs[i][j];
                if (cost < curMin) {
                    curSec = curMin;
                    curMin = cost;
                    curIndex = j;
                }
                else if (cost < curSec) {
                    curSec = cost;
                }
            }
            lastIndex = curIndex;
            lastMin = curMin;
            lastSec = curSec;
        }

        return lastMin;
    }
}
~~~

## 276. Paint Fence
There is a fence with n posts, each post can be painted with one of the k colors.

You have to paint all the posts such that no more than two adjacent fence posts have the same color.

Return the total number of ways you can paint the fence.

Note:
n and k are non-negative integers.

#### Solution
~~~
There is a fence with n posts, each post can be painted with one of the k colors.

You have to paint all the posts such that no more than two adjacent fence posts have the same color.

Return the total number of ways you can paint the fence.

Note:
n and k are non-negative integers.
~~~
class Solution {
    public int numWays(int n, int k) {
        if (n == 0) return 0;
        if (n == 1) return k;

        int sameColor = k;
        int diffColor = k * (k - 1);
        for (int i = 2; i < n; i++) {
            int temp = diffColor;
            diffColor = (sameColor + diffColor) * (k - 1);
            sameColor = temp;
        }
        return sameColor + diffColor;
    }
}
~~~
