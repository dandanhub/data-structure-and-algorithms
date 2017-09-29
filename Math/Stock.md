## 121. Best Time to Buy and Sell Stock (Easy)
Say you have an array for which the ith element is the price of a given stock on day i.

If you were only permitted to complete at most one transaction (ie, buy one and sell one share of the stock), design an algorithm to find the maximum profit.

Example 1:
~~~
Input: [7, 1, 5, 3, 6, 4]
Output: 5

max. difference = 6-1 = 5 (not 7-1 = 6, as selling price needs to be larger than buying price)
~~~

Example 2:
~~~
Input: [7, 6, 4, 3, 1]
Output: 0

In this case, no transaction is done, i.e. max profit = 0.
~~~

#### Solution
Time: O(n) <br>
Space: O(1) <br>

Attempts: 1
~~~
public class Solution {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) return 0;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int ans = 0;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < min) {
                min = prices[i];
                max = prices[i];
            }
            if (prices[i] > max) {
                max = prices[i];
                ans = Math.max(ans, max - min);
            }
        }
        return ans;
    }
}
~~~

## 122. Best Time to Buy and Sell Stock II
Say you have an array for which the ith element is the price of a given stock on day i.

Design an algorithm to find the maximum profit. You may complete as many transactions as you like (ie, buy one and sell one share of the stock multiple times). However, you may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).

#### Solution
~~~
class Solution {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) return 0;

        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                profit += prices[i] - prices[i - 1];
            }
        }

        return profit;
    }
}
~~~

## 123. Best Time to Buy and Sell Stock III
Say you have an array for which the ith element is the price of a given stock on day i.

Design an algorithm to find the maximum profit. You may complete at most two transactions.

Note:
You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).

#### Solution
time O(n); O(n) space
~~~
class Solution {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) return 0;

        int len = prices.length;
        int[] starts = new int[len];
        int[] ends = new int[len];

        int min = Integer.MAX_VALUE;
        int profit = 0;
        for (int i = 0; i < len; i++) {
            if (prices[i] >= min) {
                profit = Math.max(profit, prices[i] - min);
            } else {
                min = prices[i];
            }
            starts[i] = profit;
        }

        int max = Integer.MIN_VALUE;
        profit = 0;
        for (int i = len - 1; i >= 0; i--) {
            if (prices[i] <= max) {
                profit = Math.max(profit, max - prices[i]);
            } else {
                max = prices[i];
            }
            ends[i] = profit;
        }

        int ans = 0;
        for (int i = 0; i < len; i++) {
            ans = Math.max(ans, starts[i] + ends[i]);
        }

        return ans;
    }
}
~~~

time O(n); space O(1)
~~~
class Solution {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) return 0;

        int buy1 = Integer.MIN_VALUE, buy2 = Integer.MIN_VALUE;
        int sell1 = 0, sell2 = 0;

        for (int price : prices) {
            sell2 = Math.max(sell2, buy2 + price);
            buy2 = Math.max(buy2, sell1 - price);
            sell1 = Math.max(sell1, buy1 + price);
            buy1 = Math.max(buy1, -price);
        }
        return sell2;
    }
}
~~~

## 188. Best Time to Buy and Sell Stock IV
Say you have an array for which the ith element is the price of a given stock on day i.

Design an algorithm to find the maximum profit. You may complete at most k transactions.

Note:
You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).

#### Solution
DP[k][i] = max(dp[k][i-1], max(dp[k-1][j] - prices[j] + prices[i])
