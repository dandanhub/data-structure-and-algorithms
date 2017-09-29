## 288. Unique Word Abbreviation

An abbreviation of a word follows the form <first letter><number><last letter>. Below are some examples of word abbreviations:
~~~
a) it                      --> it    (no abbreviation)

     1
b) d|o|g                   --> d1g

              1    1  1
     1---5----0----5--8
c) i|nternationalizatio|n  --> i18n

              1
     1---5----0
d) l|ocalizatio|n          --> l10n
~~~
Assume you have a dictionary and given a word, find whether its abbreviation is unique in the dictionary. A word's abbreviation is unique if no other word from the dictionary has the same abbreviation.

Example:
~~~
Given dictionary = [ "deer", "door", "cake", "card" ]

isUnique("dear") ->
false

isUnique("cart") ->
true

isUnique("cane") ->
false

isUnique("make") ->
true
~~~

#### Solution
题目比较简单 <br>
Attempt: 1
~~~
public class ValidWordAbbr {
    Map<String, Set<String>> map;

    public ValidWordAbbr(String[] dictionary) {
        map = new HashMap<String, Set<String>>();
        for (String str : dictionary) {
            String abbr = genArrr(str);
            Set<String> set = map.getOrDefault(abbr, new HashSet<String>());
            set.add(str);
            map.put(abbr, set);
        }
    }

    public boolean isUnique(String word) {
        String abbr = genArrr(word);
        if (!map.containsKey(abbr)) return true;
        if (map.containsKey(abbr)) {
            Set<String> set = map.get(abbr);
            if (set.size() == 1 && set.contains(word)) return true;
        }
        return false;
    }

    private String genArrr(String str) {
        if (str == null || str.length() <= 2) return str;
        StringBuilder sb = new StringBuilder();
        sb.append(str.charAt(0)).append(str.length() - 2)
          .append(str.charAt(str.length() - 1));
        return sb.toString();
    }
}

/**
 * Your ValidWordAbbr object will be instantiated and called as such:
 * ValidWordAbbr obj = new ValidWordAbbr(dictionary);
 * boolean param_1 = obj.isUnique(word);
 */
~~~

## 320. Generalized Abbreviation
Write a function to generate the generalized abbreviations of a word.

Example: <br>
Given word = "word", return the following list (order does not matter):
~~~
["word", "1ord", "w1rd", "wo1d", "wor1", "2rd", "w2d", "wo2", "1o1d", "1or1", "w1r1", "1o2", "2r1", "3d", "w3", "4"]
~~~

#### Solution
Backtrack, think like subset problem
~~~
class Solution {
    public List<String> generateAbbreviations(String word) {
        List<String> ans = new ArrayList<String>();
        helper(ans, word, 0, new StringBuilder(), 0);
        return ans;
    }

    private void helper(List<String> ans, String word, int pos, StringBuilder sb, int count) {
        int len = sb.length();
        if (pos == word.length()) {
            if (count > 0) sb.append(count);
            ans.add(sb.toString());
        }
        else {
            helper(ans, word, pos + 1, sb, count + 1);

            if (count > 0) sb.append(count);
            sb.append(word.charAt(pos));
            helper(ans, word, pos + 1, sb, 0);
        }
        sb.setLength(len);
    }
}
~~~

Bit manipulation
~~~
class Solution {
    public List<String> generateAbbreviations(String word) {
        List<String> ans = new ArrayList<String>();
        int count = 1 << word.length();
        for (int i = 0; i < count; i++) {
           ans.add(generateAbbr(word, i));
        }
        return ans;
    }

    private String generateAbbr(String word, int count) {
        int k = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++, count >>= 1) {
            if ((1 & count) == 1) {
                k++;
            }
            else {
                if (k > 0) {
                    sb.append(k);
                }
                sb.append(word.charAt(i));
                k = 0;
            }
        }

        if (k > 0) {
            sb.append(k);
        }
        return sb.toString();
    }
}
~~~
