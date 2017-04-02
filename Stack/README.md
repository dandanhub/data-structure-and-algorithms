The java codes here basically summarize my work on Stack. The codes are driven by CMU course 08-722 Data Structures for Application Programmers and some LeetCode problems related about Stack.

# Solution.java
## 224. Basic Calculator
Implement a basic calculator to evaluate a simple expression string.

The expression string may contain open ( and closing parentheses ), the plus + or minus sign -, non-negative integers and empty spaces .

You may assume that the given expression is always valid.

Some examples:
~~~~
"1 + 1" = 2
" 2-1 + 2 " = 3
"(1+(4+5+2)-3)+(6+8)" = 23
~~~~
Note: Do not use the eval built-in library function.

#### Solution
1. First remove spaces of the string s.
2. Use stack to handle parentheses, pop the stack when encountering ')' until popping out '('.
3. Evaluate the string without parentheses using calculateHelper.

Be careful with '-', for example, "2-(5-6)".
