## 14. Longest Common Prefix
Write a function to find the longest common prefix string amongst an array of strings.

#### Solution
Method 1: Vertical
~~~
public class Solution {
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) return "";

        for (int i = 0; i < strs[0].length(); i++) {
            char ch = strs[0].charAt(i);
            for (int j = 1; j < strs.length; j++) {
                if (i >= strs[j].length() || strs[j].charAt(i) != ch) {
                    return strs[0].substring(0, i);
                }
            }
        }

        return strs[0];
    }
}
~~~

Method 2: [Binary Search](https://leetcode.com/articles/longest-common-prefix/#approach-4-binary-search)
