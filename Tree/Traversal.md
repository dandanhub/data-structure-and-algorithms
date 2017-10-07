## If you are given two traversal sequences, can you construct the binary tree?
If one of the traversal methods is In-order then the tree can be constructed, otherwise not.
**Therefore, following combination can uniquely identify a tree.**
- Inorder and Preorder.
- Inorder and Postorder.
- Inorder and Level-order.

**And following do not.**
- Postorder and Preorder.
- Preorder and Level-order.
- Postorder and Level-order.

Refer to [geeksforgeeks](http://www.geeksforgeeks.org/if-you-are-given-two-traversal-sequences-can-you-construct-the-binary-tree/).


## Full vs Complete
- A full binary tree (sometimes proper binary tree or 2-tree) is a tree in which every node other than the leaves has two children.
- A complete binary tree is a binary tree in which every level, except possibly the last, is completely filled, and all nodes are as far left as possible.

## Serialize and Deserialize a Binary Tree
1. Binary Search tree
Either preorder or postorder
2. Complete Binary Tree
Level-order
3. Full Binary Tree

## 105. Construct Binary Tree from Preorder and Inorder Traversal
Given preorder and inorder traversal of a tree, construct the binary tree.

Note:
You may assume that duplicates do not exist in the tree.

#### Solution
1. 第一点是用map记录inorder每个node的index
2. 第二个点是在递归函数的时候要判断边界是否越界
3. 第三点是递归调用的左右边界控制
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
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || preorder.length == 0
            || inorder == null || inorder.length == 0) return null;

        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }

        return helper(preorder, 0, preorder.length - 1, map, 0);
    }

    private TreeNode helper(int[] preorder, int pl, int pr, Map<Integer, Integer> map, int il) {
        if (pl > pr) return null;

        TreeNode node = new TreeNode(preorder[pl]);
        if (pl == pr) {
            return node;
        }

        int i = map.get(preorder[pl]);
        int len = i - il;
        node.left = helper(preorder, pl + 1, pl + len, map, il);
        node.right = helper(preorder, pl + len + 1, pr, map, i + 1);
        return node;
    }
}
~~~

## 106. Construct Binary Tree from Inorder and Postorder Traversal
Given inorder and postorder traversal of a tree, construct the binary tree.

Note:
You may assume that duplicates do not exist in the tree.

#### Solution
1. 注意和105的区别
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
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if (inorder == null || inorder.length == 0 || postorder == null
            || postorder.length == 0 || inorder.length != postorder.length) {
            return null;
        }

        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }

        return helper(postorder, 0, postorder.length - 1, map, 0);
    }

    private TreeNode helper(int[] postorder, int pl, int pr, Map<Integer, Integer> map, int il) {
        if (pl > pr) return null;

        TreeNode node = new TreeNode(postorder[pr]);
        if (pl == pr) return node;

        int index = map.get(postorder[pr]);
        int len = index - il;
        node.left = helper(postorder, pl, pl + len - 1, map, il);
        node.right = helper(postorder, pl + len, pr - 1, map, index + 1);
        return node;
    }
}
~~~

## 297. Serialize and Deserialize Binary Tree
Serialization is the process of converting a data structure or object into a sequence of bits so that it can be stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or another computer environment.

Design an algorithm to serialize and deserialize a binary tree. There is no restriction on how your serialization/deserialization algorithm should work. You just need to ensure that a binary tree can be serialized to a string and this string can be deserialized to the original tree structure.

For example, you may serialize the following tree
~~~
    1
   / \
  2   3
     / \
    4   5
~~~
as "[1,2,3,null,null,4,5]", just the same as how LeetCode OJ serializes a binary tree. You do not necessarily need to follow this format, so please be creative and come up with different approaches yourself.
Note: Do not use class member/global/static variables to store states. Your serialize and deserialize algorithms should be stateless.

#### Solution
Method 1: Level-order traversal, like LeetCode. (18.99%)
1. serialize用queue, level-order traversal.
2. deserialize用queue, 每次读取两个元素
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
public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        if (root == null) return sb.toString();

        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node != null) {
                sb.append(node.val).append(",");
                queue.offer(node.left);
                queue.offer(node.right);
            }
            else sb.append("#,");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data == null || data.length() == 0) return null;

        String[] tokens = data.split(",");
        if (tokens == null || tokens.length == 0) return null;

        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        TreeNode root = new TreeNode(Integer.valueOf(tokens[0]));
        queue.offer(root);

        int i = 1;
        while (!queue.isEmpty() && i < tokens.length) {
            TreeNode node = queue.poll();
            if (!tokens[i].equals("#")) {
                TreeNode left = new TreeNode(Integer.valueOf(tokens[i]));
                node.left = left;
                queue.offer(left);
            }
            i++;
            if (i < tokens.length && !tokens[i].equals("#")) {
                TreeNode right = new TreeNode(Integer.valueOf(tokens[i]));
                node.right = right;
                queue.offer(right);
            }
            i++;
        }

        return root;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));
~~~

Method 2: Pre-order traversal (68.09%)
1. serialize - 递归调用helper函数，如果是null的话，插入#表示
2. deserialize - 用deque保存所有结点，递归调用helper函数，如果是遇到#(表示null)，则回溯

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
public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if (root == null) return "";
        StringBuilder sb = new StringBuilder();
        serializeHelper(root, sb);
        return sb.toString();
    }

    private void serializeHelper(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append("#,");
        }
        else {
            sb.append(node.val).append(",");
            serializeHelper(node.left, sb);
            serializeHelper(node.right, sb);
        }
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data == null || data.length() == 0) return null;

        String[] tokens = data.split(",");
        Deque<String> deque = new ArrayDeque<String>();
        deque.addAll(Arrays.asList(tokens));
        return deserializeHelper(deque);
    }

    private TreeNode deserializeHelper(Deque<String> deque) {
        String str = deque.poll();
        if (str.equals("#")) { // null
            return null;
        }
        else {
            TreeNode node = new TreeNode(Integer.valueOf(str));
            node.left = deserializeHelper(deque);
            node.right = deserializeHelper(deque);
            return node;
        }
    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));
~~~

## 449. Serialize and Deserialize BST
Serialization is the process of converting a data structure or object into a sequence of bits so that it can be stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or another computer environment.

Design an algorithm to serialize and deserialize a binary search tree. There is no restriction on how your serialization/deserialization algorithm should work. You just need to ensure that a binary search tree can be serialized to a string and this string can be deserialized to the original tree structure.

The encoded string should be as compact as possible.

Note: Do not use class member/global/static variables to store states. Your serialize and deserialize algorithms should be stateless.

#### Solution
由于是BST，所有正常的preorder就可以解决问题。
1. serialize - 递归调用生成preorder
2. deserialize - 用stack，如果下一个结点比栈顶元素小，那么new TreeNode，并且作为栈顶结点的left child；如果下一个结点比栈顶元素大，那么stack.pop()直到栈顶元素比当前元素大，把新节点作为最后pop()元素的right child

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
public class Codec {

   // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if (root == null) return "";
        if (root.left == null && root.right == null) {
            return String.valueOf(root.val);
        }

        StringBuilder sb = new StringBuilder();
        sb.append(root.val).append(",");
        if (root.left != null) sb.append(serialize(root.left)).append(",");
        if (root.right != null) sb.append(serialize(root.right)).append(",");
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data == null || data.length() == 0) {
          return null;
        }
        String[] tokens = data.split(",");
        if (tokens == null || tokens.length == 0) {
          return null;  
        }

        TreeNode root = new TreeNode(Integer.valueOf(tokens[0]));
        Stack<TreeNode> stack = new Stack<TreeNode>();
        stack.push(root);
        int i = 1;
        while (i < tokens.length) { // TODO
          TreeNode child = new TreeNode(Integer.valueOf(tokens[i]));
          if (child.val < stack.peek().val) {
            stack.peek().left = child;
            stack.push(child);
          }
          else {
            TreeNode parent = stack.peek();
            while (!stack.isEmpty() && child.val > stack.peek().val) {
              parent = stack.pop();
            }
            parent.right = child;
            stack.push(child);
          }
          i++;
        }

      return root;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));
~~~

## 144. Binary Tree Preorder Traversal

Given a binary tree, return the preorder traversal of its nodes' values.

For example:
Given binary tree {1,#,2,3},
~~~
   1
    \
     2
    /
   3
~~~
return [1,2,3].

Note: Recursive solution is trivial, could you do it iteratively?

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
public class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<Integer>();
        if (root == null) return list;

        Stack<TreeNode> stack = new Stack<TreeNode>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode curr = stack.pop();
            list.add(curr.val);
            if (curr.right != null) stack.push(curr.right);
            if (curr.left != null) stack.push(curr.left);
        }
        return list;
    }
}
~~~

## 94. Binary Tree Inorder Traversal

Given a binary tree, return the inorder traversal of its nodes' values.

For example:
~~~
Given binary tree [1,null,2,3],
   1
    \
     2
    /
   3
~~~
return [1,3,2].

Note: Recursive solution is trivial, could you do it iteratively?

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
public class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<Integer>();
        if (root == null) return list;

        TreeNode curr = root;
        Stack<TreeNode> stack = new Stack<TreeNode>();
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            TreeNode node = stack.pop();
            list.add(node.val);
            curr = node.right;
        }

        return list;
    }
}
~~~

## 145. Binary Tree Postorder Traversal
Given a binary tree, return the postorder traversal of its nodes' values.

For example:
Given binary tree {1,#,2,3},
~~~
   1
    \
     2
    /
   3
~~~
return [3,2,1].

Note: Recursive solution is trivial, could you do it iteratively?

#### Solution
关键是用一个last指针记录最后访问的结点，当访问到一个root的时候，如果root.right != null, 需要判断其right支树是否被访问过，判断的条件是root.right != last.

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
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<Integer>();
        if (root == null) {
            return list;
        }

        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode curr = root;
        TreeNode last = null;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }

            TreeNode node = stack.peek();
            if (node.right != null && node.right != last) {
                curr = node.right;
            }
            else {
                list.add(node.val);
                last = node;
                stack.pop();
            }
        }

        return list;
    }
}
~~~

## 230. Kth Smallest Element in a BST (++)

Given a binary search tree, write a function kthSmallest to find the kth smallest element in it.

Note:
You may assume k is always valid, 1 ? k ? BST's total elements.

Follow up:
What if the BST is modified (insert/delete operations) often and you need to find the kth smallest frequently? How would you optimize the kthSmallest routine?

#### Solution
Method 1: Do inorder traversal, because BST, the Kth smallest element is the Kth element in inorder traversal.

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
    public int kthSmallest(TreeNode root, int k) {
        TreeNode curr = root;
        TreeNode node = null;
        Stack<TreeNode> stack = new Stack<TreeNode>();
        while (k > 0 && (curr != null || !stack.isEmpty())) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }

            node = stack.pop();
            k--;
            curr = node.right;
        }

        return node.val;
    }
}
~~~
