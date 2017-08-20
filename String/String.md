## 151. Reverse Words in a String (Tricky)
Given an input string, reverse the string word by word.

For example,
Given s = "the sky is blue",
return "blue is sky the".

Update (2015-02-12):
For C programmers: Try to solve it in-place in O(1) space.

click to show clarification.

Clarification:
- What constitutes a word?
  - A sequence of non-space characters constitutes a word.
- Could the input string contain leading or trailing spaces?
  - Yes. However, your reversed string should not contain leading or trailing spaces.
- How about multiple spaces between two words?
  - Reduce them to a single space in the reversed string.

#### Solution
Method 1: O(n) space, O(n) time
1. str.split("\\s+") 如果是根据空格split，如果要匹配多个空格字符，记得要用"\\s"
2. 一定要注意tokens.length - 1 - i，从最后一位往前，注意是tokens.length - 1开始
~~~
public class Solution {
    public String reverseWords(String s) {
        if (s == null || s.length() == 0) return s;

        String[] tokens = s.trim().split("\\s+");

        for (int i = 0; i < tokens.length / 2; i++) {
            swap(tokens, i, tokens.length - 1 - i);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tokens.length; i++) {
            sb.append(tokens[i]).append(" ");
        }
        return sb.toString().trim();
    }

    private void swap(String[] tokens, int i, int j) {
        String temp = tokens[i];
        tokens[i] = tokens[j];
        tokens[j] = temp;
    }
}
~~~

Follow-up : How to do it in-place
1. First reverse the whole string
2. and then reverse word by word

## 520. Detect Capital
Given a word, you need to judge whether the usage of capitals in it is right or not.

We define the usage of capitals in a word to be right when one of the following cases holds:

All letters in this word are capitals, like "USA".
All letters in this word are not capitals, like "leetcode".
Only the first letter in this word is capital if it has more than one letter, like "Google".
Otherwise, we define that this word doesn't use capitals in a right way.
Example 1:
~~~
Input: "USA"
Output: True
~~~

Example 2:
~~~
Input: "FlaG"
Output: False
~~~

Note: The input will be a non-empty word consisting of uppercase and lowercase latin letters.

#### Solution
Method 1: Use boolean verbose version
~~~
public class Solution {
    public boolean detectCapitalUse(String word) {
        if (word.length() <= 1) return true;

        boolean init = false;
        if (Character.isUpperCase(word.charAt(0))) {
            init = true;
        }

        boolean upper = false;
        if (Character.isUpperCase(word.charAt(1))) {
            if (!init) return false;
            upper = true;
        }

        for (int i = 2; i < word.length(); i++) {
            char ch = word.charAt(i);
            if ((Character.isUpperCase(ch) && !upper)
                || (!Character.isUpperCase(ch) && upper)) {
                return false;
            }
        }

        return true;
    }
}
~~~

Method 2: count the number of uppercase character
~~~
public class Solution {
    public boolean detectCapitalUse(String word) {
        if (word.length() <= 1) return true;

        int count = 0;
        for (int i = 0; i < word.length(); i++) {
            if (Character.isUpperCase(word.charAt(i))) {
                count++;
            }
        }

        return count == 0 || count == word.length()
            || (count == 1 && Character.isUpperCase(word.charAt(0)));
    }
}
~~~
