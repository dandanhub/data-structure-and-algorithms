# Binary Search Tree
## 255. Verify Preorder Sequence in Binary Search Tree (Medium)*
Given an array of numbers, verify whether it is the correct preorder traversal sequence of a binary search tree.

You may assume each number in the sequence is unique.

Follow up:
Could you do it using only constant space complexity?

#### Solution
1. Use to stack to verify preorder sequence. If next number is smaller than the last stack number, it means the next number is in the left subtree of all number in the stack, so we push the next number into the stack. If the next number is greater than the last stack number, we need to pop the numbers that are greater than the next number, and it means the next number is in the right subtrees of these poped number. In the meanwhile, we record the value of poped number, since we are in the right subtree of the poped number, we cannot meet any number that is smaller than the low bound.
2. Follow up let us use only constant space. We can use pointer (array index) to emulate stack.

Time: O(n)
Space: O(n)
Attempt: 2 <br>
~~~
public class Solution {
    public boolean verifyPreorder(int[] preorder) {
        if (preorder == null || preorder.length == 0) return true;
        Stack<Integer> stack = new Stack<Integer>();
        int low = Integer.MIN_VALUE;
        for (int i = 0; i < preorder.length; i++) {
            if (preorder[i] < low) return false;

            while (!stack.isEmpty() && preorder[i] > stack.peek()) {
                low = stack.pop();
            }
            stack.push(preorder[i]);
        }
        return true;
    }
}
~~~

Time: O(n)
Space: O(1)
Attempt: 1 <br>
~~~
public class Solution {
    public boolean verifyPreorder(int[] preorder) {
        if (preorder == null || preorder.length == 0) return true;
        int low = Integer.MIN_VALUE;
        int i = -1;
        for (int num : preorder) {
            if (num < low) return false;
            while (i != -1 && num > preorder[i]) {
                low = preorder[i];
                i--;
            }
            i++;
            preorder[i] = num;
        }
        return true;
    }
}
~~~

## 331. Verify Preorder Serialization of a Binary Tree
One way to serialize a binary tree is to use pre-order traversal. When we encounter a non-null node, we record the node's value. If it is a null node, we record using a sentinel value such as #.
~~~
     _9_
    /   \
   3     2
  / \   / \
 4   1  #  6
/ \ / \   / \
# # # #   # #
~~~
For example, the above binary tree can be serialized to the string "9,3,4,#,#,1,#,#,2,#,6,#,#", where # represents a null node.

Given a string of comma separated values, verify whether it is a correct preorder traversal serialization of a binary tree. Find an algorithm without reconstructing the tree.

Each comma separated value in the string must be either an integer or a character '#' representing null pointer.

You may assume that the input format is always valid, for example it could never contain two consecutive commas such as "1,,3".

Example 1:
"9,3,4,#,#,1,#,#,2,#,6,#,#"
Return true

Example 2:
"1,#"
Return false

Example 3:
"9,#,#,1"
Return false

#### Solution
1. A node may have three cases: 1) left and right children are both "#" 2) left or right child is "#", the other child is not 3) left and right children are neither "#".
If we see two consecutive "#", it means we are in 1), we pop the previous "#" and the leaf node out. In other case, we push the current node into stack.
2. 记录出度和入度，在建树过程中出度>入度，并且树建完后出度等于入度（如果给的是inorder和postorder的array应该如何处理）
3. 统计#节点和非#节点的个数

Time: O(n)
Space: O(n)
Attempts: 4 (bug failed test case "1,#,#,#,#")
~~~
public class Solution {
    public boolean isValidSerialization(String preorder) {
        if (preorder == null || preorder.length() == 0) return true;

        String[] nodes = preorder.split(",");
        Stack<String> stack = new Stack<String>();
        for (String str : nodes) {
            if (str.equals("#")) {
                while (!stack.isEmpty() && stack.peek().equals("#")) {
                    stack.pop();
                    if (stack.isEmpty()) return false; // whenever pop twice, check stack empty before the 2nd pop
                    stack.pop();
                }
            }
            stack.push(str);
        }

        return stack.size() == 1 && stack.peek().equals("#");
    }
}
~~~

Attempts: 2
~~~
public class Solution {
    public boolean isValidSerialization(String preorder) {
        if (preorder == null || preorder.length() == 0) return true;

        String[] nodes = preorder.split(",");
        int indegree = -1;
        int outdegree = 0;
        for (String str : nodes) {
            // bug version, failed test case "#,7,6,9,#,#,#"
            // if (str.equals("#")) {
            //     indegree += 1;
            // }
            // else {
            //     indegree += 1;
            //     outdegree += 2;
            // }

            // if (outdegree - indegree < 0) return false;

            indegree += 1;
            if (outdegree - indegree < 0) return false;
            if (!str.equals("#")) outdegree += 2;

        }

        return indegree == outdegree;
    }
}
~~~

~~~
Facebook面经
Given two pre-order traversal arrays of two binary search tree respectively, find first pair of non-matching leaves.
Follow Up: If they are general binary trees instead of BSTs, could you solve it? give out your reason.

From a pre-order traversal array of a BST, we can detect leaves by doing a sequential scan of the array with a helper stack (see code below for finding the next leaf).
From there, we can just keep on finding the next leaf in each tree and compare until we find leaves that don't match (or we run out of leaves in one tree before the other; or we just find no mismatches).

A binary tree with pre-order traversal array of 1,2,3 can be a root of 1 with left child 2 and right child 3, or it could be a linked list (e.g. 1 with right child 2, and 2 with right child 3)

Runtime: O(n). Space complexity: O(log n)

Binary Tree
http://www.geeksforgeeks.org/check-if-leaf-traversal-of-two-binary-trees-is-same/
~~~

## 530. Minimum Absolute Difference in BST
Given a binary search tree with non-negative values, find the minimum absolute difference between values of any two nodes.

Example:
~~~
Input:

   1
    \
     3
    /
   2

Output:
1

Explanation:
The minimum absolute difference is 1, which is the difference between 2 and 1 (or between 2 and 3).
~~~

Note: There are at least two nodes in this BST.

#### Solution
Method 1: Inorder traversal
~~~
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public int getMinimumDifference(TreeNode root) {
        if (root == null) return 0;

        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode curr = root;
        TreeNode prev = null;
        int diff = Integer.MAX_VALUE;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }

            TreeNode node = stack.pop();
            if (prev != null) {
                diff = Math.min(diff, node.val - prev.val);
            }
            prev = node;

            curr = node.right;
        }
        return diff;
    }
}
~~~

Follow-up: not BST
~~~
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public int getMinimumDifference(TreeNode root) {
        if (root == null) return 0;

        Stack<TreeNode> stack = new Stack<TreeNode>();
        stack.push(root);
        TreeSet<Integer> treeset = new TreeSet<Integer>();
        int diff = Integer.MAX_VALUE;
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            treeset.add(node.val);

            Integer higher = treeset.higher(node.val);
            if (higher != null) {
                diff = Math.min(diff, higher - node.val);
            }
            Integer lower = treeset.lower(node.val);
            if (lower != null) {
                diff = Math.min(diff, node.val - lower);
            }

            if (node.left != null) stack.push(node.left);
            if (node.right != null) stack.push(node.right);
        }
        return diff;
    }
}
~~~
