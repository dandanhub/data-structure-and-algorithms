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
