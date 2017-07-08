## 37. Sudoku Solver (Hard) *
Write a program to solve a Sudoku puzzle by filling the empty cells.

Empty cells are indicated by the character '.'.

You may assume that there will be only one unique solution.

#### Solution
Classic backtracking problem.
Iterate all row and column to validate all possibilities.

~~~
public void solveSudoku(char[][] board) {
    backtackingSolveSudoku(board, 0, 0);
}

private boolean backtackingSolveSudoku(char[][] board, int row, int column) {
    if (row == board.length) {
        return true;
    }
    if (column == board[0].length) {
        return backtackingSolveSudoku(board, row + 1, 0);
    }

    if (board[row][column] == '.') {
        for (int num = 1; num < 10; num++) {
            char ch = Character.forDigit(num, 10);
            if (isValidSoduku(board, row, column, ch)) {
                board[row][column] = ch;
                if (backtackingSolveSudoku(board, row, column + 1)) {
                    return true;
                }
                board[row][column] = '.';
            }
        }
    }
    else {
        return backtackingSolveSudoku(board, row, column + 1);
    }

    return false;
}

private boolean isValidSoduku(char[][] board, int row, int col, char ch) {

    // check column
    for (int i = 0; i < board.length; i++) {
        if (board[i][col] == ch) {
            return false;
        }
    }

    // check row
    for (int i = 0; i < board[row].length; i++) {
        if (board[row][i] == ch) {
            return false;
        }
    }

    int r = (row / 3) * 3;
    int c = (col / 3) * 3;
    // check 3 * 3
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            if (board[r + i][c + j] == ch) {
                return false;
            }
        }
    }

    return true;
}
~~~

Timestamp: July 8 2017
~~~
public class Solution {
    public void solveSudoku(char[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) return;
        helper(board);
    }

    private boolean helper(char[][] board) {
        int m = board.length, n = board[0].length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == '.') {
                    for (int val = 1; val <= 9; val++) {
                        char ch = (char)(val + 48);
                        if (!isAttack(board, i, j, ch)) {
                            board[i][j] = ch;
                            if (helper(board)) return true;
                            else board[i][j] = '.';
                        }
                    }
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isAttack(char[][] board, int row, int col, char ch) {
        int m = board.length, n = board[0].length;
        // same row
        for (int i = 0; i < m; i++) {
            if (board[i][col] == ch) return true;
        }

        // same col
        for (int i = 0; i < n; i++) {
            if (board[row][i] == ch) return true;
        }

        // 3 x 3 block
        int x = (row / 3) * 3;
        int y = (col / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[x + i][y + j] == ch) return true;
            }
        }

        return false;
    }
}
~~~
