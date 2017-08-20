You are given coins of different denominations and a total amount of money amount. Write a function to compute the fewest number of coins that you need to make up that amount. If that amount of money cannot be made up by any combination of the coins, return -1.

Example 1:
~~~
coins = [1, 2, 5], amount = 11
return 3 (11 = 5 + 5 + 1)
~~~

Example 2:
~~~
coins = [2], amount = 3
return -1.
~~~

Note:
You may assume that you have an infinite number of each kind of coin.

#### Solution
Bottom-up DP <br>
Time Complexity: O(s*n); Space Complexity: O(s)
~~~
public class Solution {
    public int coinChange(int[] coins, int amount) {
        if (coins == null) return -1;

        int[] dp = new int[amount + 1];
        for (int i = 1; i <= amount; i++) {
            dp[i] = Integer.MAX_VALUE;
            for (int j = 0; j < coins.length; j++) {
                if (coins[j] <= i && dp[i - coins[j]] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], 1 + dp[i - coins[j]]);
                }
            }
        }
        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }
}
~~~

## 518. Coin Change 2
You are given coins of different denominations and a total amount of money. Write a function to compute the number of combinations that make up that amount. You may assume that you have infinite number of each kind of coin.

Note: You can assume that

0 <= amount <= 5000
1 <= coin <= 5000
the number of coins is less than 500
the answer is guaranteed to fit into signed 32-bit integer
Example 1:
~~~
Input: amount = 5, coins = [1, 2, 5]
Output: 4
Explanation: there are four ways to make up the amount:
5=5
5=2+2+1
5=2+1+1+1
5=1+1+1+1+1
~~~

Example 2:
~~~
Input: amount = 3, coins = [2]
Output: 0
Explanation: the amount of 3 cannot be made up just with coins of 2.
~~~

Example 3:
~~~
Input: amount = 10, coins = [10]
Output: 1
~~~

#### Solution
这题有点和combination sum类似，但是不要求求出所有组合，所以考虑使用dp求个数即可。<br>

但是dp的状态有讲究，因为可能出现重复，比如sum 4, coins [1,2, 3], 结果[1, 1, 2] 和 [2, 1, 1]是重复的结果。如何才能避免重复？ <br>

对于1, 尝试所有的1个个数，然后递归。那么在做到下一个数字2的时候，不可以再回头使用1，所以对于可能的子sum，不仅要记录1D sum，还要记录它之前使用完成了哪些coins

Method 1: Top down approach <br>
这个方法比较直观，但是还是两点
1. mem的数组要使用2d数组，不仅记录子sum，还要记录哪些coins被使用完了
2. 所以在递归的时候，要一直使用某个coin到不能使用再结束
~~~
public class Solution {

    public int change(int amount, int[] coins) {
        if (coins == null) return 0;

        int[][] dp = new int[amount + 1][coins.length];
        for (int i = 0; i < dp.length; i++) Arrays.fill(dp[i], -1);
        return helper(amount, coins, 0, dp);
    }

    private int helper(int amount, int[] coins, int start, int[][] dp) {
        if (amount == 0) return 1;
        if (start == coins.length) return 0;
        if (dp[amount][start] != -1) return dp[amount][start];

        int ans = 0;
        for (int i = start; i < coins.length; i++) {
            int time = 1;
            while (amount >= time * coins[i]) {
                ans += helper(amount - time * coins[i], coins, i + 1, dp);
                time++;
            }
        }

        dp[amount][start] = ans;
        return ans;
    }
}
~~~

Method 2: DP <br>
DP[i][j]表示amount = i, 使用前j个coin的个数<br>
那么DP[i][j] = DP[i][j - 1] + DP[i - coins[j]][j]
~~~
public class Solution {
    public int change(int amount, int[] coins) {
        if (coins == null) return 0;
        if (coins.length == 0) return amount == 0 ? 1 : 0;

        int len = coins.length;
        int[][] dp = new int[amount + 1][len];
        Arrays.fill(dp[0], 1);

        for (int i = 1; i <= amount; i++) {
            for (int j = 0; j < len; j++) {
                dp[i][j] = 0;
                if (j == 0 && i < coins[j]) {
                    continue;
                }
                if (j > 0) {
                    dp[i][j] += dp[i][j - 1];
                }
                if (i >= coins[j]) {
                    dp[i][j] += dp[i - coins[j]][j];
                }
            }    
        }

        return dp[amount][len - 1];
    }
}
~~~
