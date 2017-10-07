class Solution {
    public int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0
            || matrix[0].length == 0) return 0;

        int m = matrix.length;
        int n = matrix[0].length;
        int[][] memo = new int[m][n];
        int ans = 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                ans = Math.max(ans, dfs(matrix, memo, i, j));
            }
        }
        return ans;
    }

    private int dfs(int[][] matrix, int[][] memo, int x, int y) {
        if (memo[x][y] != 0) return memo[x][y];
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] dir : dirs) {
            if (x + dir[0] >= 0 && x + dir[0] < matrix.length
                && y + dir[1] >= 0 && y + dir[1] < matrix[0].length
                && matrix[x + dir[0]][y + dir[1]] > matrix[x][y])
            memo[x][y] = Math.max(memo[x][y], 1 + dfs(matrix, memo, x + dir[0], y + dir[1]));
        }
        memo[x][y] = Math.max(1, memo[x][y]);
        return memo[x][y];
    }
}
