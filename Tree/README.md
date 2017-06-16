## 96. Unique Binary Search Trees
Given n, how many structurally unique BST's (binary search trees) that store values 1...n?

For example,
Given n = 3, there are a total of 5 unique BST's.
~~~~
   1         3     3      2      1
    \       /     /      / \      \
     3     2     1      1   3      2
    /     /       \                 \
   2     1         2                 3
~~~~

#### Solution
DP
- n = 0: 1
- n = 1: 1
- n = 2: 2
- n = 3: root = 1, 2; root = 2, 1; root = 3, 2;
- n = 4: root = 1, 5; root = 2, 1 * 2 = 2; root = 3, 1 * 2 = 2; root = 4, 5
- ...
- n = k: root = i, dp[i-1] * dp[k-i]

## 95. Unique Binary Search Trees II
Given an integer n, generate all structurally unique BST's (binary search trees) that store values 1...n.

For example,

Given n = 3, your program should return all 5 unique BST's shown below.
~~~~
   1         3     3      2      1
    \       /     /      / \      \
     3     2     1      1   3      2
    /     /       \                 \
   2     1         2                 3
~~~~

#### Solution
Add left and right tree recursively.
The base case of recursive function is start equals to end.

## 98. Validate Binary Search Tree
Given a binary tree, determine if it is a valid binary search tree (BST).

#### Solution
- The first thought is to in-order traverse the tree and determine if the traversal result is in increasing order. Time complexity O(n) and space complexity O(n). Accepted.
- An improved solution is to maintain a min and max value. The solution saves O(n) space. Use Long.MIN_VALUE, Long.MAX_VALUE as default low and up bound to avoid "overflow" issue.

## 99. Recover Binary Search Tree
Two elements of a binary search tree (BST) are swapped by mistake.

Recover the tree without changing its structure.

Note:
A solution using O(n) space is pretty straight forward. Could you devise a constant space solution?

#### Solution
Use Morris Travesal (?) referring to [here](https://discuss.leetcode.com/topic/9305/detail-explain-about-how-morris-traversal-finds-two-incorrect-pointer).

## 100. Same Tree
Given two binary trees, write a function to check if they are equal or not.

Two binary trees are considered equal if they are structurally identical and the nodes have the same value.

#### Solution
Easy. Pay attention to null handling.

## 101. Symmetric Tree
Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).

#### Solution
Recursively check the tree.

## 124. Binary Tree Maximum Path Sum
Given a binary tree, find the maximum path sum.

For this problem, a path is defined as any sequence of nodes from some starting node to any node in the tree along the parent-child connections. The path must contain at least one node and does not need to go through the root.

For example:
Given the below binary tree,
~~~~
       1
      / \
     2   3
~~~~
Return 6.

#### Solution
We need to examine all possibilities and return the max sum.
The trick is to use an instance variable for max sum.

## 543. Diameter of Binary Tree
Given a binary tree, you need to compute the length of the diameter of the tree. The diameter of a binary tree is the length of the longest path between any two nodes in a tree. This path may or may not pass through the root.

Example:
Given a binary tree
~~~~
          1
         / \
        2   3
       / \     
      4   5    
~~~~
Return 3, which is the length of the path [4,2,1,3] or [5,2,1,3].
Note: The length of path between two nodes is represented by the number of edges between them.

#### Solution
The thought is to traverse all possibilities, for a given node, there are two possibilities that its longest path is:
1. when the path passes the node, length of the path = depth(left) + depth(right)
2. when the path does not pass the node, length of the path = max(diameterOfBinaryTree(left), diameterOfBinaryTree(right))

## 538. Convert BST to Greater Tree
Given a Binary Search Tree (BST), convert it to a Greater Tree such that every key of the original BST is changed to the original key plus sum of all keys greater than the original key in BST.

Example:
~~~~
Input: The root of a Binary Search Tree like this:
              5
            /   \
           2     13

Output: The root of a Greater Tree like this:
             18
            /   \
          20     13
~~~~

#### Solution
1. Method 1: I first used a naiive way to solve the issue. Trivial and prone to make mistakes.
2. Method 2: Take advantage of BST, like doing in-order traversal (right - mid - left), by doing this, every node comes before is greater than current node.
