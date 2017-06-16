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
