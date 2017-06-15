## 333. Largest BST Subtree
Given a binary tree, find the largest subtree which is a Binary Search Tree (BST), where largest means subtree with largest number of nodes in it.

Note:
A subtree must include all of its descendants.
Here's an example:
~~~
    10
    / \
   5  15
  / \   \
 1   8   7
~~~
The Largest BST Subtree in this case is the highlighted one.
The return value is the subtree's size, which is 3.
Follow up:
Can you figure out ways to solve it with O(n) time complexity?

#### Solution
这题首先是递归解决。合法的解有以下几种情况：
1. 两个子树都是合法的BST, 并且父节点和左右子树的父节点比较也是合法的，那么len = 1 + largestBSTSubtree(root.left) + largestBSTSubtree(root.right)
2. 子树中有一个是合法的（或者都不合法），那么len = Math.max(largestBSTSubtree(root.left), largestBSTSubtree(root.right))

**问题的关键是，正确处理父节点和左右子树的父节点比较**
合法的情况必须满足：
1. 父节点大于左树所有节点
2. 父节点小于右树所有节点
为了进行比较，在递归调用的时候，返回左右子树的largestBSTSubtree个数外，还要返回左右子树的最大和最小值，以方便和父节点进行比较

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
    class Result {
        int count;
        int min;
        int max;

        Result(int count, int min, int max) {
            this.count = count;
            this.min = min;
            this.max = max;
        }
    }

    int ans = 0;

    public int largestBSTSubtree(TreeNode root) {
        if (root == null) return 0;
        largestBSTSubtreeHelper(root);
        return ans;
    }

    public Result largestBSTSubtreeHelper(TreeNode root) {
        if (root == null) return new Result(0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        if (root.left == null && root.right == null) {
            ans = Math.max(ans, 1);
            return new Result(1, root.val, root.val);
        }

        Result r1 = null;
        Result r2 = null;
        boolean isValid = true;
        if (root.left != null) {
            r1 = largestBSTSubtreeHelper(root.left);
            if (root.val <= r1.max || r1.count == 0) isValid = false;
        }
        if (root.right != null) {
            r2 = largestBSTSubtreeHelper(root.right);
            if (root.val >= r2.min || r2.count == 0) isValid = false;
        }

        int count1 = r1 == null ? 0 : r1.count;
        int count2 = r2 == null ? 0 : r2.count;
        if (!isValid) {
            ans = Math.max(ans, Math.max(count1, count2));
            return new Result(0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }

        int min = r1 == null ? r2.min : r1.min;
        int max = r2 == null ? r1.max : r2.max;
        ans = Math.max(ans, 1 + count1 + count2);
        return new Result(1 + count1 + count2, Math.min(min, root.val), Math.max(max, root.val));
    }
}
~~~
