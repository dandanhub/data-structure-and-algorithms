## 104. Maximum Depth of Binary Tree (+)
Given a binary tree, find its maximum depth.

The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.

#### Solution
~~~
public class Solution {
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return Math.max(1 + maxDepth(root.left), 1 + maxDepth(root.right));
    }
}
~~~

## 111. Minimum Depth of Binary Tree
Given a binary tree, find its minimum depth.

The minimum depth is the number of nodes along the shortest path from the root node down to the nearest leaf node.

#### Solution
~~~
public class Solution {
    public int minDepth(TreeNode root) {
        if (root == null) return 0;

        int left = minDepth(root.left);
        int right = minDepth(root.right);
        if (left == 0 || right == 0) return left + right + 1;
        return Math.min(left + 1, right + 1);
    }
}
~~~

## 110. Balanced Binary Tree
Given a binary tree, determine if it is height-balanced.

For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the two subtrees of every node never differ by more than 1.

#### Solution
O(n) bottom-up solution. Do recursion to get the depth of each node, whenever finding a node with Math.abs(h(left) - h(right)) > 1, return -1 to mark the tree is unbalanced.
~~~
public class Solution {
    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        if (depth(root) == -1) return false;

        return true;
    }

    private int depth(TreeNode root) {
        if (root == null) return 0;
        int left = depth(root.left);
        int right = depth(root.right);

        if (left == -1 || right == -1 ||
            left - right > 1 || right - left > 1) {
            return -1;
        }

        return Math.max(1 + left, 1 + right);
    }
}
~~~
