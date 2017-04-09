The Java code summarizes solutions for leetcode string problems.

# StringSolution.java

## 139. Word Break
Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, determine if s can be segmented into a space-separated sequence of one or more dictionary words. You may assume the dictionary does not contain duplicate words.

For example, given
s = "leetcode",
dict = ["leet", "code"].

Return true because "leetcode" can be segmented as "leet code".

#### Solution
Dynamic Programming.
1. One dimension array dp[], dp[i] means whether a substring from 0 to i (exclusive) has a valid word break or not.
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
