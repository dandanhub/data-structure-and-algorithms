## 392. Is Subsequence

Given a string s and a string t, check if s is subsequence of t.

You may assume that there is only lower case English letters in both s and t. t is potentially a very long (length ~= 500,000) string, and s is a short string (<=100).

A subsequence of a string is a new string which is formed from the original string by deleting some (can be none) of the characters without disturbing the relative positions of the remaining characters. (ie, "ace" is a subsequence of "abcde" while "aec" is not).

Example 1:
s = "abc", t = "ahbgdc"

Return true.

Example 2:
s = "axc", t = "ahbgdc"

Return false.

Follow up:
If there are lots of incoming S, say S1, S2, ... , Sk where k >= 1B, and you want to check one by one to see if T has its subsequence. In this scenario, how would you change your code?

#### Solution
Naiive:
~~~
public class Solution {
    public boolean isSubsequence(String s, String t) {
        if (s.length() > t.length()) return false;
        int i = 0;
        int j = 0;
        while (i < s.length()) {
            while (j < t.length() && s.charAt(i) != t.charAt(j)) {
                j++;
            }
            if (j < t.length() && s.charAt(i) == t.charAt(j)) {
                i++;
                j++;
            }
            else {
                break;
            }
        }
        return i == s.length();
    }
}
~~~

Follow-up
~~~
public class Solution {
    public boolean isSubsequence(String s, String t) {
        if (s.length() > t.length()) return false;

        Map<Character, List<Integer>> map = new HashMap<Character, List<Integer>>();
        for (int i = 0; i < t.length(); i++) {
            char ch = t.charAt(i);
            List<Integer> list = map.getOrDefault(ch, new ArrayList<Integer>());
            list.add(i);
            map.put(ch, list);
        }

        int lastIdx = -1;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (!map.containsKey(ch)) return false;
            List<Integer> list = map.get(ch);
            int index = binarySearch(list, lastIdx);
            if (index < lastIdx) return false;
            lastIdx = index;
        }
        return true;
    }

    private int binarySearch(List<Integer> list, int target) {
        int l = 0;
        int r = list.size() - 1;
        while (l < r) {
            int m = (r - l) / 2 + l;
            if (list.get(m) > target) {
                r = m;
            }
            else {
                l = m + 1;
            }
        }
        return list.get(r);
    }
}
~~~

## 522. Longest Uncommon Subsequence
Given a list of strings, you need to find the longest uncommon subsequence among them. The longest uncommon subsequence is defined as the longest subsequence of one of these strings and this subsequence should not be any subsequence of the other strings.

A subsequence is a sequence that can be derived from one sequence by deleting some characters without changing the order of the remaining elements. Trivially, any string is a subsequence of itself and an empty string is a subsequence of any string.

The input will be a list of strings, and the output needs to be the length of the longest uncommon subsequence. If the longest uncommon subsequence doesn't exist, return -1.

Example 1:
~~~
Input: "aba", "cdc", "eae"
Output: 3
~~~

Note:

All the given strings' lengths will not exceed 10.
The length of the given list will be in the range of [2, 50].

#### Solution
Method 1: O(xn​^2). where n is the number of strings and x is the average length of the strings.
~~~
public class Solution {
    public int findLUSlength(String[] strs) {
        if (strs == null || strs.length == 0) return 0;

        Arrays.sort(strs, new Comparator<String>() {
            public int compare(String s1, String s2) {
                return Integer.compare(s2.length(), s1.length());
            }
        });

        for (int i = 0; i < strs.length; i++) {
            boolean isValid = true;
            for (int j = 0; j < strs.length; j++) {
                if (i == j) continue;
                if (strs[j].length() < strs[i].length()) break;
                if (isSubsequence(strs[i], strs[j])) {
                    isValid = false;
                    break;
                }
            }
            if (isValid) return strs[i].length();
        }

        return -1;
    }


    private boolean isSubsequence(String s1, String s2) {
        int j = 0;
        for (int i = 0; i < s2.length() && j < s1.length(); i++) {
            if (s1.charAt(j) == s2.charAt(i)) {
                j++;
            }
        }

        return j == s1.length();
    }
}
~~~

Method 2: generate subsequence of all string, O(n2^x)
~~~
public class Solution {
    public int findLUSlength(String[] strs) {
        if (strs == null || strs.length == 0) return 0;

        Map<String, Integer> map = new HashMap<String, Integer>();
        for (int i = 0; i < strs.length; i++) {
            for (String str : genSubsequence(strs[i])) {
                map.put(str, map.getOrDefault(str, 0) + 1);
            }
        }

        int max = -1;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                max = Math.max(entry.getKey().length(), max);
            }
        }
        return max;
    }

    private Set<String> genSubsequence(String s) {
        if (s == null) return null;
        if (s.length() == 0) {
            Set<String> set = new HashSet<String>();
            set.add("");
            return set;
        }

        Set<String> set = new HashSet<String>();
        for (String str : genSubsequence(s.substring(1))) {
            set.add(str);
            StringBuilder sb = new StringBuilder();
            sb.append(s.charAt(0)).append(str);
            set.add(sb.toString());
        }
        return set;
    }

}
~~~

## 524. Longest Word in Dictionary through Deleting
Given a string and a string dictionary, find the longest string in the dictionary that can be formed by deleting some characters of the given string. If there are more than one possible results, return the longest word with the smallest lexicographical order. If there is no possible result, return the empty string.

Example 1:
~~~
Input:
s = "abpcplea", d = ["ale","apple","monkey","plea"]

Output:
"apple"
~~~

Example 2:
~~~
Input:
s = "abpcplea", d = ["a","b","c"]

Output:
"a"
~~~

Note:
1. All the strings in the input will only contain lower-case letters.
2. The size of the dictionary won't exceed 1,000.
3. The length of all the strings in the input won't exceed 1,000.

#### Solution
Method 1: Naiive way <br>
n the number of string in dict, x the avg length of string
1. sort O(nxlgn)
2. check subsequence O(nx)
space complexity Collections.sort "Temporary storage requirements vary from a small constant for nearly sorted input arrays to n/2 object references for randomly ordered input arrays." <br>
~~~
public class Solution {
    public String findLongestWord(String s, List<String> d) {
        if (s == null || s.length() == 0 || d == null || d.size() == 0) return "";
        Collections.sort(d, new Comparator<String>() {
            public int compare(String s1, String s2) {
                if (s1.length() == s2.length()) return s1.compareTo(s2);
                return Integer.compare(s2.length(), s1.length());
            }
        });

        for (int i = 0; i < d.size(); i++) {
            if (isSubsequence(d.get(i), s)) {
                return d.get(i);
            }
        }

        return "";
    }

    private boolean isSubsequence(String t, String s) {
        int j = 0;
        for (int i = 0; i < s.length() && j < t.length(); i++) {
            if (s.charAt(i) == t.charAt(j)) j++;
        }
        return j == t.length();
    }
}
~~~

Method 2: Without sorting
~~~
public class Solution {
    public String findLongestWord(String s, List<String> d) {
        if (s == null || s.length() == 0 || d == null || d.size() == 0) return "";

        String ans = "";
        for (int i = 0; i < d.size(); i++) {
            String str = d.get(i);
            if (isSubsequence(str, s)) {
                if (str.length() > ans.length() ||
                    (str.length() == ans.length() && str.compareTo(ans) < 0)) {
                    ans = str;
                }
            }
        }

        return ans;
    }

    private boolean isSubsequence(String t, String s) {
        int j = 0;
        for (int i = 0; i < s.length() && j < t.length(); i++) {
            if (s.charAt(i) == t.charAt(j)) j++;
        }
        return j == t.length();
    }
}
~~~
