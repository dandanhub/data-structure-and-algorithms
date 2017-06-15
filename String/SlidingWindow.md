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
