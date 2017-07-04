# Binary Index Tree and Segment Tree
## 303. Range Sum Query - Immutable (Easy)
Given an integer array nums, find the sum of the elements between indices i and j (i ≤ j), inclusive.

Example:
~~~
Given nums = [-2, 0, 3, -5, 2, -1]

sumRange(0, 2) -> 1
sumRange(2, 5) -> -1
sumRange(0, 5) -> -3
~~~

Note:
You may assume that the array does not change.
There are many calls to sumRange function.

#### Solution
Easy one.

**注意生成sum数组时，int[] sums = new int[len + 1], 和edge cases的处理**

~~~
public class NumArray {

    int[] sums;

    public NumArray(int[] nums) {
        if (nums == null || nums.length == 0) return;

        this.sums = new int[nums.length + 1];
        sums[0] = 0;
        for (int i = 1; i < nums.length + 1; i++) {
            sums[i] = nums[i - 1] + sums[i - 1];
        }
    }

    public int sumRange(int i, int j) {
        if (i < 0) i = 0;
        if (j >= sums.length - 1) j = sums.length - 2;

        return sums[j + 1] - sums[i];
    }
}

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * int param_1 = obj.sumRange(i,j);
 */
 ~~~

## 307. Range Sum Query - Mutable
Given an integer array nums, find the sum of the elements between indices i and j (i ≤ j), inclusive.

The update(i, val) function modifies nums by updating the element at index i to val.
Example:
~~~
Given nums = [1, 3, 5]

sumRange(0, 2) -> 9
update(1, 2)
sumRange(0, 2) -> 8
~~~
Note:
The array is only modifiable by the update function.
You may assume the number of calls to update and sumRange function is distributed evenly.

#### Solution
1. 这题简单的方法很容易想到，但是面临的代价是要么update O(n)，getSum O(1)；要么update O(1), getSum O(n), 题目中说明You may assume the number of calls to update and sumRange function is distributed evenly, 如何做到update和getSum两个操作的耗时even是关键
2. 这题引入两个数据结构segment tree和BIT， geeksforgeeks网站上有很好的解释，这样可以把getSum和update的复杂度都讲到O(logn)

###### Segment Tree
类似于二分，根节点存[0-n]的sum, 然后分成左右子树，左结点存[0-n/2]的sum，右结点错[n/2-n]的sum，然后递归生成左右子树。<br>
**复杂度分析，初期建树，总共有O(2n-1)个结点，space O(2n-1), time O(2n-1)**；每次查询和更新的复杂度都是O(logn)

###### BIT
1. The idea of BIT is that every positive integer can be represented using power of 2, e.g. 12 can be represented as 8 + 4. So, if we want to get the sum(0, 12), what we can do is sum(0, 8) + sum(8, 12).
2. For getSum query, we can build BIT that the parent of a node (i) is with index i - (i & (-i)), e.g. the parent index of node(12) is 8. The value of every node is the sum from its parent (exclusive) to the the node (inclusive), e.g. node(12) has a value of sum(9, 12). Therefore, for getSum(12), we add up node with index 12, and parent nodes index - (index & (-index))
3. For update operation, e.g. if we update num[0], we need to update all nodes have value range from 0 to itself, let's say it is a array of length 12, so we need to update node(1), node(2), node(4), node(8). Therefore, for update(0), the starting index is node(1), and we will also update all nodes with index + (index & (-index)).

BIT cost O(n + 1) space, and build tree cost O(nlogn) time. Each update and getSum is O(logn)

Attempts: 4
~~~
public class NumArray {
    private int[] nums;
    private int[] BIT;

    public NumArray(int[] nums) {
        if (nums == null || nums.length == 0) return;
        int n = nums.length;
        this.nums = new int[n];
        BIT = new int[n + 1];
        for (int i = 0; i < n; i++) {
            update(i, nums[i]);
        }  
    }

    public void update(int i, int val) {
        int diff = val - nums[i];
        nums[i] = val;
        for (int j = i + 1; j < BIT.length; j += j & (-j)) {
            BIT[j] += diff;
            // System.out.println(BIT[j]);
        }
    }

    public int sumRange(int i, int j) {
        if (i < 0) i = 0;
        if (j > nums.length) j = nums.length - 1;
        return sumRangeHelper(j) - sumRangeHelper(i) + nums[j];
    }

    private int sumRangeHelper(int i) {
        int sum = 0;
        for (int j = i; j > 0; j -= j & (-j)) {
            sum += BIT[j];
        }
        return sum;
    }
}

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * obj.update(i,val);
 * int param_2 = obj.sumRange(i,j);
 */
~~~

## 304. Range Sum Query 2D - Immutable
Given a 2D matrix matrix, find the sum of the elements inside the rectangle defined by its upper left corner (row1, col1) and lower right corner (row2, col2).

Range Sum Query 2D
The above rectangle (with the red border) is defined by (row1, col1) = (2, 1) and (row2, col2) = (4, 3), which contains sum = 8.

Example:
~~~
Given matrix = [
  [3, 0, 1, 4, 2],
  [5, 6, 3, 2, 1],
  [1, 2, 0, 1, 5],
  [4, 1, 0, 1, 7],
  [1, 0, 3, 0, 5]
]

sumRegion(2, 1, 4, 3) -> 8
sumRegion(1, 1, 2, 2) -> 11
sumRegion(1, 2, 2, 4) -> 12
~~~

#### Solution
1. 注意生成2D的sums数组长宽分别+1

Attempts: 2 （处理edge cases花了些时间，面试的时候和面试官确认）
~~~
public class NumMatrix {

    int[][] sums;

    public NumMatrix(int[][] matrix) {
        // edge case
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            sums = new int[0][0];
            return;    
        }

        sums = new int[matrix.length + 1][matrix[0].length + 1];
        for (int i = 1; i < sums.length; i++) {
            for (int j = 1; j < sums[0].length; j++) {
                sums[i][j] = matrix[i - 1][j - 1] + sums[i - 1][j] + sums[i][j - 1] - sums[i - 1][j - 1];
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        // edge cases
        // You may assume that row1 ≤ row2 and col1 ≤ col2.
        if (sums.length == 0 || sums[0].length == 0) return 0;
        if (row1 < 0) row1 = 0;
        if (row2 < 0) return 0;
        if (col1 < 0) col1 = 0;
        if (col2 < 0) return 0;

        if (row2 >= sums.length) row2 = sums.length - 1;
        if (row1 >= sums.length) return 0;
        if (col2 >= sums[0].length) col2 = sums[0].length - 1;
        if (col1 >= sums[0].length) return 0;

        return sums[row2 + 1][col2 + 1] - sums[row1][col2 + 1] - sums[row2 + 1][col1] + sums[row1][col1];
    }
}

/**
 * Your NumMatrix object will be instantiated and called as such:
 * NumMatrix obj = new NumMatrix(matrix);
 * int param_1 = obj.sumRegion(row1,col1,row2,col2);
 */
 ~~~


## 308. Range Sum Query 2D - Mutable

Given a 2D matrix matrix, find the sum of the elements inside the rectangle defined by its upper left corner (row1, col1) and lower right corner (row2, col2).

Range Sum Query 2D
The above rectangle (with the red border) is defined by (row1, col1) = (2, 1) and (row2, col2) = (4, 3), which contains sum = 8.

Example:
~~~
Given matrix = [
  [3, 0, 1, 4, 2],
  [5, 6, 3, 2, 1],
  [1, 2, 0, 1, 5],
  [4, 1, 0, 1, 7],
  [1, 0, 3, 0, 5]
]

sumRegion(2, 1, 4, 3) -> 8
update(3, 2, 2)
sumRegion(2, 1, 4, 3) -> 10
~~~

Note:
The matrix is only modifiable by the update function.
You may assume the number of calls to update and sumRegion function is distributed evenly.
You may assume that row1 ≤ row2 and col1 ≤ col2.

#### Solution
1. Update and Query O(n) time
2. 2D BIT
3. 2D Segment Tree

O(n)
~~~
public class NumMatrix {

    int[][] sum;

    public NumMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return;
        sum = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            sum[i][0] = matrix[i][0];
            for (int j = 1; j < matrix[0].length; j++) {
                sum[i][j] = sum[i][j - 1] + matrix[i][j];
            }
        }
    }

    public void update(int row, int col, int val) {
        if (row < 0 || row >= sum.length || col < 0 || col >= sum[0].length) return;

        int old = sum[row][col];
        if (col > 0) old -= sum[row][col - 1];
        int diff = val - old;

        // O(n)
        for (int j = col; j < sum[0].length; j++) {
            sum[row][j] += diff;
        }
        // System.out.println(Arrays.toString(sum[row]));
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        // O(n)
        int ans = 0;
        for (int i = row1; i <= row2; i++) {
            if (col1 == 0) ans += sum[i][col2];
            else ans += sum[i][col2] - sum[i][col1 - 1];
        }
        return ans;
    }
}

/**
 * Your NumMatrix object will be instantiated and called as such:
 * NumMatrix obj = new NumMatrix(matrix);
 * obj.update(row,col,val);
 * int param_2 = obj.sumRegion(row1,col1,row2,col2);
 */
~~~

Attempts: 4 注意调用getSum的边界
~~~
public class NumMatrix {
    int[][] matrix;
    int[][] sums;

    public NumMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return;
        }

        this.matrix = new int[matrix.length][matrix[0].length];
        this.sums = new int[matrix.length + 1][matrix[0].length + 1];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                update(i, j, matrix[i][j]);
            }
        }
    }

    public void update(int row, int col, int val) {
        // edge case
        if (matrix == null) return;

        if (row < 0 || row >= matrix.length || col < 0 || col >= matrix[0].length) return;
        int diff = val - matrix[row][col];
        matrix[row][col] = val;
        for (int i = row + 1; i < sums.length; i += i & (-i)) {
            for (int j = col + 1; j < sums[0].length; j += j & (-j)) {
                sums[i][j] += diff;
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        // edge cases
        if (sums == null) return 0;
        // You may assume that row1 ≤ row2 and col1 ≤ col2.
        if (row2 < 0 || row1 >= matrix.length || col2 < 0 || col1 >= matrix[0].length) return 0;
        if (row1 < 0) row1 = 0;
        if (col1 < 0) col1 = 0;
        if (row2 >= matrix.length) row2 = matrix.length - 1;
        if (col2 >= matrix[0].length) col2 = matrix[0].length - 1;

        return getSum(row2 + 1, col2 + 1) - getSum(row1, col2 + 1) - getSum(row2 + 1, col1) + getSum(row1, col1);

    }

    private int getSum(int row, int col) {
        int ans = 0;
        for (int i = row; i > 0; i -= i & (-i)) {
            for (int j = col; j > 0; j -= j & (-j)) {
                ans += sums[i][j];
            }
        }
        return ans;
    }
}

/**
 * Your NumMatrix object will be instantiated and called as such:
 * NumMatrix obj = new NumMatrix(matrix);
 * obj.update(row,col,val);
 * int param_2 = obj.sumRegion(row1,col1,row2,col2);
 */
~~~
