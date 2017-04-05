The java codes here basically summarize my work on Stack. The codes are driven by CMU course 08-722 Data Structures for Application Programmers and some LeetCode problems related about Stack.

# Solution.java
## 224. Basic Calculator (Hard)
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

## 150. Evaluate Reverse Polish Notation (Medium)
Evaluate the value of an arithmetic expression in Reverse Polish Notation.

Valid operators are +, -, \*, /. Each operand may be an integer or another expression.

Some examples:
~~~~
  ["2", "1", "+", "3", "*"] -> ((2 + 1) * 3) -> 9
  ["4", "13", "5", "/", "+"] -> (4 + (13 / 5)) -> 6
~~~~
Classical problem that can be resolved by using stack. Push elements except +, -, \*, / into stack. When encountering +, -, \*, / operator, pop up the stack and do calculate accordingly.


## 71. Simplify Path (Medium)
Given an absolute path for a file (Unix-style), simplify it.

For example,
path = "/home/", => "/home"
path = "/a/./b/../../c/", => "/c"

Corner Cases:
Did you consider the case where path = "/../"?
In this case, you should return "/".
Another corner case is the path might contain multiple slashes '/' together, such as "/home//foo/".
In this case, you should ignore redundant slashes and return "/home/foo".

#### Solution
1. Use a stack, when encountering "..", pop the last stack element out.
2. When encountering ".", do nothing and continue
3. For others, push it into stack.

## 144. Binary Tree Preorder Traversal (Medium)
Given a binary tree, return the preorder traversal of its nodes' values.

For example:
~~~~
Given binary tree {1,#,2,3},
   1
    \
     2
    /
   3
~~~~
return [1,2,3].

Note: Recursive solution is trivial, could you do it iteratively?

#### Solution
Iterative preorder is comparatively simply. Use a stack initialized with root in. While stack is not empty, pop an element from stack, push the element's right child to stack and then the element's left child.

## 94. Binary Tree Inorder Traversal
Given a binary tree, return the inorder traversal of its nodes' values.

For example:
~~~~
Given binary tree [1,null,2,3],
   1
    \
     2
    /
   3
~~~~
return [1,3,2].

Note: Recursive solution is trivial, could you do it iteratively?

#### Solution
1. Start from the root node, and go left until reaching null, push all nodes along the root into stack.
2. Pop an element from stack, the trick is to check whether the current node poped out has a right child or not.
3. If yes, set the right node as new starting point and continue from the step 1.
4. If not, continue from the step 2.


## 145. Binary Tree Postorder Traversal
Given a binary tree, return the postorder traversal of its nodes' values.

For example:
Given binary tree {1,#,2,3},
~~~~
   1
    \
     2
    /
   3
~~~~
return [3,2,1].

Note: Recursive solution is trivial, could you do it iteratively?

#### Solution
We can modify the in-order traversal to do the postorder traversal, the trick is to add a sentinel to record the last poped out node. In in-order traversal, we check whether a poped out element has right node, if yes, we set the right node as current node. Here, instead of pop an element right away, we peek the top element on the stack. And, we add more one condition that checks whether the right element has been visited or not.

## 255. Verify Preorder Sequence in Binary Search Tree (Medium) *
Given an array of numbers, verify whether it is the correct preorder traversal sequence of a binary search tree.

You may assume each number in the sequence is unique.

Follow up:
Could you do it using only constant space complexity?

#### Solution 1:
Use a stack to simulate in-order traversal process. The trick is to use a variable, called it prev, to record the last node out. We can think the variable as sentinel pointing to last root node.
1. When the current number is smaller than the peek element of the stack, compare it with the prev variable. For a valid BST, the current number should always larger than the prev variable.
2. When the current number is larger than the peek element of the stack, pop the stack until its peek is less than the current element.

#### Solution 2:
Instead of using stack, we can simply use index to move back and forth the array of numbers.

## 439. Ternary Expression Parser
Given a string representing arbitrarily nested ternary expressions, calculate the result of the expression. You can always assume that the given expression is valid and only consists of digits 0-9, ?, :, T and F (T and F represent True and False respectively).

Note:

The length of the given string is ≤ 10000.
Each number will contain only one digit.
The conditional expressions group right-to-left (as usual in most languages).
The condition will always be either T or F. That is, the condition will never be a digit.
The result of the expression will always evaluate to either a digit 0-9, T or F.

#### Solution
The key point is we need to parse the expression from right to left.
Use a stack to do the parse, once we encounter ‘？', we pop the stack to get left and right value. The problem is simple because we are guaranteed that each number will contain only one digit.
Refer to [this discussion](https://discuss.leetcode.com/topic/64409/very-easy-1-pass-stack-solution-in-java-no-string-concat)
