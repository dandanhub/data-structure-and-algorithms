## 242. Valid Anagram (+)

Given two strings s and t, write a function to determine if t is an anagram of s.

For example,
s = "anagram", t = "nagaram", return true.
s = "rat", t = "car", return false.

Note:
You may assume the string contains only lowercase alphabets.

Follow up:
What if the inputs contain unicode characters? How would you adapt your solution to such case?

#### solution
Attempts: 1
~~~
public class Solution {
    public boolean isAnagram(String s, String t) {
        if (s == null || t == null || s.length() != t.length()) return false;

        int[] dict = new int[256];
        for (int i = 0; i < s.length(); i++) {
            dict[s.charAt(i)]++;
        }

        for (int i = 0; i < t.length(); i++) {
            dict[t.charAt(i)]--;
        }

        for (int i = 0; i < dict.length; i++) {
            if (dict[i] != 0) return false;
        }
        return true;
    }
}
~~~

## 49. Group Anagrams

Given an array of strings, group anagrams together.

For example, given: ["eat", "tea", "tan", "ate", "nat", "bat"],
Return:

[
  ["ate", "eat","tea"],
  ["nat","tan"],
  ["bat"]
]
Note: All inputs will be in lower-case.

#### Solution
如何根据anagrams的规则构建hashmap是问题的关键
~~~
public class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> ans = new ArrayList<List<String>>();
        if (strs == null || strs.length == 0) return ans;

        HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
        for (int i = 0; i < strs.length; i++) {
            char[] chars = strs[i].toCharArray();
            Arrays.sort(chars);
            String sortedStr = String.valueOf(chars);
            if (!map.containsKey(sortedStr)) {
                map.put(sortedStr, new ArrayList<String>());
            }
            map.get(sortedStr).add(strs[i]);
        }

        ans.addAll(map.values());
        return ans;
    }
}
~~~
