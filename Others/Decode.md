## 91. Decode Ways
A message containing letters from A-Z is being encoded to numbers using the following mapping:
~~~
'A' -> 1
'B' -> 2
...
'Z' -> 26
~~~
Given an encoded message containing digits, determine the total number of ways to decode it.

For example,
Given encoded message "12", it could be decoded as "AB" (1 2) or "L" (12).

The number of ways decoding "12" is 2.

#### Solution
基本思路是dp[i] = dp[i-1] + dp[i-2]，但是注意检查nums[i]和nums[i-1]以判断当前单词是否可以解析为2个数字表示的字母或者1个数字表示的字母，另外注意检测两个0的情况

Attempts: 1
~~~
public class Solution {
    public int numDecodings(String s) {
        if (s == null || s.length() == 0) return 0;
        int[] dp = new int[s.length() + 1];
        dp[0] = 1;
        if (s.charAt(0) == '0') return 0;
        dp[1] = 1;
        for (int i = 2; i < s.length() + 1; i++) {
            if (s.charAt(i - 1) == '0') {
                if (s.charAt(i - 2) == '0' || Integer.valueOf(s.substring(i - 2, i)) > 26) return 0;
                dp[i] = dp[i - 2];
            }
            else {
                dp[i] = dp[i - 1];
                if (s.charAt(i - 2) != '0' && Integer.valueOf(s.substring(i - 2, i)) <= 26) dp[i] += dp[i - 2];
            }
        }

        return dp[s.length()];
    }
}
~~~
