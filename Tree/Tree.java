public class Tree {
  // 96. Unique Binary Search Trees
  public int numTrees(int n) {
        if (n == 0 || n == 1) return 1;
        if (n == 2) return 2;

        int[] count = new int[n + 1];
        count[0] = 1;
        count[1] = 1;
        count[2] = 2;
        for (int i = 3; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                count[i] += count[j - 1] * count[i - j];
            }
        }

        return count[n];
    }

  // 95. Unique Binary Search Trees II
  public List<TreeNode> generateTrees(int n) {
        if (n <= 0) return new ArrayList<TreeNode>();
        return generateTreesHelper(1, n);
  }

  public List<TreeNode> generateTreesHelper(int start, int end) {
      if (start > end) {
          List<TreeNode> list = new ArrayList<TreeNode>();
          list.add(null);
          return list;
      }

      if (start == end) {
          TreeNode root = new TreeNode(start);
          List<TreeNode> list = new ArrayList<TreeNode>();
          list.add(root);
          return list;
      }

      List<TreeNode> list = new ArrayList<TreeNode>();
      for (int i = start; i <= end; i++) {
          List<TreeNode> leftnodes = generateTreesHelper(start, i - 1);
          List<TreeNode> rightnodes = generateTreesHelper(i + 1, end);
          for (TreeNode leftnode : leftnodes) {
              for (TreeNode rightnode : rightnodes) {
                  TreeNode root = new TreeNode(i);
                  root.left = leftnode;
                  root.right = rightnode;
                  list.add(root);
              }
          }
      }

      return list;
  }

  // 98. Validate Binary Search Tree
  // public boolean isValidBST(TreeNode root) {
  //       List<Integer> ans = new ArrayList<Integer>();
  //       inorder(root, ans);
  //       for (int i = 1; i < ans.size(); i++) {
  //           if (ans.get(i) <= ans.get(i-1)) {
  //               return false;
  //           }
  //       }
  //       return true;
  // }
  //
  // private void inorder(TreeNode root, List<Integer> ans) {
  //       if (root == null) {
  //           return;
  //       }
  //       inorder(root.left, ans);
  //       ans.add(root.val);
  //       inorder(root.right, ans);
  // }

  // 98. Validate Binary Search Tree
  public boolean isValidBST(TreeNode root) {
        return isValidBSTHelper(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

  private boolean isValidBSTHelper(TreeNode root, long min, long max) {
        if (root == null) {
            return true;
        }
        if (root.val <= min || root.val >= max) {
            return false;
        }
        return isValidBSTHelper(root.left, min, root.val) && isValidBSTHelper(root.right, root.val, max);
  }

  // 100. Same Tree
  public boolean isSameTree(TreeNode p, TreeNode q) {
       if (p == null && q == null) {
           return true;
       }
       if ((p == null && q != null) || (p != null && q == null)) {
           return false;
       }
       if (p.val != q.val) {
           return false;
       }
       return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
   }

   // 101. Symmetric Tree
   public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isSymmetricHelper(root.left, root.right);
    }

    private boolean isSymmetricHelper(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) {
            return true;
        }
        if ((root1 == null && root2 != null) || (root1 != null && root2 == null)) {
            return false;
        }
        if (root1.val != root2.val) {
            return false;
        }
        return isSymmetricHelper(root1.left, root2.right) && isSymmetricHelper(root1.right, root2.left);
    }

  // 543. Diameter of Binary Tree
  public int diameterOfBinaryTree(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return 0;
        }

        int left = depth(root.left);
        int right = depth(root.right);
        int sum = left + right;

        int leftDia = diameterOfBinaryTree(root.left);
        int rightDia = diameterOfBinaryTree(root.right);
        int maxDia = Math.max(leftDia, rightDia);

        return Math.max(sum, maxDia);
    }

    private int depth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return Math.max(depth(root.left), depth(root.right)) + 1;
    }
    
    // 124. Binary Tree Maximum Path Sum
    private int ans = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        maxPathSumHelper(root);
        return ans;
    }

    private int maxPathSumHelper(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = maxPathSumHelper(root.left);
        int right = maxPathSumHelper(root.right);
        int temp = Math.max(left, 0) + Math.max(right, 0) + root.val;
        ans = Math.max(temp, ans);
        return Math.max(Math.max(left, right), 0) + root.val;
    }

    // 538. Convert BST to Greater Tree
    // Method 1: Naiive Way
    // public TreeNode convertBST(TreeNode root) {
    //     if (root == null) {
    //         return root;
    //     }
    //     convertBSTHelper(root, 0);
    //     return root;
    // }
    //
    // private void convertBSTHelper(TreeNode node, int parent) {
    //     if (node == null) {
    //         return;
    //     }
    //
    //     int right = sumTree(node.right);
    //     int sum = node.val + right + parent;
    //     node.val = sum;
    //
    //     convertBSTHelper(node.left, sum);
    //     convertBSTHelper(node.right, parent);
    // }
    //
    // private int sumTree(TreeNode root) {
    //     if (root == null) {
    //         return 0;
    //     }
    //     int left = sumTree(root.left);
    //     int right = sumTree(root.right);
    //     int sum = left + right + root.val;
    //     return sum;
    // }

    // 538. Convert BST to Greater Tree
    private int sum = 0;
    public TreeNode convertBST(TreeNode root) {
        if (root == null) {
            return root;
        }
        convertBST(root.right);
        sum += root.val;
        root.val = sum;
        convertBST(root.left);
        return root;
    }
}
