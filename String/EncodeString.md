## 271. Encode and Decode Strings

Design an algorithm to encode a list of strings to a string. The encoded string is then sent over the network and is decoded back to the original list of strings.

Machine 1 (sender) has the function:
~~~
string encode(vector<string> strs) {
  // ... your code
  return encoded_string;
}
~~~

Machine 2 (receiver) has the function:
~~~
vector<string> decode(string s) {
  //... your code
  return strs;
}
~~~
So Machine 1 does:
~~~
string encoded_string = encode(strs);
~~~
and Machine 2 does:
~~~
vector<string> strs2 = decode(encoded_string);
~~~
strs2 in Machine 2 should be the same as strs in Machine 1.

Implement the encode and decode methods.

Note:
- The string may contain any possible characters out of 256 valid ascii characters. Your algorithm should be generalized enough to work on any possible characters.
- Do not use class member/global/static variables to store states. Your encode and decode algorithms should be stateless.
- Do not rely on any library method such as eval or serialize methods. You should implement your own encode/decode algorithm.

#### Solution
1. append长度来控制每次读入的个数
2. append长度后面append一个特殊字符用来标注长度的结束

~~~
public class Codec {

    // Encodes a list of strings to a single string.
    public String encode(List<String> strs) {
        if (strs == null) return null;
        if (strs.size() == 0) return "";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strs.size(); i++) {
            String str = strs.get(i);
            sb.append(str.length()).append("\\").append(str);
        }
        return sb.toString();
    }

    // Decodes a single string to a list of strings.
    public List<String> decode(String s) {
        if (s == null) return null;
        if (s.length() == 0) return new ArrayList<String>();

        List<String> list = new ArrayList<String>();
        int i = 0;
        while (i < s.length()) {
            int index = s.indexOf("\\", i);
            int len = Integer.valueOf(s.substring(i, index));
            String str = s.substring(index + 1, index + len + 1);
            list.add(str);
            i = index + len + 1;
        }
        return list;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.decode(codec.encode(strs));
~~~

## 394. Decode String
Given an encoded string, return it's decoded string.

The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is being repeated exactly k times. Note that k is guaranteed to be a positive integer.

You may assume that the input string is always valid; No extra white spaces, square brackets are well-formed, etc.

Furthermore, you may assume that the original data does not contain any digits and that digits are only for those repeat numbers, k. For example, there won't be input like 3a or 2[4].

Examples:
~~~
s = "3[a]2[bc]", return "aaabcbc".
s = "3[a2[c]]", return "accaccacc".
s = "2[abc]3[cd]ef", return "abcabccdcdcdef".
~~~

#### Solution
两个栈，一个用来保存出现的次数，一个用来保存字符（串）（can we do better?）

~~~
public class Solution {
    public String decodeString(String s) {

        Stack<Integer> numStack = new Stack<Integer>();
        Stack<String> strStack = new Stack<String>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (Character.isDigit(ch)) {
                int num = 0;
                while (i < s.length() && Character.isDigit(s.charAt(i))) {
                    num = 10 * num + s.charAt(i) - 48;
                    i++;
                }
                i--;
                numStack.push(num);
            }
            else if (ch == ']') {
                StringBuilder sb = new StringBuilder();
                while (!strStack.isEmpty() && !strStack.peek().equals("[")) {
                    sb.insert(0, strStack.pop());
                }
                strStack.pop(); // pop [ out
                int count = numStack.pop();
                StringBuilder sub = new StringBuilder();
                while (count > 0) {
                    sub.append(sb.toString());
                    count--;
                }
                strStack.push(sub.toString());
            }
            else {
                strStack.push(s.substring(i, i + 1));
            }
        }

        StringBuilder ans = new StringBuilder();
        while (!strStack.isEmpty()) {
            ans.insert(0, strStack.pop());
        }
        return ans.toString();
    }
}
~~~
