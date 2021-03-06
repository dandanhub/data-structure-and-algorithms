## 30. Substring with Concatenation of All Words
You are given a string, s, and a list of words, words, that are all of the same length. Find all starting indices of substring(s) in s that is a concatenation of each word in words exactly once and without any intervening characters.

For example, given:
s: "barfoothefoobarman"
words: ["foo", "bar"]

You should return the indices: [0,9].
(order does not matter).

#### Solution
Sliding Window加上暴力破解 <br>
**如何加快速度是重点**

~~~
public class Solution {
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> list = new ArrayList<Integer>();
        if (s == null || s.length() == 0) return list;
        if (words == null || words.length == 0) return list;

        int n = words.length;
        int m = words[0].length();

        if (s.length() < m * n) return list;

        Map<String, Integer> map = new HashMap<String, Integer>();
        for (int i = 0; i < n; i++) {
            String word = words[i];
            map.put(word, map.getOrDefault(word, 0) + 1);
        }

        HashMap<String, Integer> curMap = new HashMap<String, Integer>();
        int curCount = n;
        for (int i = 0; i <= s.length() - m * n; i++) {
            for (int j = 0; j < n; j++) {
                String str = s.substring(i + j * m, i + j * m + m);
                if (map.containsKey(str) && curMap.getOrDefault(str, 0) < map.get(str)) {
                    curMap.put(str, curMap.getOrDefault(str, 0) + 1);
                    curCount--;
                }
                else {
                    break;
                }
            }
            if (curCount == 0) list.add(i);
            curCount = n;
            curMap.clear();
        }

        return list;

    }
}
~~~

## 239. Sliding Window Maximum (Hard)*
Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position.

For example,
Given nums = [1,3,-1,-3,5,3,6,7], and k = 3.
~~~
Window position                Max
---------------               -----
[1  3  -1] -3  5  3  6  7       3
 1 [3  -1  -3] 5  3  6  7       3
 1  3 [-1  -3  5] 3  6  7       5
 1  3  -1 [-3  5  3] 6  7       5
 1  3  -1  -3 [5  3  6] 7       6
 1  3  -1  -3  5 [3  6  7]      7
 ~~~
Therefore, return the max sliding window as [3,3,5,5,6,7].

Note:
You may assume k is always valid, ie: 1 ≤ k ≤ input array's size for non-empty array.

Follow up:
Could you solve it in linear time?

#### Solution
每次寻找Window的最大值，deque保证deque的第一个元素是当前最大元素

~~~
public class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 1) return nums;

        int[] ans = new int[nums.length - k + 1];
        int index = 0;
        Deque<Integer> deque = new ArrayDeque<Integer>();
        for (int i = 0; i < nums.length; i++) {
            // pop out all out of boudary number
            while (!deque.isEmpty() && deque.peek() < i - k + 1) {
                deque.poll();
            }

            // pop up smaller nums
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }

            deque.offer(i);

            if (i >= k - 1) ans[index++] = nums[deque.peekFirst()];
        }
        return ans;
    }
}
~~~

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

使用长度为256的数组
~~~
public class Solution {
    public String minWindow(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) return "";
        int[] dict = new int[256];
        int count = 0; // num of distinct char
        for (int i = 0; i < t.length(); i++) {
            if (dict[t.charAt(i)] == 0) count++;
            dict[t.charAt(i)]++;
        }

        int i = 0, j = 0;
        // String str = s; // BUG
        String str = "";
        while (j < s.length()) {
            char chj = s.charAt(j);
            dict[chj]--;
            if (dict[chj] == 0) count--;

            while (count == 0) {
                if (str.equals("") || str.length() > j - i + 1) {
                    str = s.substring(i, j + 1);
                }
                char chi = s.charAt(i);
                if (dict[chi] == 0) count++;
                dict[chi]++;
                i++;
            }

            j++;
        }

        return str;
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
            dict[ch - 'a'] += 1;
        }

        for (int i = 0; i < len1; i++) {
            char ch = s2.charAt(i);
            dict[ch - 'a'] -= 1;    
        }
        if (allZero(dict)) return true;

        int j = 0;
        for (int i = s1.length(); i < len2; i++) {
            char chj = s2.charAt(j);
            dict[chj - 'a'] += 1;
            j++;

            char chi = s2.charAt(i);
            dict[chi - 'a'] -= 1;
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

## 424. Longest Repeating Character Replacement
Given a string that consists of only uppercase English letters, you can replace any letter in the string with another letter at most k times. Find the length of a longest substring containing all repeating letters you can get after performing the above operations.

Note:
Both the string's length and k will not exceed 104.

Example 1:
~~~
Input:
s = "ABAB", k = 2

Output:
4

Explanation:
Replace the two 'A's with two 'B's or vice versa.
~~~

Example 2:
~~~
Input:
s = "AABABBA", k = 1

Output:
4

Explanation:
Replace the one 'A' in the middle with 'B' and form "AABBBBA".
The substring "BBBB" has the longest repeating letters, which is 4.
~~~

#### Solution
类似于 At most K distinct character, 但是要维护一个maxCharCount.

~~~
public class Solution {
    public int characterReplacement(String s, int k) {
        if (s == null || s.length() == 0) return 0;

        int[] dict = new int[26];
        int i = 0, j = 0;
        int res = 0, maxCharCount = 0;

        while (j < s.length()) {        
            dict[s.charAt(j) - 'A']++;
            if (maxCharCount < dict[s.charAt(j) - 'A']) {
                maxCharCount = dict[s.charAt(j) - 'A'];
            }

            // move pointer i
            while (j - i + 1 - maxCharCount > k) {
                dict[s.charAt(i) - 'A']--;
                i++;
                for (int count : dict) {
                    maxCharCount = Math.max(maxCharCount, count);
                }
            }

            res = Math.max(res, j - i + 1);
            j++;
        }

        return res;
    }
}
~~~

## 395. Longest Substring with At Least K Repeating Characters (Not Sliding Window)
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

#### Solution
分治 <br>
解题思想是如果s中所有字符都出现>=k次，那么直接返回str.length() <br>
如果有字符出现小于k次，那么合法的string中必然不能包含这个字符，所以我们去掉这个字符recursive的去获取左边和右边string的合法解长度

~~~
public class Solution {
    public int longestSubstring(String s, int k) {
        if (s == null || s.length() == 0) return 0;
        return helper(s, k, 0, s.length());
    }

    private int helper(String s, int k, int start, int end) {
        int[] dict = new int[26];
        for (int i = start; i < end; i++) {
            dict[s.charAt(i) - 'a']++;
        }

        for (int i = start; i < end; i++) {
            char ch = s.charAt(i);
            if (dict[ch - 'a'] > 0 && dict[ch - 'a'] < k) {
                int left = helper(s, k, start, i);
                int right = helper(s, k, i + 1, end);
                return Math.max(left, right);
            }
        }

        return end - start;
    }
}
~~~

## 3. Longest Substring Without Repeating Characters
Given a string, find the length of the longest substring without repeating characters.

Examples:

Given "abcabcbb", the answer is "abc", which the length is 3.

Given "bbbbb", the answer is "b", with the length of 1.

Given "pwwkew", the answer is "wke", with the length of 3. Note that the answer must be a substring, "pwke" is a subsequence and not a substring.

#### Solution

用char[256]数组来记录已有的char
~~~
public class Solution {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) return 0;
        int i = 0, j = 0;
        int len = 0;
        int[] dict = new int[256];
        while (j < s.length()) {
            char chj = s.charAt(j);
            if (dict[chj] == 0) {
                dict[chj]++;
                len = Math.max(len, j - i + 1);
                j++;
            }
            else {
                while (dict[chj] > 0) {
                    char chi = s.charAt(i);
                    dict[chi]--;
                    i++;
                }
            }
        }       
        return len;
    }
}
~~~

用HashSet来记录已有的char
~~~
public class Solution {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) return 0;
        Set<Character> set = new HashSet<Character>();
        int i = 0, j = 0, len = s.length();
        int ans = 0;
        while (j < len) {
            char cj = s.charAt(j);
            while (set.contains(cj)) {
                char ci = s.charAt(i);
                set.remove(ci);
                i++;
            }
            ans = Math.max(ans, j - i + 1);
            set.add(cj);
            j++;
        }
        return ans;
    }
}
~~~

## 159. Longest Substring with At Most Two Distinct Characters
Given a string, find the length of the longest substring T that contains at most 2 distinct characters.

For example, Given s = “eceba”,

T is "ece" which its length is 3.

#### Solution
简单实用两个计数器和两个char变量 <br>
扩展到k的话使用map <br>
Beats 91% <br>
~~~
class Solution {
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        if (s == null || s.length() == 0) return 0;

        int len = s.length();
        int i = 0;
        int j = 0;
        char ch1 = 'a';
        char ch2 = 'b';
        int c1 = 0;
        int c2 = 0;
        int ans = 1;
        while (j < len) {
            char ch = s.charAt(j);
            if (c1 != 0 && ch1 == ch) {
                c1++;
            }
            else if (c2 != 0 && ch2 == ch) {
                c2++;
            }
            else if (c1 == 0) {
                ch1 = ch;
                c1++;
            }
            else if (c2 == 0) {
                ch2 = ch;
                c2++;
            }
            else {
                ans = Math.max(ans, j - i);
                while (c1 != 0 && c2 != 0) {
                    char ci = s.charAt(i);
                    if (ci == ch1) {
                        c1--;
                    }
                    else if (ci == ch2) {
                        c2--;
                    }
                    i++;
                }
                j--;
            }
            j++;
        }
        ans = Math.max(ans, j - i); //注意这里千万不要忘记更新
        return ans;
    }
}
~~~
