// ## 79. Word Search
// Given a 2D board and a word, find if the word exists in the grid.
//
// The word can be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once.
//
// For example,
// Given board =
//
// [
//   ['A','B','C','E'],
//   ['S','F','C','S'],
//   ['A','D','E','E']
// ]
// word = "ABCCED", -> returns true,
// word = "SEE", -> returns true,
// word = "ABCB", -> returns false.
//
// #### Solution
// DFS with backtracking.
// What if we cannot allocate new space, then mark visited or not by set board in-place, board[y][x] ^= 256;
//
// **Complexity Analysis**
// Time complexity: exponential O(mn * 2^mn)
// Space complexity: O(mn)
//
// Note Always consider the test case
// ~~~
// ["a"]
// ["a"]
// ~~~
// 所以检测的时候先检查word.charAt(0) == board[i][j], 然后从index 1开始backtrack.
//
// ~~~
public class Solution {
    public boolean exist(char[][] board, String word) {
        if (word == null || word.length() == 0) return true;
        if (board == null || board.length == 0 || board[0].length == 0) return false;

        int h = board.length;
        int w = board[0].length;
        boolean[][] vistied = new boolean[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (word.charAt(0) == board[i][j] && backtrack(board, word, vistied, i, j, 1)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean backtrack(char[][] board, String word, boolean[][] visited, int i, int j, int index) {
        if (word.length() == index) return true;

        int h = board.length;
        int w = board[0].length;

        visited[i][j] = true;
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : dirs) {
            int x = i + dir[0];
            int y = j + dir[1];
            if (x >= 0 && x < h && y >= 0 && y < w && !visited[x][y] && word.charAt(index) == board[x][y]) {
                if (backtrack(board, word, visited, x, y, index + 1)) return true;
            }
        }
        visited[i][j] = false;

        return false;
    }
}
// ~~~
//
// ## 212. Word Search II
// Given a 2D board and a list of words from the dictionary, find all words in the board.
//
// Each word must be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once in a word.
//
// For example,
// Given words = ["oath","pea","eat","rain"] and board =
//
// [
//   ['o','a','a','n'],
//   ['e','t','a','e'],
//   ['i','h','k','r'],
//   ['i','f','l','v']
// ]
// Return ["eat","oath"].
// Note:
// You may assume that all inputs are consist of lowercase letters a-z.
//
// #### Solution
// 1. The key is to use Tries (How to implement Tires?)
// 2. Then DFS + backtrack search.
//
// 几个需要注意的点
// 1. 如何避免重复
// 2. 在BuildTries的时候，Tries移动
// ~~~
// for (int j = 0; j < s.length(); j++) {
//                 char ch = s.charAt(j);
//                 if (curr.children[ch - 'a'] == null) {
//                     curr.children[ch - 'a'] = new Tries();
//                 }
//                 // BUG
//                 // else {
//                 //     curr = curr.children[ch - 'a'];
//                 // }
//                 curr = curr.children[ch - 'a'];
//             }
//             curr.word = s;
// ~~~

// **Note**
// Always consider the test case
// ~~~
// ["a"]
// ["a"]
// ~~~
// If you trigger backtrack by checking whether x, y is within board, will you add "a" as a valid ans?
//
// To speed up, check the [discussion](https://discuss.leetcode.com/topic/33246/java-15ms-easiest-solution-100-00)
//
// ~~~
ppublic class Solution {
    public List<String> findWords(char[][] board, String[] words) {
        List<String>  ans = new ArrayList<String>();
        if (board == null || board.length == 0 || board[0].length == 0 || words == null || words.length == 0) {
            return ans;
        }

        TriesNode root = buildTriesTree(words);
        boolean[][] visited = new boolean[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (root.children[board[i][j] - 'a'] != null) {
                    backtrack(ans, board, root.children[board[i][j] - 'a'], visited, i, j);
                }
            }
        }

        return ans;
    }

    private void backtrack(List<String> ans, char[][] board, TriesNode root, boolean[][] visited, int i, int j) {
        if (root.val != null) {
            ans.add(root.val); // found a valid word
            root.val = null;
        }

        int h = board.length;
        int w = board[0].length;
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        visited[i][j] = true;
        for (int[] dir : dirs) {
            int x = i + dir[0];
            int y = j + dir[1];
            if (x >= 0 && x < h && y >= 0 && y < w && !visited[x][y] && root.children[board[x][y] - 'a'] != null) {
                backtrack(ans, board, root.children[board[x][y] - 'a'], visited, x, y);
            }
        }
        visited[i][j] = false;
    }

    private class TriesNode {
        String val;
        TriesNode[] children;

        TriesNode() {
            children = new TriesNode[26];
        }
    }

    private TriesNode buildTriesTree(String[] words) {
        TriesNode root = new TriesNode();
        for (String str : words) {
            insertString(root, str);
        }
        return root;
    }

    private void insertString(TriesNode root, String str) {
        if (str == null || str.length() == 0) return;

        TriesNode curr = root;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (curr.children[ch - 'a'] == null) {
                curr.children[ch - 'a'] = new TriesNode();
            }
            curr = curr.children[ch - 'a'];
        }
        curr.val = str;
    }
}
// ~~~
