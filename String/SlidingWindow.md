## 76. Minimum Window Substring
Given a string S and a string T, find the minimum window in S which will contain all the characters in T in complexity O(n).

For example,
S = "ADOBECODEBANC"
T = "ABC"
Minimum window is "BANC".

Note:
If there is no such window in S that covers all characters in T, return the empty string "".

If there are multiple such windows, you are guaranteed that there will always be only one unique minimum window in S.

#### Solution
Sliding Window经典题

**关键是即使map.get(ch) <= 0 也要持续更新value, 这样在回退的时候方便统计**

Attempts: 3
~~~
public class Solution {
    public String minWindow(String s, String t) {
        if (t == null || s == null || s.length() < t.length())  return "";

        int lens = s.length();
        int lent = t.length();
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        for (int i = 0; i < lent; i++) {
            char ch = t.charAt(i);
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }

        int i = 0;
        int j = 0;
        int count = map.size();
        String ans = null;
        while (j < lens) {
            char chj = s.charAt(j);
            if (map.containsKey(chj)) {
                map.put(chj, map.get(chj) - 1);
                if (map.get(chj) == 0) count--;
            }
            while (count == 0) {
                // update ans
                if (ans == null || j - i + 1 < ans.length()) ans = s.substring(i, j + 1);

                char chi = s.charAt(i);
                if (map.containsKey(chi)) {
                    if (map.get(chi) == 0) count++;
                    map.put(chi, map.get(chi) + 1);
                }
                i++;
            }

            j++;
        }

        return ans == null ? "" : ans;
    }
}
~~~

## 567. Permutation in String
Given two strings s1 and s2, write a function to return true if s2 contains the permutation of s1. In other words, one of the first string's permutations is the substring of the second string.

Example 1:
~~~
Input:s1 = "ab" s2 = "eidbaooo"
Output:True
Explanation: s2 contains one permutation of s1 ("ba").
~~~

Example 2:
~~~
Input:s1= "ab" s2 = "eidboaoo"
Output: False
~~~

Note:
The input strings only contain lower case letters.
The length of both given strings is in range [1, 10,000].

#### Solution
Sliding window. <br>
**i,j同步移动保证sliding window的大小是s1.length()即可**

~~~
public class Solution {
    public boolean checkInclusion(String s1, String s2) {
        if (s1 == null || s1.length() == 0) return true;
        if (s2 == null || s1.length() > s2.length()) return false;

        int[] dict = new int[26];
        int len1 = s1.length();
        int len2 = s2.length();

        for (int i = 0; i < len1; i++) {
            char ch = s1.charAt(i);
            dict[ch - 'a']++;
        }

        for (int i = 0; i < len1; i++) {
            char ch = s2.charAt(i);
            dict[ch - 'a']--;    
        }
        if (allZero(dict)) return true;

        int j = 0;
        for (int i = s1.length(); i < len2; i++) {
            char chi = s2.charAt(i);
            char chj = s2.charAt(j);
            dict[chj - 'a']++;
            dict[chi - 'a']--;
            j++;
            if (allZero(dict)) return true;
        }

        return false;
    }

    private boolean allZero(int[] dict) {
        for (int i = 0; i < dict.length; i++) {
            if (dict[i] != 0) return false;
        }
        return true;
    }
}
~~~

## 340. Longest Substring with At Most K Distinct Characters (Medium)
Given a string, find the length of the longest substring T that contains at most k distinct characters.

For example, Given s = “eceba” and k = 2,

T is "ece" which its length is 3.

#### Solution
Use two pointers, and a dict to make sure a substring has at most K distinct characters.

**注意这题和395. Longest Substring with At Least K Repeating Characters的区别**

Attempt: 1
~~~
public class Solution {
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (s == null || s.length() == 0 || k <= 0) return 0;
        int[] dict = new int[256];
        int i = 0;
        int j = 0;
        int num = 0;
        int len = 0;
        while (j < s.length()) {
            char chj = s.charAt(j);
            if (dict[chj] == 0) num++;
            dict[chj]++;

            while (num > k) {
                char chi = s.charAt(i);
                dict[chi]--;
                if (dict[chi] == 0) num--;
                i++;
            }

            len = Math.max(len, j - i + 1);
            j++;
        }

        return len;
    }
}
~~~

## 395. Longest Substring with At Least K Repeating Characters
Find the length of the longest substring T of a given string (consists of lowercase letters only) such that every character in T appears no less than k times.

Example 1:
~~~
Input:
s = "aaabb", k = 3

Output:
3

The longest substring is "aaa", as 'a' is repeated 3 times.
~~~

Example 2:
~~~
Input:
s = "ababbc", k = 2

Output:
5

The longest substring is "ababb", as 'a' is repeated 2 times and 'b' is repeated 3 times.
~~~
