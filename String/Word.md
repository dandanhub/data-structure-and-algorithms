## 151. Reverse Words in a String
Given an input string, reverse the string word by word.

For example,
Given s = "the sky is blue",
return "blue is sky the".

## 186. Reverse Words in a String II
Given an input string, reverse the string word by word. A word is defined as a sequence of non-space characters.

The input string does not contain leading or trailing spaces and the words are always separated by a single space.

For example,
Given s = "the sky is blue",
return "blue is sky the".

Could you do it in-place without allocating extra space?

## 557. Reverse Words in a String III
Given a string, you need to reverse the order of characters in each word within a sentence while still preserving whitespace and initial word order.

Example 1:
Input: "Let's take LeetCode contest"
Output: "s'teL ekat edoCteeL tsetnoc"
Note: In the string, each word is separated by single space and there will not be any extra space in the string.

#### Solution
- Without restriction 151 and 557, the problem is easy. When with restriction like 186, we need to do it in-place, we can first reverse the whole string in-place and reverse each word in-place.

---

## 139. Word Break
Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, determine if s can be segmented into a space-separated sequence of one or more dictionary words. You may assume the dictionary does not contain duplicate words.

For example, given
s = "leetcode",
dict = ["leet", "code"].

Return true because "leetcode" can be segmented as "leet code".

##


#### Solution
m - the length of s
n - the size of dict
k - the avg length of word in dict
For 139. Word Break, use DP time complexity O(nmk).
For 140. Word Break II, typical backtracking + DP (cached intermediate result).
**Complexity Analysis**
Time: worst case, call backtrack func to check every suffixes of s (fixed end), m times.
Each time, iterate the whole dict and compare string nk. In total, it is O(mnk)
Space: O(kn) store the word as set.
Check discussion here[https://stackoverflow.com/questions/21273505/memoization-algorithm-time-complexity].



---
