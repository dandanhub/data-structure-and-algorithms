The file summarizes problems involved 2D board.

## 361. Bomb Enemy
Given a 2D grid, each cell is either a wall 'W', an enemy 'E' or empty '0' (the number zero), return the maximum enemies you can kill using one bomb.
The bomb kills all the enemies in the same row and column from the planted point until it hits the wall since the wall is too strong to be destroyed.
Note that you can only put the bomb at an empty cell.

Example:
~~~
For the given grid

0 E 0 0
E 0 W E
0 E 0 0

return 3. (Placing a bomb at (1,1) kills 3 enemies)
~~~

#### Solution
Method 1: Naiive Solution - 遍历每个点，然后统计能够杀死的个数 <br>
Time: O(mn(m+n)); Space: O(1) <br>

Method 2: Store one killed enemies value for a row and an array of each column <br>
Time: O(mn); Space: O(n)
~~~
public class Solution {
    public int maxKilledEnemies(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;
        int m = grid.length, n = grid[0].length;

        // init cols
        int[] cols = new int[n];
        for (int i = 0; i < n; i++) {
            updateEnemiesCols(grid, cols, 0, i);
        }

        int ans = 0;
        for (int i = 0; i < m; i++) {
            int row = updateEnemiesRow(grid, i, 0);
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '0') {
                    ans = Math.max(ans, row + cols[j]);
                }
                else if (grid[i][j] == 'W') {
                    if (j + 1 < n) row = updateEnemiesRow(grid, i, j + 1);
                    if (i + 1 < m) updateEnemiesCols(grid, cols, i + 1, j);
                }
            }
        }
        return ans;
    }

    private void updateEnemiesCols(char[][] grid, int[] cols, int x, int y) {
        cols[y] = 0;
        for (int i = x; i < grid.length; i++) {
            if (grid[i][y] == 'E') {
                cols[y]++;
            }
            else if (grid[i][y] == 'W') break;
        }
    }

    private int updateEnemiesRow(char[][] grid, int x, int y) {
        int count = 0;
        for (int j = y; j < grid[0].length; j++) {
            if (grid[x][j] == 'E') count++;
            else if (grid[x][j] == 'W') break;
        }
        return count;
    }
}
~~~

## 419. Battleships in a Board
Given an 2D board, count how many battleships are in it. The battleships are represented with 'X's, empty slots are represented with '.'s. You may assume the following rules:

You receive a valid board, made of only battleships or empty slots.
Battleships can only be placed horizontally or vertically. In other words, they can only be made of the shape 1xN (1 row, N columns) or Nx1 (N rows, 1 column), where N can be of any size.
At least one horizontal or vertical cell separates between two battleships - there are no adjacent battleships.
Example:
~~~
X..X
...X
...X
~~~

In the above board there are 2 battleships.
Invalid Example:
~~~
...X
XXXX
...X
~~~
This is an invalid board that you will not receive - as battleships will always have a cell separating between them.

Follow up:
Could you do it in one-pass, using only O(1) extra memory and without modifying the value of the board?

#### Solution
没太看懂题意

~~~
public class Solution {
    public int countBattleships(char[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) return 0;

        int m = board.length, n = board[0].length;
        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == '.') continue;
                if (board[i][j] == 'X' && j > 0 && board[i][j - 1] == 'X') continue;
                if (board[i][j] == 'X' && i > 0 && board[i - 1][j] == 'X') continue;
                count++;
            }
        }
        return count;
    }
}
~~~

~~~
battleShip game, 一个二维数组，里面有ship，1表示ship，ship可以是垂直的也可以是横向的，然后有不同type的ship（根据长度不同而不同），写一个方法，判断是否击中，写第二个方法，判断是击中了，还是Miss了，还是击沉了（全部长度都击中了），方法参数是x,y 表示二维数组的坐标
~~~

## 54. Spiral Matrix
Given a matrix of m x n elements (m rows, n columns), return all elements of the matrix in spiral order.

For example,
Given the following matrix:
~~~
[
 [ 1, 2, 3 ],
 [ 4, 5, 6 ],
 [ 7, 8, 9 ]
]
~~~
You should return [1,2,3,6,9,8,7,4,5].

#### Solution
注意是循环外面改变up, down, left, right
~~~
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
~~~

## 59. Spiral Matrix II
Given an integer n, generate a square matrix filled with elements from 1 to n2 in spiral order.

For example,
Given n = 3,

You should return the following matrix:
~~~
[
 [ 1, 2, 3 ],
 [ 8, 9, 4 ],
 [ 7, 6, 5 ]
]
~~~

#### Solution
简单题目，注意数字从1开始
~~~
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
~~~

## 48. Rotate Image
You are given an n x n 2D matrix representing an image.

Rotate the image by 90 degrees (clockwise).

Follow up:
Could you do this in-place?

#### Solution
旋转2D数组通用模板：
1. 先一行一行swap
2. 然后根据要求symmetric swap
1 2 3     7 8 9     7 4 1
4 5 6  => 4 5 6  => 8 5 2
7 8 9     1 2 3     9 6 3

~~~
public class Solution {
    public void rotate(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return;
        int m = matrix.length, n = matrix[0].length;

        // swap row by row
        int s = 0, e = m - 1;
        while (s < e) {
            int[] temp = matrix[s];
            matrix[s] = matrix[e];
            matrix[e] = temp;
            s++;
            e--;
        }

        // swap symmetric
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < i; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }
}
~~~

## 289. Game of Life
According to the Wikipedia's article: "The Game of Life, also known simply as Life, is a cellular automaton devised by the British mathematician John Horton Conway in 1970."

Given a board with m by n cells, each cell has an initial state live (1) or dead (0). Each cell interacts with its eight neighbors (horizontal, vertical, diagonal) using the following four rules (taken from the above Wikipedia article):

Any live cell with fewer than two live neighbors dies, as if caused by under-population.
Any live cell with two or three live neighbors lives on to the next generation.
Any live cell with more than three live neighbors dies, as if by over-population..
Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
Write a function to compute the next state (after one update) of the board given its current state.

Follow up:
Could you solve it in-place? Remember that the board needs to be updated at the same time: You cannot update some cells first and then use their updated values to update other cells.
In this question, we represent the board using a 2D array. In principle, the board is infinite, which would cause problems when the active area encroaches the border of the array. How would you address these problems?

#### Solution
这题的关键是更改board去记录更新之前的状态
~~~
public class Solution {
    public void gameOfLife(int[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) return;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 1 && (neighbors(board, i, j) < 2 || neighbors(board, i, j) > 3)) {
                    board[i][j] = 2; // 1 -> 0
                }
                else if (board[i][j] == 0 && neighbors(board, i, j) == 3) {
                    board[i][j] = 3; // 0 -> 1
                }
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 2) {
                    board[i][j] = 0;
                }
                else if (board[i][j] == 3) {
                    board[i][j] = 1;
                }
            }
        }
    }

    private int neighbors(int[][] board, int r, int c) {
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        int count = 0;
        for (int[] dir : dirs) {
            int x = r + dir[0];
            int y = c + dir[1];
            if (x < 0 || x >= board.length || y < 0 || y >= board[0].length) continue;
            if (board[x][y] == 1 || board[x][y] == 2) {
                count++;
            }
        }
        return count;
    }
}
~~~

Follow-up:
Not really keen on doing it in Java. What I do is I have the coordinates of all living cells in a set. Then I count the living neighbors of all cells by going through the living cells and increasing the counter of their neighbors (thus cells without living neighbor will not be in the counter). Afterwards I just collect the new set of living cells by picking those with the right amount of neighbors. Does that help?

Refer to [discussion](https://discuss.leetcode.com/topic/26236/infinite-board-solution/5)
