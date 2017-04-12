
public class BackTracking {

  // 93. Restore IP Addresses
  public List<String> restoreIpAddresses(String s) {
        List<String> list = new ArrayList<String>();
        if (s == null || s.length() < 4) {
            return list;
        }

        int len = s.length();
        // 1st
        for (int a = 0; a < 3; a++) {
            if (isValid(s.substring(0, a + 1))) {

                // 2nd
                for (int b = a + 1; b < a + 4 && b < len; b++) {
                    if (isValid(s.substring(a + 1, b + 1))) {

                        // 3rd
                        for (int c = b + 1; c < b + 4 && c < len; c++) {
                            if (isValid(s.substring(b + 1, c + 1))) {

                                // 4th
                                if (isValid(s.substring(c + 1))) {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(s.substring(0, a + 1))
                                      .append(".")
                                      .append(s.substring(a + 1, b + 1))
                                      .append(".")
                                      .append(s.substring(b + 1, c + 1))
                                      .append(".")
                                      .append(s.substring(c + 1));
                                    list.add(sb.toString());
                                }
                            }
                        }
                    }
                }
            }
        }

        return list;
    }

    private boolean isValid(String s) {
        if (s == null || s.length() == 0 || s.length() > 3 || (s.length() > 1 && s.charAt(0) == '0') || Integer.parseInt(s) > 255) {
            return false;
        }
        return true;
    }
  /*
  test cases
  "123"
  "000"
  "0000"
  "25525511135"
  "51232342"
  "19216810062"
  "10001"
  "0279245587303"
  */

  // 468. Validate IP Address
  // Check whether a given string is a valid IP address
  public String validIPAddress(String IP) {
        if (IP == null || IP.length() == 0) {
            return "Neither";
        }

        if (IP.contains(".") && isValidIPv4(IP)) {
            return "IPv4";
        }
        else if (IP.contains(":") && isValidIPv6(IP)){
            return "IPv6";
        }

        return "Neither";
    }

    // Check whether a given string is a valid IPv4 address
    private boolean isValidIPv4(String IP) {
        if (IP.charAt(0) == '.' || IP.charAt(IP.length() - 1) == '.') {
            return false;
        }
        String[] tokens = IP.split("\\.");
        if (tokens.length != 4) {
            return false;
        }
        for (int i = 0; i < tokens.length; i++) {
            String str = tokens[i];
            if (str.length() == 0 || str.length() > 3 || (str.length() > 1 && str.charAt(0) == '0')
                    || !str.matches("[0-9]+") || Integer.parseInt(str) > 255) {
                        return false;
                    }
        }
        return true;
    }

    // Check whether a string is a valid IPv6 address
    private boolean isValidIPv6(String IP) {
        if (IP.charAt(0) == ':' || IP.charAt(IP.length() - 1) == ':') {
            return false;
        }
        String[] tokens = IP.split(":");
        if (tokens.length != 8) {
            return false;
        }
        for (int i = 0; i < tokens.length; i++) {
            String str = tokens[i].toLowerCase();
            if (str.length() == 0 || str.length() > 4 || !str.matches("[0-9a-f]+")) {
                return false;
            }
        }
        return true;
    }

    /*
    test cases
    "2001:0db8:85a3:0:0:8A2E:0370:7334:"
    ",2001:0db8:85a3:0:0:8A2E:0370:7334:"
    ":2001:0db8:85a3:0:0:8A2E:0370:7334"
    "0.0.0"
    "172.16.254.1.1"
    "172.16.254.1."
    ".172.16.254.1"
    "172.16.254.1:1"
    "172.16.254.1"
    "172.16.254.01"
    "172.abc.1e4.01"
    "172.abc.1e4.01"
    "2001:0db8:85a3::8A2E:0370:7334"
    "2001:0db8:85a3:0000:0000:8a2e:0370:7334"
    "2001:db8:85a3:0:0:8A2E:0370:7334"
    "2001:db8:85a3:0:0:8A2E4:0:7334"
    "02001:0db8:85a3:0000:0000:8a2e:0370:7334"
    "20EE:Fb8:85a3:0:0:8A2E:0370:7334"
    */

    // 51. N-Queens
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

    // 52. N-Queens II
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

    // 37. Sudoku Solver
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
}
