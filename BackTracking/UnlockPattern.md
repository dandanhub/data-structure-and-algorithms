## 351. Android Unlock Patterns

Given an Android 3x3 key lock screen and two integers m and n, where 1 ≤ m ≤ n ≤ 9, count the total number of unlock patterns of the Android lock screen, which consist of minimum of m keys and maximum n keys.

Rules for a valid pattern:
Each pattern must connect at least m keys and at most n keys.
All the keys must be distinct.
If the line connecting two consecutive keys in the pattern passes through any other keys, the other keys must have previously selected in the pattern. No jumps through non selected key is allowed.
The order of keys used matters.

Explanation:
~~~
| 1 | 2 | 3 |
| 4 | 5 | 6 |
| 7 | 8 | 9 |
~~~

- Invalid move: 4 - 1 - 3 - 6
Line 1 - 3 passes through key 2 which had not been selected in the pattern.

- Invalid move: 4 - 1 - 9 - 2
Line 1 - 9 passes through key 5 which had not been selected in the pattern.

- Valid move: 2 - 4 - 1 - 3 - 6
Line 1 - 3 is valid because it passes through key 2, which had been selected in the pattern

- Valid move: 6 - 5 - 4 - 1 - 9 - 2
Line 1 - 9 is valid because it passes through key 5, which had been selected in the pattern.

- Example:
Given m = 1, n = 1, return 9.

#### Solution
Backtracking

**粗心注意** <br>
helper(used, i, count - 1, conn); // 这里注意Backtracking的时候不要修改count本身的值

~~~
public class Solution {
    int ans = 0;

    public int numberOfPatterns(int m, int n) {
        int[][] conn = new int[10][10];
        conn[1][3] = 2;
        conn[1][7] = 4;
        conn[1][9] = 5;
        conn[2][8] = 5;
        conn[3][1] = 2;
        conn[3][7] = 5;
        conn[3][9] = 6;
        conn[4][6] = 5;
        conn[6][4] = 5;
        conn[7][1] = 4;
        conn[7][3] = 5;
        conn[7][9] = 8;
        conn[8][2] = 5;
        conn[9][1] = 5;
        conn[9][3] = 6;
        conn[9][7] = 8;
        boolean[] used = new boolean[10];
        for (int i = m; i <= n; i++) {
            helper(used, 0, i, conn);   
        }
        return ans;
    }   

    private void helper(boolean[] used, int start, int count, int[][] conn) {

        if (count == 0) {
            ans++;
            return;
        }

        for (int i = 1; i <= 9; i++) {
            if (used[i]) continue;
            if (conn[start][i] == 0 || used[conn[start][i]]) {
                used[i] = true;
                helper(used, i, count - 1, conn); // 这里注意Backtracking的时候不要修改count本身的值
                used[i] = false;
            }
        }
    }
}
~~~

利用对称性加快速度
~~~
public class Solution {

    public int numberOfPatterns(int m, int n) {
        int[][] conn = new int[10][10];
        conn[1][3] = 2;
        conn[1][7] = 4;
        conn[1][9] = 5;
        conn[2][8] = 5;
        conn[3][1] = 2;
        conn[3][7] = 5;
        conn[3][9] = 6;
        conn[4][6] = 5;
        conn[6][4] = 5;
        conn[7][1] = 4;
        conn[7][3] = 5;
        conn[7][9] = 8;
        conn[8][2] = 5;
        conn[9][1] = 5;
        conn[9][3] = 6;
        conn[9][7] = 8;

        int ans = 0;
        boolean[] used = new boolean[10];
        for (int i = m; i <= n; i++) {
            ans += 4 * helper(used, 1, i - 1, conn);   
            ans += 4 * helper(used, 2, i - 1, conn);
            ans += helper(used, 5, i - 1, conn);
        }
        return ans;
    }   

    private int helper(boolean[] used, int start, int count, int[][] conn) {
        if (count == 0) {
            return 1;
        }

        used[start] = true;
        int res = 0;
        for (int i = 1; i <= 9; i++) {
            if (used[i]) continue;
            if (conn[start][i] == 0 || used[conn[start][i]]) {
                res += helper(used, i, count - 1, conn);
            }
        }
        used[start] = false;
        return res;
    }
}
~~~
