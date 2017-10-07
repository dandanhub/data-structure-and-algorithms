package Google;

import java.util.*;

public class RemoveNodes {    
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public static List<TreeNode> removeNodes(TreeNode root, List<Integer> toRemoved) {
        Set<Integer> toRemovedSet = new HashSet<Integer>();
        for (int num : toRemoved) {
            toRemovedSet.add(num);
        }
        List<TreeNode> res = new ArrayList<TreeNode>();
        TreeNode node = helper(res, toRemovedSet, null, root, true);
        if (node != null) res.add(root);
        return res;
    }

    private static TreeNode helper(List<TreeNode> res, Set<Integer> toRemovedSet,
            TreeNode parent, TreeNode node, boolean isLeft) {
        if (node == null) return null;
        node.left = helper(res, toRemovedSet, node, node.left, true);
        node.right = helper(res, toRemovedSet, node, node.right, false);
        if (toRemovedSet.contains(node.val)) {
            if (parent != null) {
                if (isLeft) parent.left = null;
                else parent.right = null;
            }
            if (node.left != null) {
                res.add(node.left);
            }
            if (node.right != null) {
                res.add(node.right);
            }
            return null;
        }
        return node;
    }
}
