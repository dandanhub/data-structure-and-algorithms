## 125. Valid Palindrome
Given a string, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.

For example,
"A man, a plan, a canal: Panama" is a palindrome.
"race a car" is not a palindrome.

Note:
Have you consider that the string might be empty? This is a good question to ask during an interview.

For the purpose of this problem, we define empty string as valid palindrome.

#### Solution
**注意如何使用Character.toLowerCase(ch), Character.isDigit(ch), Character.isLetter(ch), Character.isLetterOrDigit(ch)接口**

~~~
public class Solution {
    public boolean isPalindrome(String s) {
        if (s == null) return false;
        if (s.length() == 0) return true;
        int i = 0;
        int j = s.length() - 1;
        while (i < j) {
            while (i < j && !Character.isLetterOrDigit(s.charAt(i))) {
                i++;
            }
            while (i < j && !Character.isLetterOrDigit(s.charAt(j))) {
                j--;
            }
            if (Character.toLowerCase(s.charAt(i)) != Character.toLowerCase(s.charAt(j))) return false;
            i++;
            j--;
        }
        return true;
    }
}
~~~

## 5. Longest Palindromic Substring
Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.

Example:
~~~
Input: "babad"
Output: "bab"
Note: "aba" is also a valid answer.
~~~

Example:
~~~
Input: "cbbd"
Output: "bb"
~~~

#### Solution
1. O(n^2) 每个节点作为中间点往两边搜索
2. Manacher’s Algorithm O(n) Solution 可做做到O(n)

Time O(n^2) <br>
~~~
public class Solution {
    public String longestPalindrome(String s) {
        if (s == null || s.length() == 0) return s;
        String ans = "";

        for (int i = 0; i < s.length(); i++) {
            String m = helper(s, i, i);
            String n = helper(s, i, i + 1);
            if (m.length() > ans.length()) ans = m;
            if (n.length() > ans.length()) ans = n;
        }

        return ans;
    }

    private String helper(String s, int start, int end) {
        int i = start;
        int j = end;
        while (i >= 0 && j < s.length()) {
            if (s.charAt(i) != s.charAt(j)) {
                break;
            }
            i--;
            j++;
        }
        return s.substring(i + 1, j);
    }
}
~~~

## 131. Palindrome Partitioning (Medium) *
Given a string s, partition s such that every substring of the partition is a palindrome.

Return all possible palindrome partitioning of s.

For example, given s = "aab",
Return
~~~~
[
  ["aa","b"],
  ["a","a","b"]
]
~~~~

#### Solution
Use backtracking template.
1. Given a string s, if s.substring(0, i) is a valid palindrome, then recursively check the palindrome partition of s.substring(i).
2. The base case of recursion is when a string is empty, we can add the partitions to final result and return.
3. Handle the base return case carefully.

**如何加快速度** <br>

Attempts: 6
~~~
public class Solution {
    public List<List<String>> partition(String s) {
        List<List<String>> ans = new ArrayList<List<String>>();

        // edge cases
        if (s == null) return ans;
        if (s.length() == 0) {
            ans.add(new ArrayList<String>());
            return ans;
        }

        backtrack(s, ans, new ArrayList<String>());
        return ans;
    }

    public void backtrack(String s, List<List<String>> ans, List<String> list) {
        // base case
        if (s.length() == 0) {
            ans.add(new ArrayList<String>(list));
            return;
        }

        for (int i = 1; i <= s.length(); i++) {
            String prefix = s.substring(0, i);
            if (isPalindrome(prefix)) {
                list.add(prefix);
                backtrack(s.substring(i), ans, list);
                list.remove(list.size() - 1);
            }
        }
    }

    private boolean isPalindrome(String s) {
        int i = 0;
        int j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) return false;
            i++;
            j--;
        }
        return true;
    }
}
~~~

## 132. Palindrome Partitioning II (Hard) *
Given a string s, partition s such that every substring of the partition is a palindrome.

Return the minimum cuts needed for a palindrome partitioning of s.

For example, given s = "aab",
Return 1 since the palindrome partitioning ["aa","b"] could be produced using 1 cut.

#### Solution
1. 最笨的DP，if (s[j:i] is palindrome) DP[i] = dp[j] + 1, i和j有n^2个可能性，每次判断if (s[j:i] is palindrome)需要O(n)的时间，总耗时O(n^3), 不出意料TLE, 如何加快速度
2. 可以提前计算一个palindrome lookup table, if (s[i] == s[j]) pal[i][j] = p[i+1][j-1]. **注意2维循环的时候i从高位到低位，j从低位到高位**，这样可以提高速度到O(n^2)
3. Discussion top solution 给出了O(n^2) time, O(n) space的方法

Attempt: 没有想出O(n^2)的方法
~~~
public class Solution {
    public int minCut(String s) {
        if (s == null || s.length() == 0) return 0;

        int len = s.length();

        // pre-calculate the palindrom look-up table
        boolean[][] palindrome = new boolean[len][len];
        for (int i = len - 1; i >= 0; i--) {
            for (int j = i; j < len; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    if (j - i < 2) {
                        palindrome[i][j] = true;
                    }
                    else {
                        palindrome[i][j] = palindrome[i + 1][j - 1];
                    }
                }
            }
        }

        int[] dp = new int[len];

        for (int i = 0; i < len; i++) {
            dp[i] = i; // dp[0] = 0
            for (int j = i; j >= 0; j--) {
                if (palindrome[j][i]) {
                    if (j == 0) dp[i] = 0;
                    else dp[i] = Math.min(dp[i], dp[j - 1] + 1);
                }
            }
        }

        return dp[len - 1];
    }
}
~~~

## 266. Palindrome Permutation (Easy)
Given a string, determine if a permutation of the string could form a palindrome.

For example,
"code" -> False, "aab" -> True, "carerac" -> True.

#### Solution
1. Two pass的方法，第一遍扫描这个str, 统计每个字符的词频，然后第二遍判断是否出现了>1的奇数词频的char
2. One pass 用set，char不在set里面，把char加进来，char在set里面，把char移除，最后判断set.size() == 0 || set.size() == 1 <br>
这题和409相似

## 409. Longest Palindrome (Easy)
Given a string which consists of lowercase or uppercase letters, find the length of the longest palindromes that can be built with those letters.

This is case sensitive, for example "Aa" is not considered a palindrome here.

Note:
Assume the length of given string will not exceed 1,010.

Example:
~~~
Input:
"abccccdd"

Output:
7

Explanation:
One longest palindrome that can be built is "dccaccd", whose length is 7.
~~~~

#### Solution
1. 笨方法也是扫描两边，第一遍统计每个单词出现的频率，第二遍数数
2. One pass, 类似于266，用HashSet, char不在set里面，把char加进来，char在set里面，说明当前的char出现第二次了，count += 2, 最后如果set不为空的话，说明至少有一个char落单了，count += 1
