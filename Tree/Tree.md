## 116. Populating Next Right Pointers in Each Node
Given a binary tree
~~~
    struct TreeLinkNode {
      TreeLinkNode *left;
      TreeLinkNode *right;
      TreeLinkNode *next;
    }
~~~
Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set to NULL.

Initially, all next pointers are set to NULL.

Note:

You may only use constant extra space.
You may assume that it is a perfect binary tree (ie, all leaves are at the same level, and every parent has two children).
For example,
Given the following perfect binary tree,
~~~
         1
       /  \
      2    3
     / \  / \
    4  5  6  7
~~~

After calling your function, the tree should look like:
~~~
         1 -> NULL
       /  \
      2 -> 3 -> NULL
     / \  / \
    4->5->6->7 -> NULL
~~~

#### Solution
1. 两层while循环，第一个处理一层一层，第二个处理层内遍历
2. 保存两个TreeLinkNode，一个是第一个while循环更新到下一层，另一个是第二个while循环更新到当前level的下一个结点

~~~
/**
 * Definition for binary tree with next pointer.
 * public class TreeLinkNode {
 *     int val;
 *     TreeLinkNode left, right, next;
 *     TreeLinkNode(int x) { val = x; }
 * }
 */
public class Solution {
    public void connect(TreeLinkNode root) {
        if (root == null) return;

        TreeLinkNode curr = root;
        while (curr != null) {
            TreeLinkNode post = curr.left; // the first node in next level
            TreeLinkNode prev = null;
            while (curr != null && curr.left != null) {
                if (prev != null) prev.next = curr.left;
                curr.left.next = curr.right;
                prev = curr.right; // save the right child of prev node
                curr = curr.next;
            }
            curr = post;
        }
    }
}
~~~

## 298. Binary Tree Longest Consecutive Sequence
Given a binary tree, find the length of the longest consecutive sequence path.

The path refers to any sequence of nodes from some starting node to any node in the tree along the parent-child connections. The longest consecutive path need to be from parent to child (cannot be the reverse).

For example,
~~~
   1
    \
     3
    / \
   2   4
        \
         5
~~~
Longest consecutive sequence path is 3-4-5, so return 3.
~~~
   2
    \
     3
    /
   2    
  /
 1
~~~
Longest consecutive sequence path is 2-3, not 3-2-1, so return 2.

#### Solution
Method 1: 320ms Very Slow <br>
Time Complexity : O(n^2) <br>
Space Complexity: O(n) due to recursion
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
public class Solution {
    public int longestConsecutive(TreeNode root) {
        if (root == null) return 0;
        int self = helper(root);
        int left = longestConsecutive(root.left);
        int right = longestConsecutive(root.right);
        return Math.max(self, Math.max(left, right));
    }

    private int helper(TreeNode root) {
        if (root == null) return 0;

        int left = 1;
        if (root.left != null && root.val + 1 == root.left.val) {
            left += helper(root.left);
        }
        int right = 1;
        if (root.right != null && root.val + 1 == root.right.val) {
            right += helper(root.right);
        }
        return Math.max(left, right);
    }
}
~~~

Method 2: Top down solution, helper function with length <br>
Time Complexity : O(n) <br>
Space Complexity: O(n) due to recursion
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
public class Solution {
    public int longestConsecutive(TreeNode root) {
        if (root == null) return 0;
        return helper(root, null, 0);
    }

    private int helper(TreeNode node, TreeNode parent, int length) {
        if (node == null) return 0;
        if (parent != null && parent.val + 1 == node.val) {
            length++;
        }
        else length = 1;
        int left = helper(node.left, node, length);
        int right = helper(node.right, node, length);
        return Math.max(length, Math.max(left, right));
    }
}
~~~

Method 3: Bottom up solution <br>
Time Complexity : O(n) <br>
Space Complexity: O(n) due to recursion
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
public class Solution {
    private int maxlen = 0;

    public int longestConsecutive(TreeNode root) {
        if (root == null) return 0;
        helper(root);
        return maxlen;
    }

    private int helper(TreeNode node) {
        if (node == null) return 0;
        int left = 1 + helper(node.left);
        if (node.left != null && node.left.val != node.val + 1) {
            left = 1;
        }

        int right = 1 + helper(node.right);
        if (node.right != null && node.right.val != node.val + 1) {
            right = 1;
        }

        maxlen = Math.max(maxlen, Math.max(left, right));
        return Math.max(left, right);
    }
}
~~~
