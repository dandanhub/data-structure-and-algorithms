## 270. Closest Binary Search Tree Value
Given a non-empty binary search tree and a target value, find the value in the BST that is closest to the target.

Note:
Given target value is a floating point.
You are guaranteed to have only one unique value in the BST that is closest to the target.

#### Solution
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
    public int closestValue(TreeNode root, double target) {
        TreeNode curr = root;
        int value = root.val;
        while (curr != null) {
            if (Math.abs(curr.val - target) < Math.abs(value - target)) {
                value = curr.val;
            }
            
            if (curr.val == target) {
                return value;
            }
            else if (curr.val > target) {
                curr = curr.left;
            }
            else {
                curr = curr.right;
            }
        }
        return value;
    }
}
~~~
