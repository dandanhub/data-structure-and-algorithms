// Method 1: Merge Sort

class Solution {
    class Node {
        int index;
        int value;
        int count;

        public Node(int index, int value) {
            this.index = index;
            this.value = value;
        }
    }

    public List<Integer> countSmaller(int[] nums) {
        List<Integer> list = new ArrayList<Integer>();

        // sanity check
        if (nums == null || nums.length == 0) return list;

        List<Node> nodes = new ArrayList<Node>();
        for (int i = 0; i < nums.length; i++) {
            list.add(0);
            Node node = new Node(i, nums[i]);
            nodes.add(node);
        }

        mergeSort(nodes);

        for (int i = 0; i < nodes.size(); i++) {
            int index = nodes.get(i).index;
            int count = nodes.get(i).count;
            list.set(index, count);
        }

        return list;
    }

    private void mergeSort(List<Node> nodes) {
        if (nodes.size() <= 1) {
            return;
        }

        int mid = nodes.size() / 2;

        // slit left half and sort
        List<Node> leftNodes = new ArrayList<Node>();
        for (int i = 0; i < mid; i++) {
            leftNodes.add(nodes.get(i));
        }
        mergeSort(leftNodes);

        // slit right half and sort
        List<Node> rightNodes = new ArrayList<Node>();
        for (int i = mid; i < nodes.size(); i++) {
            rightNodes.add(nodes.get(i));
        }
        mergeSort(rightNodes);

        // merge left half and right half
        int i = 0;
        int j = 0;
        int index = 0;
        while (i < leftNodes.size()) {
            while (j < rightNodes.size()
                   && leftNodes.get(i).value > rightNodes.get(j).value) {
                nodes.set(index++, rightNodes.get(j++));
            }
            // add the count of smaller number
            Node node = leftNodes.get(i);
            node.count += j;
            nodes.set(index++, leftNodes.get(i++));
        }
        while (j < rightNodes.size()) {
            nodes.set(index++, rightNodes.get(j++));
        }
    }
}


// Method 2: BST

public class Solution {
    class Node {
        Node left, right;
        int val, sum, dup = 1;
        public Node(int v, int s) {
            val = v;
            sum = s;
        }
    }
    public List<Integer> countSmaller(int[] nums) {
        Integer[] ans = new Integer[nums.length];
        Node root = null;
        for (int i = nums.length - 1; i >= 0; i--) {
            root = insert(nums[i], root, ans, i, 0);
        }
        return Arrays.asList(ans);
    }
    private Node insert(int num, Node node, Integer[] ans, int i, int preSum) {
        if (node == null) {
            node = new Node(num, 0);
            ans[i] = preSum;
        } else if (node.val == num) {
            node.dup++;
            ans[i] = preSum + node.sum;
        } else if (node.val > num) {
            node.sum++;
            node.left = insert(num, node.left, ans, i, preSum);
        } else {
            node.right = insert(num, node.right, ans, i, preSum + node.dup + node.sum);
        }
        return node;
    }
}
