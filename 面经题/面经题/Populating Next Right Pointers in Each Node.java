/**
 * Definition for binary tree with next pointer.
 * public class TreeLinkNode {
 *     int val;
 *     TreeLinkNode left, right, next;
 *     TreeLinkNode(int x) { val = x; }
 * }
 */
 // Given the following perfect binary tree
 // 1. 两层while循环，第一个处理一层一层，第二个处理层内遍历
 // 2. 保存两个TreeLinkNode，一个是第一个while循环更新到下一层，另一个是第二个while循环更新到当前level的下一个结点
public class Solution {
    public void connect(TreeLinkNode root) {
        if (root == null) return;

        TreeLinkNode currLevel = root;
        while (currLevel != null) {
            TreeLinkNode nextHead = currLevel.left;
            while (currLevel != null && currLevel.left != null) {
                currLevel.left.next = currLevel.right;
                if (currLevel.next != null) currLevel.right.next = currLevel.next.left;
                currLevel = currLevel.next;
            }
            currLevel = nextHead;
        }
    }
}

// 117. Populating Next Right Pointers in Each Node II
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

        TreeLinkNode currLevel = root;
        TreeLinkNode nextLevel = null;
        TreeLinkNode prev = null;

        while (currLevel != null) {
            while (currLevel != null) {
                // left child
                if (currLevel.left != null) {
                    if (nextLevel == null) {
                        nextLevel = currLevel.left;
                        prev = currLevel.left;
                    }
                    else if (prev != null) {
                        prev.next = currLevel.left;
                        prev = prev.next;
                    }
                }

                // right child
                if (currLevel.right != null) {
                    if (nextLevel == null) {
                        nextLevel = currLevel.right;
                        prev = currLevel.right;
                    }
                    else if (prev != null) {
                        prev.next = currLevel.right;
                        prev = prev.next;
                    }
                }

                currLevel = currLevel.next;
            }

            currLevel = nextLevel;
            nextLevel = null;
            prev = null;
        }

    }
}
