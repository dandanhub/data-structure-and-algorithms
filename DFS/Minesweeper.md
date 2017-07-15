## 529. Minesweeper
Let's play the minesweeper game (Wikipedia, online game)!

You are given a 2D char matrix representing the game board. 'M' represents an unrevealed mine, 'E' represents an unrevealed empty square, 'B' represents a revealed blank square that has no adjacent (above, below, left, right, and all 4 diagonals) mines, digit ('1' to '8') represents how many mines are adjacent to this revealed square, and finally 'X' represents a revealed mine.

Now given the next click position (row and column indices) among all the unrevealed squares ('M' or 'E'), return the board after revealing this position according to the following rules:

If a mine ('M') is revealed, then the game is over - change it to 'X'.
If an empty square ('E') with no adjacent mines is revealed, then change it to revealed blank ('B') and all of its adjacent unrevealed squares should be revealed recursively.
If an empty square ('E') with at least one adjacent mine is revealed, then change it to a digit ('1' to '8') representing the number of adjacent mines.
Return the board when no more squares will be revealed.
Example 1:
~~~
Input:

[['E', 'E', 'E', 'E', 'E'],
 ['E', 'E', 'M', 'E', 'E'],
 ['E', 'E', 'E', 'E', 'E'],
 ['E', 'E', 'E', 'E', 'E']]

Click : [3,0]

Output:

[['B', '1', 'E', '1', 'B'],
 ['B', '1', 'M', '1', 'B'],
 ['B', '1', '1', '1', 'B'],
 ['B', 'B', 'B', 'B', 'B']]
~~~

Example 2:
~~~
Input:

[['B', '1', 'E', '1', 'B'],
 ['B', '1', 'M', '1', 'B'],
 ['B', '1', '1', '1', 'B'],
 ['B', 'B', 'B', 'B', 'B']]

Click : [1,2]

Output:

[['B', '1', 'E', '1', 'B'],
 ['B', '1', 'X', '1', 'B'],
 ['B', '1', '1', '1', 'B'],
 ['B', 'B', 'B', 'B', 'B']]
~~~

Note:
The range of the input matrix's height and width is [1,50].
The click position will only be an unrevealed square ('M' or 'E'), which also means the input board contains at least one clickable square.
The input board won't be a stage when game is over (some mines have been revealed).
For simplicity, not mentioned rules should be ignored in this problem. For example, you don't need to reveal all the unrevealed mines when the game is over, consider any cases that you will win the game or flag any squares.

#### Solution
1. 如果当前board[row][col] == 'M'，把当前char设置为'X'，然后直接返回
2. 如果当前board[row][col] == 'E' && mineCount(row, col) > 0，把当前char设置为对应的个数，然后返回
3. 如果当前board[row][col] == 'E' && mineCount(row, col) == 0，把当前char设置为'B'，然后递归调用dfs

~~~
public class Solution {
    public char[][] updateBoard(char[][] board, int[] click) {
        boolean[][] visited = new boolean[board.length][board[0].length];
        dfs(board, visited, click[0], click[1]);
        return board;
    }

    private void dfs(char[][] board, boolean[][] visited, int row, int col) {
        // base cases
        if (board[row][col] == 'M') {
            board[row][col] = 'X';
            return;
        }

        visited[row][col] = true;
        int count = mineCount(board, row, col);
        if (count > 0) {
            board[row][col] = (char)(count + '0');
            return;
        }

        board[row][col] = 'B';
        int m = board.length, n = board[0].length;
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        for (int[] dir : dirs) {
            int x = row + dir[0];
            int y = col + dir[1];
            if (x >= 0 && x < m && y >= 0 && y < n && !visited[x][y]) {
                dfs(board, visited, x, y);
            }
        }
    }

    private int mineCount(char[][] board, int row, int col) {
        int m = board.length, n = board[0].length;
        int count = 0;
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        for (int[] dir : dirs) {
            int x = row + dir[0];
            int y = col + dir[1];
            if (x >= 0 && x < m && y >= 0 && y < n) {
                if (board[x][y] == 'M' || board[x][y] == 'X') {
                    count++;
                }
            }
        }
        return count;
    }
}
~~~
