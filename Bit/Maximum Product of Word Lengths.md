## 318. Maximum Product of Word Lengths
Given a string array words, find the maximum value of length(word[i]) * length(word[j]) where the two words do not share common letters. You may assume that each word will contain only lower case letters. If no such two words exist, return 0.

Example 1:
~~~
Given ["abcw", "baz", "foo", "bar", "xtfn", "abcdef"]
Return 16
The two words can be "abcw", "xtfn".
~~~

Example 2:
~~~
Given ["a", "ab", "abc", "d", "cd", "bcd", "abcd"]
Return 4
The two words can be "ab", "cd".
~~~

Example 3:
~~~
Given ["a", "aa", "aaa", "aaaa"]
Return 0
No such pair of words.
~~~

#### Solution
Method 1: Intuitive Method 115 ms
~~~
class Solution {
    public int maxProduct(String[] words) {
        if (words == null || words.length == 0) return 0;

        int ans = 0;
        for (int i = 0; i < words.length; i++) {
            int[] dict = new int[26];
            for (int k = 0; k < words[i].length(); k++) {
                dict[words[i].charAt(k) - 'a']++;
            }
            for (int j = i + 1; j < words.length; j++) {
                boolean valid = true;
                for (int k = 0; k < words[j].length(); k++) {
                    if (dict[words[j].charAt(k) - 'a'] != 0) {
                        valid = false;
                        break;
                    }
                }
                if (valid) ans = Math.max(ans, words[i].length() * words[j].length());
            }
        }
        return ans;
    }
}
~~~

在比较两个String是否包含相同字符的时候，由于只有26个英文字符，所以可以使用bit manipulation, 24 ms
~~~
class Solution {
    public int maxProduct(String[] words) {
        if (words == null || words.length == 0) return 0;

        int len = words.length;
        int[] val = new int[len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < words[i].length(); j++) {
                val[i] |= 1 << (words[i].charAt(j) - 'a');
            }
        }

        int ans = 0;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if ((val[i] & val[j]) == 0) {
                    ans = Math.max(ans, words[i].length() * words[j].length());
                }
            }
        }
        return ans;
    }
}
~~~
