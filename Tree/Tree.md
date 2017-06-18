# Path Sum
## 112. Path Sum (Easy)
Given a binary tree and a sum, determine if the tree has a root-to-leaf path such that adding up all the values along the path equals the given sum.

For example:
Given the below binary tree and sum = 22,
~~~
              5
             / \
            4   8
           /   / \
          11  13  4
         /  \      \
        7    2      1
~~~
return true, as there exist a root-to-leaf path 5->4->11->2 which sum is 22.

#### Solution
Recursive function, base case is when reaching leaf.

Time Complexity: O(n)
Space Complexity: O(n) due to recursion
Attempt: 2 (return false if root is null)
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
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) return false; // null node
        if (root.left == null && root.right == null) return sum == root.val; // leaf node

        if (root.left != null && hasPathSum(root.left, sum - root.val)) {
            return true;
        }

        if (root.right != null && hasPathSum(root.right, sum - root.val)) {
            return true;
        }

        return false;
    }
}
~~~

## 113. Path Sum II (Medium)
Given a binary tree and a sum, find all root-to-leaf paths where each path's sum equals the given sum.

For example:
Given the below binary tree and sum = 22,
~~~~
              5
             / \
            4   8
           /   / \
          11  13  4
         /  \    / \
        7    2  5   1
~~~~

return
~~~~
[
   [5,4,11,2],
   [5,8,4,5]
]
~~~~

#### Solution
Backtracking based on Path Sum 1.

Time Complexity: O(n) <br>
Space Complexity: the ans size <br>
Attempt: 2 **注意最后要把叶子节点加进来**
~~~
public List<List<Integer>> pathSum(TreeNode root, int sum) {
    List<List<Integer>> ans = new ArrayList<List<Integer>>();
    if (root == null) return ans;
    searchPath(ans, root, new ArrayList<Integer>(), sum);
    return ans;
}

public void searchPath(List<List<Integer>> list, TreeNode node, List<Integer> path, int sum) {
    if (node.left == null && node.right == null && node.val == sum) { // leaf
        List<Integer> ele = new ArrayList<Integer>(path); // 注意这里要把叶子节点加进来
        ele.add(node.val);
        list.add(ele);
    }

    // List<Integer> ele = new ArrayList<Integer>(path);
    path.add(node.val);
    if (node.left != null) searchPath(list, node.left, path, sum - node.val);
    if (node.right != null) searchPath(list, node.right, path, sum - node.val);
    path.remove(path.size() - 1);
}

~~~

## 437. Path Sum III (Medium) *
You are given a binary tree in which each node contains an integer value.

Find the number of paths that sum to a given value.

The path does not need to start or end at the root or a leaf, but it must go downwards (traveling only from parent nodes to child nodes).

The tree has no more than 1,000 nodes and the values are in the range -1,000,000 to 1,000,000.

Example:
~~~
root = [10,5,-3,3,2,null,11,3,-2,null,1], sum = 8

      10
     /  \
    5   -3
   / \    \
  3   2   11
 / \   \
3  -2   1

Return 3. The paths that sum to 8 are:

1.  5 -> 3
2.  5 -> 2 -> 1
3. -3 -> 11
~~~

#### Solution
1. DFS solution.
我们有两个递归函数：
- func1 一个是计算包含当前根节点并且path sum等于target的路径个数
- func2 另一个是计算不包含当前根节点的并且path sum等于target的路径个数。 <br>
func1 在递归调用的时候，因为限定了路径必须包含当前（和调用函数的根节点），所以只能考虑左右子树满足func1的个数 <br>
func2 在递归调用的时候，需要同时考虑左右子树满足func1和func2的个数
2. 如何加快速度，在O(n)时间内解决，看到讨论帖[discussion](https://discuss.leetcode.com/topic/64526/17-ms-o-n-java-prefix-sum-method/13)
- The idea is that we cal sum of path from root to the current node.
- Then we check whether there exits a path ending at the current node that have path sum equals target. <br>
How to check this? <br>
We can check whether there is a path from root to any node along the path has a prefix path sum equals to prefixSum[current node] - target.

Method 1: <br>
Time complexity: O(h^2) h is the height of the tree <br>
Space complexity: O(n) due to recursion <br>
Attempt: 2 (typo bug)
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
    public int pathSum(TreeNode root, int sum) {
        if (root == null) return 0;

        int count = 0;

        // base case
        if (sum == root.val) {
            count += 1;
        }

        if (root.left != null) {
            count += pathSum(root.left, sum); // not including the curr node
            count += pathSumHelp(root.left, sum - root.val); // including the curr node
        }

        if (root.right != null) {
            count += pathSum(root.right, sum); // not including the curr node
            count += pathSumHelp(root.right, sum - root.val); // including the curr node
        }

        return count;
    }

    public int pathSumHelp(TreeNode root, int sum) {
        int count = 0;
        if (sum == root.val) {
            count += 1;
        }

         if (root.left != null) {
            count += pathSumHelp(root.left, sum - root.val); // not including the curr node
        }

        if (root.right != null) {
            count += pathSumHelp(root.right, sum - root.val); // not including the curr node
        }

        return count;
    }
}
~~~

Method 2: <br>
Time complexity: O(n) each node is visited once <br>
Space complexity: O(n) map to store prefix sum <br>
Attempt: 2 (the order of add element to map matters)
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
    public int pathSum(TreeNode root, int sum) {
        if (root == null) return 0;
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(0, 1); // put initial prefix sum
        return pathSumHelper(root, 0, sum, map);
    }

    private int pathSumHelper(TreeNode root, int sum, int target, Map<Integer, Integer> map) {
        if (root == null) return 0;

        sum += root.val;

        int count = 0;

        // bug version, failed test case [1] 0
        // put the prefix sum into map
        // map.put(sum, map.getOrDefault(sum, 0) + 1);
        // check whether any preceeding node along the path having prefix sum = sum - target
        // count += map.getOrDefault(sum - target, 0);
        count += map.getOrDefault(sum - target, 0);
        map.put(sum, map.getOrDefault(sum, 0) + 1);

        // check left and right children tree
        count += pathSumHelper(root.left, sum, target, map) + pathSumHelper(root.right, sum, target, map);

        // remove curr node from map when backtracking
        map.put(sum, map.get(sum) - 1);
        return count;
    }
}
~~~

~~~
follow-up 如果这题需要输出所有路径怎么做？
~~~

## 129. Sum Root to Leaf Numbers (Easy)
Given a binary tree containing digits from 0-9 only, each root-to-leaf path could represent a number.

An example is the root-to-leaf path 1->2->3 which represents the number 123.

Find the total sum of all root-to-leaf numbers.

For example,
~~~
    1
   / \
  2   3
~~~
The root-to-leaf path 1->2 represents the number 12.
The root-to-leaf path 1->3 represents the number 13.

Return the sum = 12 + 13 = 25.

#### Solution
递归函数，两个参数，1) cur node and 2) cur sum, 递归调用的时候把sum的值*10 <br>

Time: O(n) visit each node once <br>
Space: O(n) due to recursion <br>
Attempt: 2 (typo bug)
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
    public int sumNumbers(TreeNode root) {
        if (root == null) return 0;
        return sumNumbersHelper(root, 0);
    }

    private int sumNumbersHelper(TreeNode root, int sum) {
        if (root == null) return sum;

        int res = sum * 10 + root.val;
        if (root.left == null && root.right == null) { // leaf
            return res;
        }

        int ans = 0;
        if (root.left != null) {
            // ans += sumNumbersHelper(root.left, sum); // typo bug
            ans += sumNumbersHelper(root.left, res);
        }

        if (root.right != null) {
            // ans += sumNumbersHelper(root.right, sum); // typo bug
            ans += sumNumbersHelper(root.right, res);
        }

        return ans;
    }
}
~~~

## 124. Binary Tree Maximum Path Sum (Medium)
Given a binary tree, find the maximum path sum.

For this problem, a path is defined as any sequence of nodes from some starting node to any node in the tree along the parent-child connections. The path must contain at least one node and does not need to go through the root.

For example:
Given the below binary tree,
~~~
       1
      / \
     2   3
~~~
Return 6.

#### Solution
A recursive method maxPathDown(TreeNode node) (1) computes the maximum path sum with highest node is the input node, update maximum if necessary (2) returns the maximum sum of the path that can be extended to input node's parent.
From https://discuss.leetcode.com/topic/4407/accepted-short-solution-in-java

Time: O(n) each node is visited once
Space: O(n) due to recursion
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
    // int maxSum; // bug, consider test case [-3]
    int maxSum = Integer.MIN_VALUE; // set the initial value of Integer.MIN_VALUE taking consider of negative value

    public int maxPathSum(TreeNode root) {
        maxPathSumHelper(root);
        return maxSum;
    }

    private int maxPathSumHelper(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) {
            maxSum = Math.max(maxSum, root.val);
            return root.val;
        }

        int left = Math.max(0, maxPathSumHelper(root.left));
        int right = Math.max(0, maxPathSumHelper(root.right));

        maxSum = Math.max(maxSum, root.val + left + right);
        return root.val + Math.max(left, right);
    }
}
~~~

~~~
follow up: 如果是找最小的path？
Amazon OA2 10.4
另一题是BT找最小path
~~~

## 333. Largest BST Subtree (Medium) *
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
2. 父节点小于右树所有节点 <br>
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
