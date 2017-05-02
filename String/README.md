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

## 293. Flip Game (Easy)
You are playing the following Flip Game with your friend: Given a string that contains only these two characters: + and -, you and your friend take turns to flip two consecutive "++" into "--". The game ends when a person can no longer make a move and therefore the other person will be the winner.

Write a function to compute all possible states of the string after one valid move.

For example, given s = "++++", after one move, it may become one of the following states:
~~~~
[
  "--++",
  "+--+",
  "++--"
]
~~~~
If there is no valid move, return an empty list [].

#### Solution
Simply iterate the string.

## 165. Compare Version Numbers
Compare two version numbers version1 and version2.
If version1 > version2 return 1, if version1 < version2 return -1, otherwise return 0.

You may assume that the version strings are non-empty and contain only digits and the . character.
The . character does not represent a decimal point and is used to separate number sequences.
For instance, 2.5 is not "two and a half" or "half way to version three", it is the fifth second-level revision of the second first-level revision.

Here is an example of version numbers ordering:
~~~~
0.1 < 1.1 < 1.2 < 13.37
~~~~

#### Solution
WRONG: my first idea is to compare v1 and v2 at their min length; and then handle the one that is longer. However, it becomes trivial to handle corner case, like "1.0" v.s. "1", and "01" v.s. "1".

~~~~
for (int i = 0; i < Math.min(tokens1.length, tokens2.length); i++) {
  int v1 = Integer.parseInt(tokens1[i]);
  int v2 = Integer.parseInt(tokens2[i]);
  if (v1 != v2) {
    return v1 > v2 ? 1 : -1;
    }
}
~~~~

Think from the other side, compare at their max length, for the one which is shorter, simply set it to 0.
