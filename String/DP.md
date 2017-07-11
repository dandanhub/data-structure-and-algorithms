# DP
## 10. Regular Expression Matching
Implement regular expression matching with support for '.' and '\*'.

~~~
'.' Matches any single character.
'*' Matches zero or more of the preceding element.

The matching should cover the entire input string (not partial).

The function prototype should be:
bool isMatch(const char *s, const char *p)

Some examples:
isMatch("aa","a") → false
isMatch("aa","aa") → true
isMatch("aaa","aa") → false
isMatch("aa", "a*") → true
isMatch("aa", ".*") → true
isMatch("ab", ".*") → true
isMatch("aab", "c*a*b") → true
~~~

#### Solution
Two dimensional DP, boolean dp[i][j] means whether substring of s(0, i) matches p(0, j).
1. if s[i] == p[j] or p[j] == '.', dp[i][j] = dp[i-1][j-1]
2. else if p[j] == '\*'
- if s[i] != p[j-1], dp[i][j] = dp[i][j-2] (match a* zero time)
- else dp[i][j] = dp[i][j-2] (match zero time) || dp[i][j-1] (match one time) || dp[i-1][j] (match multi times)

~~~
public class Solution {
    public boolean isMatch(String s, String p) {
        int slen = s.length();
        int plen = p.length();
        boolean[][] dp = new boolean[slen + 1][plen + 1];
        dp[0][0] = true;

        // 处理 a*
        // b
        // .*b*c 这种初始化 dp[0][1] = false, dp[0][2] = true
        // bb* 直接返回
        // b*b
        for (int j = 0; j < plen - 1; j = j + 2) {
            int next = j + 1;
            if (p.charAt(next) == '*') {
                dp[0][j + 1] = false;
                dp[0][next + 1] = true;
            }
            else {
                break;
            }
        }

        for (int i = 1; i < slen + 1; i++) {
            for (int j = 1; j < plen + 1; j++) {
                if (s.charAt(i - 1) == p.charAt(j - 1)) {
                    dp[i][j] = dp[i-1][j-1];
                }
                else if (p.charAt(j - 1) == '.') {
                    dp[i][j] = dp[i-1][j-1];
                }
                else if (p.charAt(j - 1) == '*') {
                    // "aaaac"
                    // "a*."
                    if (s.charAt(i - 1) == p.charAt(j - 2) || p.charAt(j - 2) == '.') {
                        // dp[i][j] = dp[i][j-2] || dp[i][j-1] || dp[i-1][j];
                        dp[i][j] = dp[i][j-2] || dp[i-1][j];
                    }
                    else { // a* 匹配0次
                        dp[i][j] = dp[i][j-2];
                    }
                }
            }
        }
        return dp[slen][plen];
    }
}
~~~

## 44. Wildcard Matching
Implement wildcard pattern matching with support for '?' and '\*'.

~~~
'?' Matches any single character.
'*' Matches any sequence of characters (including the empty sequence).

The matching should cover the entire input string (not partial).

The function prototype should be:
bool isMatch(const char *s, const char *p)

Some examples:
isMatch("aa","a") → false
isMatch("aa","aa") → true
isMatch("aaa","aa") → false
isMatch("aa", "*") → true
isMatch("aa", "a*") → true
isMatch("ab", "?*") → true
isMatch("aab", "c*a*b") → false
~~~

#### Solution
1. if s[i] == p[j] or p[j] == '?', dp[i][j] = dp[i-1][j-1]
2. else if p[j] == '\*', dp[i][j] = dp[i][j-1] || dp[i-1][j]

~~~
public class Solution {
    public boolean isMatch(String s, String p) {
        // edge cases
        if (s == null || p == null) return false;

        int sl = s.length();
        int pl = p.length();
        boolean[][] dp = new boolean[sl + 1][pl + 1];
        dp[0][0] = true;
        for (int i = 1; i < pl + 1; i++) {
            if (p.charAt(i - 1) == '*') dp[0][i] = true;
            else break;
        }

        for (int i = 1; i < sl + 1; i++) { // bug
            for (int j = 1; j < pl + 1; j++) { // bug
                if (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '?') {
                    dp[i][j] = dp[i - 1][j - 1];
                }
                else if (p.charAt(j - 1) == '*') {
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                }
            }
        }

        return dp[sl][pl];
    }
}
~~~
