## Text Justification

~~~
Google， Linkedin，Airbnb今年都被问过。。。。
L和A的follow up是可以加单词的Hypen，把一个词可以分行写。

Text Justification DP
https://youtu.be/ENyox7kNKeY?t=17m8s
~~~

####

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

## 482. License Key Formatting (Easy) (G)
Now you are given a string S, which represents a software license key which we would like to format. The string S is composed of alphanumerical characters and dashes. The dashes split the alphanumerical characters within the string into groups. (i.e. if there are M dashes, the string is split into M+1 groups). The dashes in the given string are possibly misplaced.

We want each group of characters to be of length K (except for possibly the first group, which could be shorter, but still must contain at least one character). To satisfy this requirement, we will reinsert dashes. Additionally, all the lower case letters in the string must be converted to upper case.

So, you are given a non-empty string S, representing a license key to format, and an integer K. And you need to return the license key formatted according to the description above.

Example 1:
~~~
Input: S = "2-4A0r7-4k", K = 4

Output: "24A0-R74K"

Explanation: The string S has been split into two parts, each part has 4 characters.
~~~

Example 2:
~~~
Input: S = "2-4A0r7-4k", K = 3

Output: "24-A0R-74K"

Explanation: The string S has been split into three parts, each part has 3 characters except the first part as it could be shorter as said above.
~~~

Note:
The length of string S will not exceed 12,000, and K is a positive integer.
String S consists only of alphanumerical characters (a-z and/or A-Z and/or 0-9) and dashes(-).
String S is non-empty.

#### Solution
Java String替换字符 s.replacec("-","");
~~~
public class Solution {
    public String licenseKeyFormatting(String S, int K) {
        // delete - from input
        String str = S.replace("-","").toUpperCase();
        StringBuilder sb = new StringBuilder(str).reverse();
        StringBuilder res = new StringBuilder();
        // group char to be of length k, from right to left
        for (int i = 0; i < sb.length(); i += K) {
            res.append(sb.substring(i, Math.min(i + K, sb.length())));
            res.append('-');
        }

        // remove the last '-'
        if (res.length() > 0) res.deleteCharAt(res.length() - 1);
        return res.reverse().toString();
    }
}
~~~
