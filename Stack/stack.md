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
