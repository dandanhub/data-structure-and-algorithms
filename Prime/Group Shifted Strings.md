## 249. Group Shifted Strings
Given a string, we can "shift" each of its letter to its successive letter, for example: "abc" -> "bcd". We can keep "shifting" which forms the sequence:

"abc" -> "bcd" -> ... -> "xyz"
Given a list of strings which contains only lowercase alphabets, group all strings that belong to the same shifting sequence.

For example, given: ["abc", "bcd", "acef", "xyz", "az", "ba", "a", "z"],
A solution is:

[
  ["abc","bcd","xyz"],
  ["az","ba"],
  ["acef"],
  ["a","z"]
]

#### Solution
注意边界情况例如： [["abc","am"]]
~~~
class Solution {
    public List<List<String>> groupStrings(String[] strings) {
        List<List<String>> ans = new ArrayList<List<String>>();
        if (strings == null || strings.length == 0) return ans;

        Map<String, List<String>> map = new HashMap<String, List<String>>();
        for (int i = 0; i < strings.length; i++) {
            String hash = generateHash(strings[i]);
            List<String> list = map.getOrDefault(hash, new ArrayList<String>());
            list.add(strings[i]);
            map.put(hash, list);
        }

        for (List<String> list : map.values()) {
            ans.add(list);
        }
        return ans;
    }

    private String generateHash(String str) {
        if (str == null) return null;
        if (str.length() == 0) return "";

        char base = str.charAt(0);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            sb.append((str.charAt(i) - base + 26) % 26).append("#");
        }
        return sb.toString();
    }
}
~~~
