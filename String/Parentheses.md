## 20. Valid Parentheses (Easy)
Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.

The brackets must close in the correct order, "()" and "()[]{}" are all valid but "(]" and "([)]" are not.

#### Solution
用栈来处理
**如果parentheses只有(),那么可以用count计数器来解决** <br>
**注意最后返回的时候是判断return stack.isEmpty();或者是return count == 0;**

Attempt: 2
~~~
public class Solution {
    public boolean isValid(String s) {
        if (s == null || s.length() == 0) return true;
        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(' || ch == '{' || ch == '[') stack.push(ch);
            else if (ch == ')' || ch == '}' || ch == ']') {
                if (stack.isEmpty()) return false; // fix bug "]"
                char prev = stack.pop();
                if ((ch == ')' && prev != '(') || (ch == '}' && prev != '{')
                   || (ch == ']' && prev != '[')) return false; // fix bug "()"
            }
            else return false;
        }
        return stack.isEmpty();
    }
}
~~~

当parentheses只有()的时候
~~~
public boolean isValid(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                count++;
            }
            else if (s.charAt(i) == ')') {
                count--;
                if (count < 0) {
                    return false;
                }
            }
        }
        return count == 0;
    }
~~~

## 22. Generate Parentheses
Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.

For example, given n = 3, a solution set is:
~~~
[
  "((()))",
  "(()())",
  "(())()",
  "()(())",
  "()()()"
]
~~~

#### Solution
Backtracking
**每次加入一个(然后递归，当剩余(个数小于剩余)个数的时候，加入一个)然后递归**

~~~
public class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<String>();
        if (n <= 0) return ans;
        helper(ans, new StringBuilder(), n, n);
        return ans;
    }

    private void helper(List<String> ans, StringBuilder sb, int lp, int rp) {
        // valid ans
        if (lp == 0 && rp == 0) {
            ans.add(sb.toString());
            return;
        }

        if (lp > 0) {
            sb.append("(");
            helper(ans, sb, lp - 1, rp);
            sb.deleteCharAt(sb.length() - 1);
        }

        if (lp < rp) {
            sb.append(")");
            helper(ans, sb, lp, rp - 1);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}
~~~

## 241. Different Ways to Add Parentheses

Given a string of numbers and operators, return all possible results from computing all the different possible ways to group numbers and operators. The valid operators are +, - and \*.

Example 1
~~~
Input: "2-1-1".

((2-1)-1) = 0
(2-(1-1)) = 2
Output: [0, 2]
~~~

Example 2
~~~
Input: "2*3-4*5"

(2*(3-(4*5))) = -34
((2*3)-(4*5)) = -14
((2*(3-4))*5) = -10
(2*((3-4)*5)) = -10
(((2*3)-4)*5) = 10
Output: [-34, -14, -10, -10, 10]
~~~

#### Solution
1. 分治，遇到operator，分别计算左边和右边substring的可能解，然后合并
2. 用map cache来加快速度

~~~
public class Solution {
    Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
    public List<Integer> diffWaysToCompute(String input) {
        List<Integer> list = new ArrayList<Integer>();
        if (input == null || input.length() == 0) return list;

        // base case
        if (map.containsKey(input)) return map.get(input);
        if (input.matches("[0-9]+")) {
            int num = Integer.valueOf(input);
            list.add(num);
            return list;
        }

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i) ;
            if (ch == '+' || ch == '-' || ch == '*') {
                List<Integer> left = diffWaysToCompute(input.substring(0, i));
                List<Integer> right = diffWaysToCompute(input.substring(i + 1));
                combine(left, right, ch, list);
            }

        }

        map.put(input, list);
        return list;
    }

    private void combine(List<Integer> left, List<Integer> right, char operator, List<Integer> ans) {
        for (int i = 0; i < left.size(); i++) {
            for (int j = 0; j < right.size(); j++) {
                int num = 0;
                int num1 = left.get(i);
                int num2 = right.get(j);
                if (operator == '-') {
                    num = num1 - num2;
                }
                else if (operator == '+') {
                    num = num1 + num2;
                }

                else if (operator == '*') {
                    num = num1 * num2;
                }
                ans.add(num);
            }
        }
    }
}
~~~

## 301. Remove Invalid Parentheses
Remove the minimum number of invalid parentheses in order to make the input string valid. Return all possible results.

Note: The input string may contain letters other than the parentheses ( and ).

Examples:
~~~
"()())()" -> ["()()()", "(())()"]
"(a)())()" -> ["(a)()()", "(a())()"]
")(" -> [""]
~~~

#### Solution
1. 思考如果只要求返回一个结果，简单很多，如何处理
2. 如果要求返回minimal number of removal如何处理（用栈，遇到`(`入栈，遇到`)`如果栈顶是`(`，pop出一个，最后返回栈里元素的个数）

DFS Refer to [discussion](https://discuss.leetcode.com/topic/34875/easy-short-concise-and-fast-java-dfs-3-ms-solution/2)
~~~
public class Solution {
    public List<String> removeInvalidParentheses(String s) {
        List<String> ans = new ArrayList<String>();
        if (s == null) return ans;
        char[] pars = {'(', ')'};
        helper(s, ans, 0, 0, pars);
        return ans;
    }

    private void helper(String s, List<String> ans, int start, int lastIndex, char[] pars) {
        int count = 0;
        for (int i = start; i < s.length(); i++) {
            if (s.charAt(i) == pars[0]) count++;
            if (s.charAt(i) == pars[1]) count--;
            // meet invalid parentheses, need to remove a ')'
            if (count < 0) {
                for (int j = lastIndex; j <= i; j++) {
                    if (s.charAt(j) == pars[1] && (j == lastIndex || s.charAt(j - 1) != pars[1])) {
                        helper(s.substring(0, j) + s.substring(j + 1), ans, i, j, pars);
                    }
                }
                return;
            }
        }

        StringBuilder sb = new StringBuilder(s);
        String reversed = sb.reverse().toString();
        if (pars[0] == '(') { // need to check from right to left for invalid ')'
            helper(reversed, ans, 0, 0, new char[]{')', '('});
        }
        else {
            ans.add(reversed);
        }
    }
}
~~~

如果只要求返回一个结果（注意：未充分测试）<br>
Given a string with parentheses, return a string with balanced parentheses by removing the fewest characters possible. You cannot add anything to the string.
~~~
public class Solution {
    public String removeInvalidParentheses(String s) {
        if (s == null) return null;
        String str = helper(s, new char[]{'(', ')'});
        StringBuilder sb = new StringBuilder(str);
        str = helper(sb.reverse().toString(), new char[]{')', '('});
        return new StringBuilder(str).reverse().toString();
    }

    private String helper(String s, char[] pars) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == pars[0]) count++;
            if (s.charAt(i) == pars[1]) count--;
            // meet invalid parentheses, need to remove a ')'
            if (count < 0) {
                for (int j = 0; j <= i; j++) {
                    if (s.charAt(j) == pars[1]) {
                        String suffix = helper(s.substring(i + 1), pars);
                        return s.substring(0, j) + s.substring(j + 1, i + 1) + suffix;
                    }
                }
            }
        }
        return s;
    }
}
~~~

如果是BFS, 注意处理TLE, TLE版本
~~~
public class Solution {
    public List<String> removeInvalidParentheses(String s) {
        if (s == null) return new ArrayList<String>();

        // take care of edge case
        if (isValid(s)) {
            List<String> ans = new ArrayList<String>();
            ans.add(s);
            return ans;
        }

        Set<String> set = new HashSet<String>();  // 去除重复
        Queue<String> queue = new LinkedList<String>();
        queue.offer(s);
        boolean found = false;
        while (!queue.isEmpty()) {
            String str = queue.poll();
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) != ')' && str.charAt(i) != '(') continue; // 跳过字母元素
                StringBuilder sb = new StringBuilder(str);
                sb.deleteCharAt(i);
                String next = sb.toString();
                if (isValid(next)) {
                    set.add(next);
                    found = true;
                }
                if (!found) queue.offer(next);
            }
        }

        return new ArrayList<String>(set);
    }

    private boolean isValid(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') count++;
            if (s.charAt(i) == ')') count--;
            if (count < 0) return false;
        }
        return count == 0;
    }
}
~~~

如果是BFS, 注意处理TLE, 加入Set<String>判断当前的String是否已经访问过
~~~
public class Solution {
    public List<String> removeInvalidParentheses(String s) {
        if (s == null) return new ArrayList<String>();

        // take care of edge case
        if (isValid(s)) {
            List<String> ans = new ArrayList<String>();
            ans.add(s);
            return ans;
        }

        Set<String> set = new HashSet<String>();  // 去除重复
        Set<String> visited = new HashSet<String>();
        Queue<String> queue = new LinkedList<String>();
        queue.offer(s);
        boolean found = false;
        while (!queue.isEmpty()) {
            String str = queue.poll();
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) != ')' && str.charAt(i) != '(') continue; // 跳过字母元素
                StringBuilder sb = new StringBuilder(str);
                sb.deleteCharAt(i);
                String next = sb.toString();
                if (isValid(next)) {
                    set.add(next);
                    found = true;
                }
                if (!found && !visited.contains(next)) {
                    queue.offer(next);
                    visited.add(next);
                }
            }
        }

        return new ArrayList<String>(set);
    }

    private boolean isValid(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') count++;
            if (s.charAt(i) == ')') count--;
            if (count < 0) return false;
        }
        return count == 0;
    }
}
~~~

~~~
今天上午面了非死不可，口音挺标准的男人，说是internatinalization组的，上来先来个自我介绍接着就是做题
LC301 Remove Invalid Parentheses，我突然发现这个题我快把答案背下来了，兴奋地上来就写，刷刷写完了，然后小哥叫我给他解释，我解释了半天，能详细有多详细，但是小哥又说看不懂我的code，我还得一点一点讲解，结果一个题做了40分钟，都快末了了，小哥突然说这个题不需要你返回所有的结果，只要一个结果就行，然后我一下子就懵了，本来想重写，小哥说时间来不及了，就这样了。。。

面完整个人都不好了，有一股想撞墙的感觉，疯了，要死了，这种情况还能过吗？

remove minimum number of left and right brackets and return the string with
valid bracket pairs. given (a))()), should return (a)(), or (a()); given )()
()), should return either ()() or (()); given ()((9()), should return either
()(9()) or ()((9)).
~~~

## 32. Longest Valid Parentheses
Given a string containing just the characters '(' and ')', find the length of the longest valid (well-formed) parentheses substring.

For "(()", the longest valid parentheses substring is "()", which has length = 2.

Another example is ")()())", where the longest valid parentheses substring is "()()", which has length = 4.

#### Solution
1. DP[i]表示以i结束的最长Parentheses长度
2. 用Stack来做 https://leetcode.com/articles/longest-valid-parentheses/

~~~
public class Solution {
    public int longestValidParentheses(String s) {
        if (s == null || !s.contains("(") || !s.contains(")")) return 0;
        int len = s.length();
        int[] dp = new int[len + 1]; // 以i结尾的最长的parentheses的长度

        int count = 0;
        for (int i = 2; i < len + 1; i++) { // start from the 2nd character
            if (s.charAt(i - 1) == ')') {
                // match the prev char
                if (s.charAt(i - 2) == '(') {
                    dp[i] = dp[i - 2] + 2;
                }
                else if (s.charAt(i - 2) == ')') {
                    int gap = dp[i - 1]; // the leftmost unmatched char
                    if (i - gap - 2 >= 0 && s.charAt(i - gap - 2) == '(') {
                        dp[i] = dp[i - gap - 2] + dp[i - 1] + 2;
                    }
                }
            }
            count = Math.max(count, dp[i]);
        }
        return count;
    }
}
~~~
