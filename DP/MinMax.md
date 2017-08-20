## 375. Guess Number Higher or Lower II
We are playing the Guess Game. The game is as follows:

I pick a number from 1 to n. You have to guess which number I picked.

Every time you guess wrong, I'll tell you whether the number I picked is higher or lower.

However, when you guess a particular number x, and you guess wrong, you pay $x. You win the game when you guess the number I picked.

Example:
~~~
n = 10, I pick 8.

First round:  You guess 5, I tell you that it's higher. You pay $5.
Second round: You guess 7, I tell you that it's higher. You pay $7.
Third round:  You guess 9, I tell you that it's lower. You pay $9.

Game over. 8 is the number I picked.

You end up paying $5 + $7 + $9 = $21.
~~~

Given a particular n ≥ 1, find out how much money you need to have to guarantee a win.

#### Solution
1. 最小化(最大可能代价)
  - 最小：尝试选择从1到n
  - 最大：对于每一次选择都要考虑最大代价 k + Math.max(dp[i][k - 1], dp[k + 1][j]);
2. dp[i][j] 表示guess从i到j内的数的最小代价
  - dp[i][j] = min(k + max(dp[i][k - 1], dp[k + 1][j]))

~~~
public class Solution {
    public int getMoneyAmount(int n) {
        int[][] dp = new int[n + 1][n + 1];

        for (int i = 1; i < n + 1; i++) {
            dp[i][i] = 0;
            for (int j = i - 1; j > 0; j--) {
                dp[j][i] = Integer.MAX_VALUE;
                for (int k = j; k < i; k++) {
                    dp[j][i] = Math.min(dp[j][i], k + Math.max(dp[j][k - 1], dp[k + 1][i]));
                }
            }
        }

        return dp[1][n];
    }
}
~~~

## 464. Can I Win
In the "100 game," two players take turns adding, to a running total, any integer from 1..10. The player who first causes the running total to reach or exceed 100 wins.

What if we change the game so that players cannot re-use integers?

For example, two players might take turns drawing from a common pool of numbers of 1..15 without replacement until they reach a total >= 100.

Given an integer maxChoosableInteger and another integer desiredTotal, determine if the first player to move can force a win, assuming both players play optimally.

You can always assume that maxChoosableInteger will not be larger than 20 and desiredTotal will not be larger than 300.

Example
~~~
Input:
maxChoosableInteger = 10
desiredTotal = 11

Output:
false

Explanation:
No matter which integer the first player choose, the first player will lose.
The first player can choose an integer from 1 up to 10.
If the first player choose 1, the second player can only choose integers from 2 up to 10.
The second player will win by choosing 10 and get a total = 11, which is >= desiredTotal.
Same with other integers chosen by the first player, the second player will always win.
~~~

#### Solution
1. 几个边界情况，
  - 如果 maxChoosableInteger >= desiredTotal, return true
  - 如果 sum(1 : maxChoosableInteger) < desiredTotal, return false
2. 在两种情况下，第一个人可以win
  - 可选择的数字 > desiredTotal
  - 遍历所有可以用的数字，然后递归调用函数，如果递归函数返回false (说明第二个人不能win)，则第一个人可以赢
3. 单纯的递归会超时，使用dp存储状态，这里的Map不能简单使用desiredTotal, 因为每个数字允许使用一次，所以要用每个状态作为map的key，这里可以使用used数组把数组转出String作为key，因为题目限定maxChoosableInteger最大为20，也可以使用int used，然后通过位运算获得每个数字的使用状态。

~~~
public class Solution {
    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        if (maxChoosableInteger >= desiredTotal) return true;
        if (maxChoosableInteger * (maxChoosableInteger + 1) / 2 < desiredTotal) return false;

        int used = 0;
        Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();
        if (helper(maxChoosableInteger, used, desiredTotal, map)) return true;
        return false;
    }

    public boolean helper(int maxChoosableInteger, int used, int desiredTotal, Map<Integer, Boolean> map) {     
        if (map.containsKey(used)) return map.get(used);

        for (int i = 1; i <= maxChoosableInteger; i++) {
            if (((used >> i) & 1) == 1) continue;

            used += (1 << i);
            if (i >= desiredTotal || !helper(maxChoosableInteger, used, desiredTotal - i, map)) {
                used -= (1 << i);
                map.put(used, true);
                return true;
            }
            used -= (1 << i);
        }

        map.put(used, false);
        return false;
    }
}
~~~
