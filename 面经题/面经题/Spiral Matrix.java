// ## 54. Spiral Matrix
// Given a matrix of m x n elements (m rows, n columns), return all elements of the matrix in spiral order.
//
// For example,
// Given the following matrix:
// ~~~
// [
//  [ 1, 2, 3 ],
//  [ 4, 5, 6 ],
//  [ 7, 8, 9 ]
// ]
// ~~~
// You should return [1,2,3,6,9,8,7,4,5].
//
// #### Solution
// 注意是循环外面改变up, down, left, right
public class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> list = new ArrayList<Integer>();
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return list;

        int m = matrix.length, n = matrix[0].length;
        int up = 0;
        int down = m - 1;
        int left = 0;
        int right = n - 1;

        while (list.size() < m * n) {
            // from left to right
            for (int j = left; j <= right; j++) {
                list.add(matrix[up][j]);
            }
            up++;

            if (list.size() == m * n) break;

            // from up tp down
            for (int i = up; i <= down; i++) {
                list.add(matrix[i][right]);
            }
            right--;

            if (list.size() == m * n) break;

            // from right to left
            for (int j = right; j >= left; j--) {
                list.add(matrix[down][j]);
            }
            down--;

            if (list.size() == m * n) break;

            // from down to up
            for (int i = down; i >= up; i--) {
                list.add(matrix[i][left]);
            }
            left++;
        }

        return list;
    }
}

// ## 59. Spiral Matrix II
// Given an integer n, generate a square matrix filled with elements from 1 to n2 in spiral order.
//
// For example,
// Given n = 3,
//
// You should return the following matrix:
// ~~~
// [
//  [ 1, 2, 3 ],
//  [ 8, 9, 4 ],
//  [ 7, 6, 5 ]
// ]
// ~~~
//
// #### Solution
// 简单题目，注意数字从1开始
public class Solution {
    public int[][] generateMatrix(int n) {

        // edge case
        if (n == 0) return new int[0][0];

        n = Math.abs(n);
        int[][] matrix = new int[n][n];

        int up = 0;
        int down = n - 1;
        int left = 0;
        int right = n - 1;

        int total = n * n;
        int count = 0;
        while (count < total) {
            // from left to right, along up row
            for (int i = left; i <= right; i++) {
                matrix[up][i] = ++count;
            }
            up++;
            if (count == total) break;

            // from up to down, along right col
            for (int i = up; i <= down; i++) {
                matrix[i][right] = ++count;
            }
            right--;
            if (count == total) break;

            // from right to left, along down row
            for (int i = right; i >= left; i--) {
                matrix[down][i] = ++count;
            }
            down--;
            if (count == total) break;

            // from down to up, along left col
            for (int i = down; i >= up; i--) {
                matrix[i][left] = ++count;
            }
            left++;
            if (count == total) break;
        }
        return matrix;
    }
}
