## 385. Mini Parser
Given a nested list of integers represented as a string, implement a parser to deserialize it.

Each element is either an integer, or a list -- whose elements may also be integers or other lists.

Note: You may assume that the string is well-formed:

String is non-empty.
String does not contain white spaces.
String contains only digits 0-9, [, - ,, ].
Example 1:
~~~
Given s = "324",

You should return a NestedInteger object which contains a single integer 324.
~~~

Example 2:
~~~
Given s = "[123,[456,[789]]]",

Return a NestedInteger object containing a nested list with 2 elements:

1. An integer containing value 123.
2. A nested list containing two elements:
    i.  An integer containing value 456.
    ii. A nested list with one element:
         a. An integer containing value 789.
~~~

#### Solution

~~~
/**
 * // This is the interface that allows for creating nested lists.
 * // You should not implement it, or speculate about its implementation
 * public interface NestedInteger {
 *     // Constructor initializes an empty nested list.
 *     public NestedInteger();
 *
 *     // Constructor initializes a single integer.
 *     public NestedInteger(int value);
 *
 *     // @return true if this NestedInteger holds a single integer, rather than a nested list.
 *     public boolean isInteger();
 *
 *     // @return the single integer that this NestedInteger holds, if it holds a single integer
 *     // Return null if this NestedInteger holds a nested list
 *     public Integer getInteger();
 *
 *     // Set this NestedInteger to hold a single integer.
 *     public void setInteger(int value);
 *
 *     // Set this NestedInteger to hold a nested list and adds a nested integer to it.
 *     public void add(NestedInteger ni);
 *
 *     // @return the nested list that this NestedInteger holds, if it holds a nested list
 *     // Return null if this NestedInteger holds a single integer
 *     public List<NestedInteger> getList();
 * }
 */
public class Solution {
    public NestedInteger deserialize(String s) {
        if (!s.startsWith("[")) return new NestedInteger(Integer.valueOf(s));

        Stack<NestedInteger> stack = new Stack<NestedInteger>();
        NestedInteger res = new NestedInteger();
        stack.push(res);
        int sign = 1;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == '[') {
                NestedInteger ni = new NestedInteger();
                stack.peek().add(ni);
                stack.push(ni);
            }
            else if (s.charAt(i) == '-') {
                sign = -1;
            }
            else if (Character.isDigit(s.charAt(i))) {
                int num = 0;
                while (i < s.length() && Character.isDigit(s.charAt(i))) {
                    num = 10 * num + s.charAt(i) - 48;
                    i++;
                }

                stack.peek().add(new NestedInteger(sign * num));
                sign = 1;
                i--;
            }
            else if (s.charAt(i) == ']') {
                stack.pop();
            }
        }

        return res;
    }
}
~~~

## 65. Valid Number
Validate if a given string is numeric.

Some examples:
"0" => true
" 0.1 " => true
"abc" => false
"1 a" => false
"2e10" => true
Note: It is intended for the problem statement to be ambiguous. You should gather all requirements up front before implementing one.

#### Solution
Brute Force <br>
测试用例
~~~
"3"
"03"
"3 0 "
".03"
"-.003"
"+.003"
"+.00.3"
"+.00+3"
".00-3"
"0"
" 0.1 "
"abc"
"1 a"
"2e10"
"e210"
".e210"
"2.e210"
"2.0e210"
"2.0e21e0"
"."
"+."
"+2e"
"+2e."
"+2e.3"
"+2e4.3"
"--"
"-."
"e."
".+-e"
"30."
" "
"    "
" +005047e+6"
" +005047e+6e2"
"459277e38+"
~~~

Attempt: 5
~~~
public class Solution {
    public boolean isNumber(String s) {
        if (s == null) return false;
        s = s.trim();
        if (s.length() == 0) return false;

        boolean digit = false; // seen digit or not
        boolean dot = false;   // seen dot '.' or not
        boolean sc = false;     // seen 'e' or not, scientific num
        boolean exp = false;    // seen exp part for scientific num or not
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (Character.isDigit(ch)) {
                digit = true;
                if (sc) exp = true;
            }
            else if (ch == '.' && !dot && !sc) {
                dot = true;
            }
            else if (ch == 'e' && digit && !sc) {
                sc = true;
            }
            else if ((ch == '-' || ch == '+') && (i == 0 || (sc && s.charAt(i - 1) == 'e'))) {
                continue;
            }
            else return false;
        }

        if (dot && !digit) {
            return false;
        }
        if (sc && !exp) {
            return false;
        }

        return true;
    }
}
~~~
