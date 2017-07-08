## 51. N-Queens
The n-queens puzzle is the problem of placing n queens on an n×n chessboard such that no two queens attack each other.

Given an integer n, return all distinct solutions to the n-queens puzzle.

Each solution contains a distinct board configuration of the n-queens' placement, where 'Q' and '.' both indicate a queen and an empty space respectively.

For example,
There exist two distinct solutions to the 4-queens puzzle:
~~~~
[
 [".Q..",  // Solution 1
  "...Q",
  "Q...",
  "..Q."],

 ["..Q.",  // Solution 2
  "Q...",
  "...Q",
  ".Q.."]
]
~~~~

#### Solution
Classic backtracking problem.
Scan all possibilities and when a possibility is valid, add it to the final result.

~~~
public List<List<String>> solveNQueens(int n) {
    List<List<String>> ans = new ArrayList<List<String>>();
    if (n < 0) {
        return ans;
    }
    int[][] table = new int[n][n];
    backtrackingNQueens(ans, new ArrayList<String>(), n, 0, table);
    return ans;
}

private void backtrackingNQueens(List<List<String>> ans, List<String> list, int n, int row, int[][] table) {
    if (row == n) {
        ans.add(new ArrayList<String>(list));
        return;
    }

    for (int column = 0; column < n; column++) {
        if (isAttack(table, row, column)) {
            table[row][column] = 1;
            StringBuilder newRow = new StringBuilder();
            for (int i = 0; i < n; i++) {
                if (i != column) {
                    newRow.append(".");
                }
                else {
                    newRow.append("Q");
                }
            }
            list.add(newRow.toString());
            backtrackingNQueens(ans, list, n, row + 1, table);
            list.remove(list.size() - 1);
            table[row][column] = 0;
        }
    }
}

private boolean isAttack(int[][] table, int row, int column) {
    // conflict in the same column
    for (int i = 0; i < row; i++) {
        if (table[i][column] == 1) {
            return false;
        }
    }

    // conflict in diagonal, left
    for (int i = row - 1; i >= 0; i--) {
        int col = column - (row - i);
        if (col >= 0 && table[i][col] == 1) {
            return false;
        }
    }

    // conflict in diagonal, right
    for (int i = row - 1; i >= 0; i--) {
        int col = column + (row - i);
        if (col < table[row].length && table[i][col] == 1) {
            return false;
        }
    }

    return true;
}
~~~

## 52. N-Queens II
Follow up for N-Queens problem.
Now, instead outputting board configurations, return the total number of distinct solutions.

#### Solution
Instead of adding a valid N-Queens layout to result, we add the count by 1.

~~~
public int totalNQueens(int n) {
    if (n < 0) {
        return 0;
    }
    int[][] table = new int[n][n];
    return backtrackingTotalNQueens(n, 0, table);
}

private int backtrackingTotalNQueens(int n, int row, int[][] table) {
    if (row == n) {
        return 1;
    }

    int ans = 0;
    for (int column = 0; column < n; column++) {
        if (isAttack(table, row, column)) {
            table[row][column] = 1;
            ans += backtrackingNQueens(n, row + 1, table);
            table[row][column] = 0;
        }
    }
    return ans;
}
~~~
