## 650. 2 Keys Keyboard
Initially on a notepad only one character 'A' is present. You can perform two operations on this notepad for each step:

Copy All: You can copy all the characters present on the notepad (partial copy is not allowed).
Paste: You can paste the characters which are copied last time.
Given a number n. You have to get exactly n 'A' on the notepad by performing the minimum number of steps permitted. Output the minimum number of steps to get n 'A'.

Example 1:
~~~
Input: 3
Output: 3
Explanation:
Intitally, we have one character 'A'.
In step 1, we use Copy All operation.
In step 2, we use Paste operation to get 'AA'.
In step 3, we use Paste operation to get 'AAA'.
~~~

Note:
The n will be in the range [1, 1000].

#### Solution
Method 1: DP solution. If i can be divided by j, the we can make i / j copies of j to construct i.
~~~
public class Solution {
    public int minSteps(int n) {
        int[] dp = new int[n + 1];
        for (int i = 2; i < n + 1; i++) {
            dp[i] = i;
            for (int j = i - 1; j > 0; j--) {
                if (i % j == 0) {
                    int count = i / j;
                    dp[i] = dp[j] + count;
                    break; // break with boost up the speed
                }
            }
        }
        return dp[n];
    }
}
~~~

Method 2: Math. If i can be divided by j, the we can make i / j copies of j to construct i.
Think n = 1024, 1024 can be divided by 2, we can make 2 copies of 512 to construct 2014, so we add 2 to the final ans. And then for 512, we can make 2 copies of 256 to construct 512, so we add another 2 to the final ans. And so on.
~~~
public class Solution {
    public int minSteps(int n) {
        if (n == 1) return 0;
        int ans = 0;
        while (n > 1) {
            for (int i = 2; i <= n; i++) {
                if (n % i == 0) {
                    ans += i;
                    n = n / i;
                    break;
                }
            }
        }
        return ans;
    }
}
~~~

## 651. 4 Keys Keyboard

Imagine you have a special keyboard with the following keys:

Key 1: (A): Print one 'A' on screen.

Key 2: (Ctrl-A): Select the whole screen.

Key 3: (Ctrl-C): Copy selection to buffer.

Key 4: (Ctrl-V): Print buffer on screen appending it after what has already been printed.

Now, you can only press the keyboard for N times (with the above four keys), find out the maximum numbers of 'A' you can print on screen.

Example 1:
~~~
Input: N = 3
Output: 3
Explanation:
We can at most get 3 A's on screen by pressing following key sequence:
A, A, A
~~~

Example 2:
~~~
Input: N = 7
Output: 9
Explanation:
We can at most get 9 A's on screen by pressing following key sequence:
A, A, A, Ctrl A, Ctrl C, Ctrl V, Ctrl V
~~~

Note:
1 <= N <= 50
Answers will be in the range of 32-bit signed integer.

#### Solution
DP.
1. 如果N <= 6的话，那么最长的A就是通过一直print A得到的
2. 对于其他情况，我们想要找打每个节点，这个节点以后我们只需要进行一次ctrl a + ctrl c，然后不断的ctrl v 就可以得到最长A
~~~
class Solution {
    public int maxA(int N) {
        if (N <= 0) return 0;

        if (N <= 6) return N;

        int[] dp = new int[N + 1];
        for (int i = 1; i <= 6; i++) dp[i] = i;
        for (int i = 7; i < N + 1; i++) {
            dp[i] = i;
            for (int j = i - 3; j >= 1; j--) {
                dp[i] = Math.max((i - j - 1) * dp[j], dp[i]);
            }
        }

        return dp[N];
    }
}
~~~
