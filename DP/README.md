# DP.java

## 139. Word Break
Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, determine if s can be segmented into a space-separated sequence of one or more dictionary words. You may assume the dictionary does not contain duplicate words.

For example, given
s = "leetcode",
dict = ["leet", "code"].

Return true because "leetcode" can be segmented as "leet code".

#### Solution
The boolean DP[i] means whether the substring from 0 to i (inclusive) is a valid word break.
1. Base case, DP[0] is true, which means empty string is a valid work breakable string.
2. Transition formula: dp[i] = (dp[0] && dict.contains(s.substring(0, i + 1))) || (dp[1] && dict.contains(s.substring(1, i + 1)))) || ... || (dp[i] && dict.contains(s.substring(i, i + 1)))).

## 140. Word Break II
Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, add spaces in s to construct a sentence where each word is a valid dictionary word. You may assume the dictionary does not contain duplicate words.

Return all such possible sentences.

For example, given
s = "catsanddog",
dict = ["cat", "cats", "and", "sand", "dog"].

A solution is ["cats and dog", "cat sand dog"].

#### Solution
Niiave backtacking got TLE with the test case:
~~~~
"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
["a","aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa","aaaaaaaaaa"]

answer: []
~~~~

Use a HashMap<String, LinkedList<String>> map to store word breaks of substrings, so as to avoid duplicated search.

## 152. Maximum Product Subarray (Medium*)
Find the contiguous subarray within an array (containing at least one number) which has the largest product.

For example, given the array [2,3,-2,4],
the contiguous subarray [2,3] has the largest product = 6.

#### Solution
The basic idea is to record the maximum and minimum product for array[0:i]. We can use DP to crack the problem. For example,
    2, 0, 3, -2, -3
max 2  0  3  0   18
min 2  0  0  -6  -3

Method 1. We can use a DP[n] array to store the value
Method 2. No need to use O(n) space because we only need to know the max and min value of the previous one element.
