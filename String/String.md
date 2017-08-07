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
