## 402. Remove K Digits
Given a non-negative integer num represented as a string, remove k digits from the number so that the new number is the smallest possible.

Note:
The length of num is less than 10002 and will be ≥ k.
The given num does not contain any leading zero.
Example 1:
~~~
Input: num = "1432219", k = 3
Output: "1219"
Explanation: Remove the three digits 4, 3, and 2 to form the new number 1219 which is the smallest.
~~~

Example 2:
~~~
Input: num = "10200", k = 1
Output: "200"
Explanation: Remove the leading 1 and the number is 200. Note that the output must not contain leading zeroes.
~~~

Example 3:
~~~
Input: num = "10", k = 2
Output: "0"
Explanation: Remove all the digits from the number and it is left with nothing which is 0.
~~~

#### Solution
核心思想是尽可能删除靠左的比较大的数字，用栈来维护前后数字的大小关系

Attempt: 3
Bug 1: 输入的数字很长，所以在考虑移除leading zero的时候，不能转成数字再转回String
Bug 2: 移除leading zero的时候，注意考虑最后输出的结果本身是不是就是0，如果是的话，则停止
~~~
public class Solution {
    public String removeKdigits(String num, int k) {
        if (num == null || num.length() == 0) return num;
        if (k >= num.length()) return "0";

        Stack<Character> stack = new Stack<Character>();
        char[] nums = num.toCharArray();
        for (int i = 0; i < nums.length; i++) {
            // remove the digit
            while (!stack.isEmpty() && stack.peek() > nums[i] && k > 0) {
                stack.pop();
                k--;
            }
            stack.push(nums[i]);
        }

        // edge case
        while (k > 0 && !stack.isEmpty()) {
            stack.pop();
            k--;
        }

        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }

        sb = sb.reverse();

        // remove leading zero
        while (sb.length() > 1 && sb.charAt(0) == '0') sb.deleteCharAt(0);

        return sb.toString();
    }
}
~~~

不用栈结构，用数组指针模拟栈顶
~~~
public class Solution {
    public String removeKdigits(String num, int k) {
        if (num == null || num.length() == 0) return num;
        if (k >= num.length()) return "0";

        char[] nums = num.toCharArray();
        int top = 0;
        for (int i = 0; i < nums.length; i++) {
            // remove the digit
            while (top > 0 && nums[top - 1] > nums[i] && k > 0) {
                top--;
                k--;
            }
            nums[top++] = nums[i];
        }

        // edge case
        while (k > 0 && top > 0) {
            top--;
            k--;
        }

        StringBuilder sb = new StringBuilder();
        while (top > 0) {
            sb.append(nums[--top]);
        }

        sb = sb.reverse();

        // remove leading zero
        while (sb.length() > 1 && sb.charAt(0) == '0') sb.deleteCharAt(0);

        return sb.toString();
    }
}
~~~

## 224. Basic Calculator (Medium) *
Implement a basic calculator to evaluate a simple expression string.

The expression string may contain open ( and closing parentheses ), the plus + or minus sign -, non-negative integers and empty spaces .

You may assume that the given expression is always valid.

Some examples:
~~~
"1 + 1" = 2
" 2-1 + 2 " = 3
"(1+(4+5+2)-3)+(6+8)" = 23
~~~
Note: Do not use the eval built-in library function.

#### Solution
因为这题只有+ and -号，所以在进行处理的时候不需要借用stack来保存operator，反而用一个int sign (1 or -1)来表示即可。但是因为有()会影响sign，所以用stack来保存(左边的结果，最后遇到)再弹出之前的结果进行运算。
**注意这种String里面读数字的题再解析数字的时候要考虑到数字可能占用多个chars**

~~~
public class Solution {
    public int calculate(String s) {
        if (s == null || s.length() == 0) return 0;

        int len = s.length();
        int sign = 1;
        int res = 0;
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < len; i++) {
            char ch = s.charAt(i);
            if (ch == ' ') continue;
            else if (ch == '+') {
                sign = 1;
            }
            else if (ch == '-') {
                sign = -1;
            }
            else if (ch == '(') {
                stack.push(res); // push the prev res before (
                stack.push(sign); // push the prev sign before (
                sign = 1; // reset sign and res
                res = 0;
            }
            else if (ch == ')') {
                int presign = stack.pop();
                int preres = stack.pop();
                res = preres + presign * res;
                sign = 1;
            }
            else { // a digit
                // parse all contigious digits to a num
                int num = 0;
                while (i < len && Character.isDigit(s.charAt(i))) {
                    num = num * 10 + (s.charAt(i) - '0');
                    i++;
                }
                i--; // backward one step
                res += sign * num;
            }
        }

        return res;
    }
}
~~~

## 227. Basic Calculator II (Medium) *
Implement a basic calculator to evaluate a simple expression string.

The expression string contains only non-negative integers, +, -, \*, / operators and empty spaces . The integer division should truncate toward zero.

You may assume that the given expression is always valid.

Some examples:
"3+2*2" = 7
" 3/2 " = 1
" 3+5 / 2 " = 5
Note: Do not use the eval built-in library function.

#### Solution
1. 一个栈保存数字，如果该数字之前的符号是加或减，那么把当前数字压入栈中，注意如果是减号，则加入当前数字的相反数，因为减法相当于加上一个相反数。如果之前的符号是乘或除，那么从栈顶取出一个数字和当前数字进行乘或除的运算，再把结果压入栈中，那么完成一遍遍历后，所有的乘或除都运算完了，再把栈中所有的数字都加起来就是最终结果了
2. 不用栈，用last记录“栈顶”数字，res记录最终结果，模拟出栈入栈过程

1
~~~
public class Solution {
    public int calculate(String s) {
        if (s == null || s.length() == 0) return 0;
        Stack<Integer> stack = new Stack<Integer>();
        char sign = '+';
        int len =  s.length();
        for (int i = 0; i < len; i++) {
            if (s.charAt(i) == ' ') continue;
            else if (Character.isDigit(s.charAt(i))) {
                int num = 0;
                while (i < len && Character.isDigit(s.charAt(i))) {
                    num = 10 * num + s.charAt(i) - '0';
                    i++;
                }
                i--;

                if (sign == '+') {
                stack.push(num);
                }
                else if (sign == '-') {
                    stack.push(-num);
                }
                else if (sign == '*') {
                    int last = stack.pop();
                    stack.push(last * num);
                }
                else if (sign == '/') {
                    int last = stack.pop();
                    stack.push(last / num);
                }
            }
            else {
                sign = s.charAt(i);
            }
        }

        int res = 0;
        while (!stack.isEmpty()) {
            res += stack.pop();
        }

        return res;
    }
}
~~~

2
~~~
public class Solution {
    public int calculate(String s) {
        if (s == null || s.length() == 0) return 0;

        int res = 0;
        int last = 0;
        char sign = '+';
        int len =  s.length();
        for (int i = 0; i < len; i++) {
            if (s.charAt(i) == ' ') continue;
            else if (Character.isDigit(s.charAt(i))) {
                int num = 0;
                while (i < len && Character.isDigit(s.charAt(i))) {
                    num = 10 * num + s.charAt(i) - '0';
                    i++;
                }
                i--;

                if (sign == '+') {
                    last = num;
                }
                else if (sign == '-') {
                    last = -num;
                }
                else if (sign == '*') {
                    res -= last;
                    last = last * num;
                }
                else if (sign == '/') {
                    res -= last;
                    last = last / num;
                }
                res += last;
            }
            else {
                sign = s.charAt(i);
            }
        }

        return res;
    }
}
~~~

~~~
面试题目是： basic calculator II (leetcode 227) 需要处理 +-*/ 四个运算， 输入的string 有空格， 数字 和 运算符。之前面经有这道题， follow up也是很常规的。 解法参考了： https://discuss.leetcode.com/topic/16935/share-my-java-solution 在codepad上写， 我用的 java， 需要自己写 class， 自己写 main 函数 和 testcase， 由于我用了stack， 需要自己 import java.util. 跑过了自己写的testcase后， 面试官自己写了几个testcase测了一下没有问题。

follow up 1 是 可不可以不用 stack 。 我说可以用 两个变量 ： 一个 变量 来记录当前的 sum 值， 另一个 变量 记录上一个 数字 。 面试官觉得make sense 就没让写代码实现。 (上面的链接里有这种解法的代码) follow up 2 是 加 括号。我又写了一个函数， 用stack 存 '(' 的index， 遇到 ')' ， pop() ，出最近的一个 '('， 然后 取出这一段 substring ， 求出结果， 然后 用这个结果 替换回 原来的 substring。 注意的一点是， 这样子 input string 的长度 就改变了， index的位置也就会变。 最白痴的处理方法就是 把 结果 填充成和原来一样的长度再替换回去。 最后把 没有 括号的 string 求一遍结果。 同样的自己写 test case， 面试官又测了几个自己的testcase ，比如 ((((5)))) 这样子的。 最后是 向他提问， 问了些简单的问题，就结束了。 面试时间一共基本是 55分钟的样子。没有问到 时间复杂度 空间复杂度的问题。

链接: https://instant.1point3acres.com/thread/203992
来源: 一亩三分地
~~~
