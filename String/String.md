# Stack
## 388. Longest Absolute File Path (Medium) * (Google)

Suppose we abstract our file system by a string in the following manner:

The string "dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext" represents:
~~~
dir
    subdir1
    subdir2
        file.ext
~~~
The directory dir contains an empty sub-directory subdir1 and a sub-directory subdir2 containing a file file.ext.

The string "dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext" represents:
~~~
dir
    subdir1
        file1.ext
        subsubdir1
    subdir2
        subsubdir2
            file2.ext
~~~
The directory dir contains two sub-directories subdir1 and subdir2. subdir1 contains a file file1.ext and an empty second-level sub-directory subsubdir1. subdir2 contains a second-level sub-directory subsubdir2 containing a file file2.ext.

We are interested in finding the longest (number of characters) absolute path to a file within our file system. For example, in the second example above, the longest absolute path is "dir/subdir2/subsubdir2/file2.ext", and its length is 32 (not including the double quotes).

Given a string representing the file system in the above format, return the length of the longest absolute path to file in the abstracted file system. If there is no file in the system, return 0.

Note:
The name of a file contains at least a . and an extension.
The name of a directory or sub-directory will not contain a ..
Time complexity required: O(n) where n is the size of the input string.

Notice that a/aa/aaa/file1.txt is not the longest file path, if there is another path aaaaaaaaaaaaaaaaaaaaa/sth.png.

#### Solution
维护一个stack根据规则出栈入栈，更新最大长度
1. 这题有个坑爹的地方就是题目没有说明四个空格也要当做"\t"来处理，因为这个限制所以在最开始split的时候要input.spilt("\n")，而不能够用input.spilt("\n\t").
2. 然后就是"\t"长度记为1

Verbose Solution
~~~
public class Solution {
    /*
    * This problem is not well-defined. It should state that 4-space is considered as a TAB under certain situation.
    */
    public int lengthLongestPath(String input) {
        if (input == null || input.length() == 0) return 0;
        Stack<String> stack = new Stack<String>();

        // split the input string
        String[] tokens = input.split("\n"); // can't split by "\n\t"
        if (tokens == null || tokens.length == 0) return 0;

        // System.out.println(tokens.length);
        // for (int i = 0; i < tokens.length; i++) System.out.println(tokens[i].length() + " " + tokens[i]);

        stack.push(tokens[0]);
        int len = 0;
        int k = 1;
        int curlen = tokens[0].length();
        if (tokens[0].contains(".")) len = Math.max(len, curlen);
        for (int i = 1; i < tokens.length; i++) {
            // get the number of "\t"
            int count = 0;
            for (int j = 0; j < tokens[i].length(); j++) {
                // "\t" is one character
                if (tokens[i].substring(j, j + 1).equals("\t")) {
                    count++;
                }
                else break;
            }

            while (k > count) {
                String str = stack.pop();
                k--;
                curlen -= str.length() - k + 1;
            }

            stack.push(tokens[i]);
            curlen += tokens[i].length() - k + 1;
            k++;

            if (tokens[i].contains(".")) len = Math.max(len, curlen);
        }

        return len;
    }
}
~~~

Concise Solution
~~~
public class Solution {
    public int lengthLongestPath(String input) {
        if (input == null || input.length() == 0) return 0;
        // System.out.println(input.length());
        Stack<Integer> stack = new Stack<Integer>(); // stack当前路径的长度
        String[] tokens = input.split("\\n");
        stack.push(0);
        int maxLen = 0;
        for (String str : tokens) {
            int level = str.lastIndexOf("\t") + 1; // 在获取一个token有多少个\t的时候，用str.lastIndexOf("\t") + 1
            while (level + 1 < stack.size()) stack.pop();
            int len = stack.peek() + str.length() - level + 1;
            if (level == 0) len--;
            // System.out.println(str + " " + str.length() + " " + level + " " +len);
            stack.push(len);
            if (str.contains(".")) {
                maxLen = Math.max(maxLen, len);
            }
        }
        return maxLen;
    }
}
~~~

# DP
## 10. Regular Expression Matching
Implement regular expression matching with support for '.' and '\*'.

~~~
'.' Matches any single character.
'*' Matches zero or more of the preceding element.

The matching should cover the entire input string (not partial).

The function prototype should be:
bool isMatch(const char *s, const char *p)

Some examples:
isMatch("aa","a") → false
isMatch("aa","aa") → true
isMatch("aaa","aa") → false
isMatch("aa", "a*") → true
isMatch("aa", ".*") → true
isMatch("ab", ".*") → true
isMatch("aab", "c*a*b") → true
~~~

#### Solution
Two dimensional DP, boolean dp[i][j] means whether substring of s(0, i) matches p(0, j).
1. if s[i] == p[j] or p[j] == '.', dp[i][j] = dp[i-1][j-1]
2. else if p[j] == '\*'
- if s[i] != p[j-1], dp[i][j] = dp[i][j-2] (match a* zero time)
- else dp[i][j] = dp[i][j-2] (match zero time) || dp[i][j-1] (match one time) || dp[i-1][j] (match multi times)

~~~
public class Solution {
    public boolean isMatch(String s, String p) {
        int slen = s.length();
        int plen = p.length();
        boolean[][] dp = new boolean[slen + 1][plen + 1];
        dp[0][0] = true;

        // 处理 a*
        // b
        // .*b*c 这种初始化 dp[0][1] = false, dp[0][2] = true
        // bb* 直接返回
        // b*b
        for (int j = 0; j < plen - 1; j = j + 2) {
            int next = j + 1;
            if (p.charAt(next) == '*') {
                dp[0][j + 1] = false;
                dp[0][next + 1] = true;
            }
            else {
                break;
            }
        }

        for (int i = 1; i < slen + 1; i++) {
            for (int j = 1; j < plen + 1; j++) {
                if (s.charAt(i - 1) == p.charAt(j - 1)) {
                    dp[i][j] = dp[i-1][j-1];
                }
                else if (p.charAt(j - 1) == '.') {
                    dp[i][j] = dp[i-1][j-1];
                }
                else if (p.charAt(j - 1) == '*') {
                    // "aaaac"
                    // "a*."
                    if (s.charAt(i - 1) == p.charAt(j - 2) || p.charAt(j - 2) == '.') {
                        // dp[i][j] = dp[i][j-2] || dp[i][j-1] || dp[i-1][j];
                        dp[i][j] = dp[i][j-2] || dp[i-1][j];
                    }
                    else { // a* 匹配0次
                        dp[i][j] = dp[i][j-2];
                    }
                }
            }
        }
        return dp[slen][plen];
    }
}
~~~

## 44. Wildcard Matching
Implement wildcard pattern matching with support for '?' and '\*'.

~~~
'?' Matches any single character.
'*' Matches any sequence of characters (including the empty sequence).

The matching should cover the entire input string (not partial).

The function prototype should be:
bool isMatch(const char *s, const char *p)

Some examples:
isMatch("aa","a") → false
isMatch("aa","aa") → true
isMatch("aaa","aa") → false
isMatch("aa", "*") → true
isMatch("aa", "a*") → true
isMatch("ab", "?*") → true
isMatch("aab", "c*a*b") → false
~~~

#### Solution
1. if s[i] == p[j] or p[j] == '?', dp[i][j] = dp[i-1][j-1]
2. else if p[j] == '\*', dp[i][j] = dp[i][j-1] || dp[i-1][j]

~~~
public class Solution {
    public boolean isMatch(String s, String p) {
        // edge cases
        if (s == null || p == null) return false;

        int sl = s.length();
        int pl = p.length();
        boolean[][] dp = new boolean[sl + 1][pl + 1];
        dp[0][0] = true;
        for (int i = 1; i < pl + 1; i++) {
            if (p.charAt(i - 1) == '*') dp[0][i] = true;
            else break;
        }

        for (int i = 1; i < sl + 1; i++) { // bug
            for (int j = 1; j < pl + 1; j++) { // bug
                if (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '?') {
                    dp[i][j] = dp[i - 1][j - 1];
                }
                else if (p.charAt(j - 1) == '*') {
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                }
            }
        }

        return dp[sl][pl];
    }
}
~~~


# Misc
## 293. Flip Game (Easy)
You are playing the following Flip Game with your friend: Given a string that contains only these two characters: + and -, you and your friend take turns to flip two consecutive "++" into "--". The game ends when a person can no longer make a move and therefore the other person will be the winner.

Write a function to compute all possible states of the string after one valid move.

For example, given s = "++++", after one move, it may become one of the following states:
~~~~
[
  "--++",
  "+--+",
  "++--"
]
~~~~
If there is no valid move, return an empty list [].

#### Solution
Simply iterate the string.

~~~
public List<String> generatePossibleNextMoves(String s) {
      List<String> list = new ArrayList<String>();
      if (s == null || s.length() < 2) {
          return list;
      }
      for (int i = 0; i < s.length() - 1; i++) {
          if (s.substring(i, i + 2).equals("++")) {
              StringBuilder sb = new StringBuilder();
              sb.append(s.substring(0, i))
                .append("--")
                .append(s.substring(i + 2));
              list.add(sb.toString());
          }
      }
      return list;
  }
~~~

## 165. Compare Version Numbers
Compare two version numbers version1 and version2.
If version1 > version2 return 1, if version1 < version2 return -1, otherwise return 0.

You may assume that the version strings are non-empty and contain only digits and the . character.
The . character does not represent a decimal point and is used to separate number sequences.
For instance, 2.5 is not "two and a half" or "half way to version three", it is the fifth second-level revision of the second first-level revision.

Here is an example of version numbers ordering:
~~~~
0.1 < 1.1 < 1.2 < 13.37
~~~~

#### Solution
WRONG: my first idea is to compare v1 and v2 at their min length; and then handle the one that is longer. However, it becomes trivial to handle corner case, like "1.0" v.s. "1", and "01" v.s. "1".

~~~~
for (int i = 0; i < Math.min(tokens1.length, tokens2.length); i++) {
  int v1 = Integer.parseInt(tokens1[i]);
  int v2 = Integer.parseInt(tokens2[i]);
  if (v1 != v2) {
    return v1 > v2 ? 1 : -1;
    }
}
~~~~

Think from the other side, compare at their max length, for the one which is shorter, simply set it to 0.
