## 93. Restore IP Addresses (Medium) *
Given a string containing only digits, restore it by returning all possible valid IP address combinations.

For example:
Given "25525511135",

return ["255.255.11.135", "255.255.111.35"]. (Order does not matter)

#### Solution
Check four IP parts one by one. Each IP part should at least have 1 digit and at most 3 digits, and it should between 0 - 255.

~~~
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
~~~

## 468. Validate IP Address (Medium) * (Not about backtracking, add here as a supplement for problem 93)
Write a function to check whether an input string is a valid IPv4 address or IPv6 address or neither.

IPv4 addresses are canonically represented in dot-decimal notation, which consists of four decimal numbers, each ranging from 0 to 255, separated by dots ("."), e.g.,172.16.254.1;

Besides, leading zeros in the IPv4 is invalid. For example, the address 172.16.254.01 is invalid.

IPv6 addresses are represented as eight groups of four hexadecimal digits, each group representing 16 bits. The groups are separated by colons (":"). For example, the address 2001:0db8:85a3:0000:0000:8a2e:0370:7334 is a valid one. Also, we could omit some leading zeros among four hexadecimal digits and some low-case characters in the address to upper-case ones, so 2001:db8:85a3:0:0:8A2E:0370:7334 is also a valid IPv6 address(Omit leading zeros and using upper cases).

However, we don't replace a consecutive group of zero value with a single empty group using two consecutive colons (::) to pursue simplicity. For example, 2001:0db8:85a3::8A2E:0370:7334 is an invalid IPv6 address.

Besides, extra leading zeros in the IPv6 is also invalid. For example, the address 02001:0db8:85a3:0000:0000:8a2e:0370:7334 is invalid.

Note: You may assume there is no extra space or special characters in the input string.

Example 1:
~~~~
Input: "172.16.254.1"
Output: "IPv4"
Explanation: This is a valid IPv4 address, return "IPv4".
~~~~

Example 2:
~~~~
Input: "2001:0db8:85a3:0:0:8A2E:0370:7334"
Output: "IPv6"
Explanation: This is a valid IPv6 address, return "IPv6".
~~~~

Example 3:
~~~~
Input: "256.256.256.256"
Output: "Neither"
Explanation: This is neither a IPv4 address nor a IPv6 address.
~~~~

#### Solution
1. I checked IPv4 and IPv6 separately. If a string contains “.”, then check it with IPv4 function. If a string contains ":", check it with IPv6 function.
2. For IPv4, I split string by ".", and then validate four number parts one by one.
3. For IPv6, I split string by ":", and then validate eight number parts one by one.
I failed the test case: "2001:0db8:85a3:0:0:8A2E:0370:7334:". The issue here is by using `String[] tokens = IP.split(":");`, it will give 8 tokens as result. So we need to consider the corner case where the first and last character is ':'.

~~~
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
~~~

The java file contains solutions to leetcode question about
palindrome. Some of the problems use backtracking.


---


## 132. Palindrome Partitioning II (Hard) * (Backtracking exceed time limit)
Given a string s, partition s such that every substring of the partition is a palindrome.

Return the minimum cuts needed for a palindrome partitioning of s.

For example, given s = "aab",
Return 1 since the palindrome partitioning ["aa","b"] could be produced using 1 cut.

#### Solution (Dynamic Programming)
Two data structures are used:
`boolean[][] pal = new boolean[len][len];`
`int[] dp = new int[len];`
1. `pal[i][j]` means whether a substring from i to j (inclusive) is a panlindrome or not.
2. `dp[i]` means the shortest cut of substring from 0 to i (inclusive).
3. The dynamic transition formula is:
~~~~
if (s.charAt(j) == s.charAt(i) && (j + 1 >= i - 1 || pal[j + 1][i - 1] == true)) {
      // substring from i to j (inclusive) is a palindrome
      dp[i] = Math.min(dp[i], j == 0 ? 0 : dp[j - 1] + 1);
      pal[j][i] = true;
}
else {
      dp[i] = Math.min(dp[i], dp[i - 1] + 1);
}
~~~~

~~~
// 131. Palindrome Partitioning
public List<List<String>> partition(String s) {
    List<List<String>> ans = new ArrayList<List<String>>();
    if (s == null) {
        return ans;
    }
    if (s.length() == 0) {
        ans.add(new ArrayList<String>());
        return ans;
    }
    backtracking(ans, new ArrayList<String>(), s);
    return ans;
}

// Classical backtracking trick
private void backtracking(List<List<String>> ans, List<String> list, String s) {
    if (s.length() == 0) {
        List<String> newList = new ArrayList<String>(list);
        ans.add(newList);
    }

    for (int i = 1; i <= s.length(); i++) {
        String substr = s.substring(0, i);
        if (isPalindrome(substr)) {
            list.add(substr);
            backtracking(ans, list, s.substring(i));
            list.remove(list.size() - 1);
        }
    }
}

// Check whether a given string is a valid palindrome or not
private boolean isPalindrome(String s) {
    if (s == null) {
        //TO-DO;
    }
    int i = 0;
    int j = s.length() - 1;
    while (i < j) {
        if (s.charAt(i) != s.charAt(j)) {
            return false;
        }
        i++;
        j--;
    }
    return true;
}

/*
// 132. Palindrome Partitioning II
// Backtracking time out
public int minCut(String s) {
    if (s == null) {
        // TO-DO
    }
    if (s.length() == 0 || isPalindrome(s)) {
        return 0;
    }

    int minCount = Integer.MAX_VALUE;
    for (int i = 1; i < s.length(); i++) {
        String substr = s.substring(0, i);
        if (isPalindrome(substr)) {
            int next = minCut(s.substring(i));
            minCount = Math.min(minCount, next + 1);
        }
    }
    return minCount;
}

// Check whether a given string is a valid palindrome or not
private boolean isPalindrome(String s) {
    if (s == null) {
        //TO-DO;
    }
    int i = 0;
    int j = s.length() - 1;
    while (i < j) {
        if (s.charAt(i) != s.charAt(j)) {
            return false;
        }
        i++;
        j--;
    }
    return true;
}
*/

/*
test case
"fifgbeajcacehiicccfecbfhhgfiiecdcjjffbghdidbhbdbfbfjccgbbdcjheccfbhafehieabbdfeigbiaggchaeghaijfbjhi"
*/
~~~

~~~
// 132. Palindrome Partitioning II
// Dynamic Programming
public int minCut(String s) {
    if (s == null || s.length() == 0) {
        return 0;
    }

    int len = s.length();
    boolean[][] pal = new boolean[len][len];
    int[] dp = new int[len];

    for (int i = 0; i < len; i++) {
        dp[i] = i;
        for (int j = 0; j < i; j++) {
            if (s.charAt(j) == s.charAt(i) && (j + 1 >= i - 1 || pal[j + 1][i - 1] == true)) {
                dp[i] = Math.min(dp[i], j == 0 ? 0 : dp[j - 1] + 1);
                pal[j][i] = true;
            }
            else {
                dp[i] = Math.min(dp[i], dp[i - 1] + 1);
            }
        }
    }
    return dp[len - 1];
}
/*
test case
""
"a"
"aa"
"ab"
"aacecaaa"
"aaaaaaa"
"aaacec"
"abcddcbaaa"
*/

~~~

## 409. Longest Palindrome
Given a string which consists of lowercase or uppercase letters, find the length of the longest palindromes that can be built with those letters.

This is case sensitive, for example "Aa" is not considered a palindrome here.

Note:
Assume the length of given string will not exceed 1,010.

Example:
~~~~
Input:
"abccccdd"

Output:
7

Explanation:
One longest palindrome that can be built is "dccaccd", whose length is 7.
~~~~

#### Solution
1. Solution 1
  - My solution is simple. Use a hash map to store the character in the string together with its occurring times.
  - The 2nd step is to iterate the entries in the hash map to do the counting.
  - If a character occurs odd times, say a times, then add a - 1 into the result.
  - If a character occurs even times, say b times, then add b into the result.
2. Solution 2
We can use a hash set instead of hash map. Do a little trick like this [leetcode discussion](https://discuss.leetcode.com/topic/6186/java-backtracking-solution/2).


## 214. Shortest Palindrome (Hard) * (Time Limitation)
Given a string S, you are allowed to convert it to a palindrome by adding characters in front of it. Find and return the shortest palindrome you can find by performing this transformation.

For example:
Given "aacecaaa", return "aaacecaaa".
Given "abcd", return "dcbabcd".

#### Solution
 Naiive O(n^2) method will receive Time Limit Exceed error. Use KMP algorithm to expedite the searching process.

###### Solution 1
**What is KMP?**
We can refer to [this Chinese blog](http://www.ruanyifeng.com/blog/2013/05/Knuth%E2%80%93Morris%E2%80%93Pratt_algorithm.html).
1. Calculate a partial match table, e.g. search word "ABCDABD".

  A B C D A B D
  0 0 0 0 1 2 0

  - Prefix and Postfix
  - A: [] and [] -> 0
  - AB: [A] and [B] -> 0
  - ABC: [A, AB] and [C, BC] -> 0
  - ABCD: [A, AB, ABC] and [D, CD, BCD] -> 0
  - ABCDA: [A, AB, ABC, ABCD] and [A, DA, CDA, BCDA] -> 1
  - ABCDAB: [A, AB, ABC, ABCD, ABCDB] and [B, AB, DAB, CDAB, BCDAB] -> 2
  - ...

2. Calculate offset using formula:
offset = (count of matched character) - (corresponding value in partial match table)

Here we only need to build KMP table to find the longest palindrome starting from 0 position.

1. Build a new string, e.g. for input catacb, build new string catacb#bcatac.
2. Build KMP table on the new string.
3. The longest palindrome staring from 0 is the value of the last character in the table.

It takes O(n) to get the solution. The code to build KMP table is kind of complicated.

###### Solution 2
Recursively check the longest palindrome substring from 0.
Starting from the char in mid of the array.
The time complexity n/2 + (n/2 - 1) + (n/2 - 2) + ... + (n/2 - n/2) = n/2 * n/2 - (n/4 + n^2/8) = O(n^2)

## 266. Palindrome Permutation (Easy) *
Given a string, determine if a permutation of the string could form a palindrome.

For example,
"code" -> False, "aab" -> True, "carerac" -> True.

#### Solution
We count the time that a character occurs in the string. If there is more than one character occurs odd time, then the string cannot form a palindrome. If there is only one character occurs odd time, then return true.

## 267. Palindrome Permutation II
Given a string s, return all the palindromic permutations (without duplicates) of it. Return an empty list if no palindromic permutation could be form.

For example:

Given s = "aabb", return ["abba", "baab"].

Given s = "abc", return [].

Hint:

If a palindromic permutation exists, we just need to generate the first half of the string.
To generate all distinct permutations of a (half of) string, use a similar approach from: Permutations II or Next Permutation.

#### Solution
We can solve the problem in two steps:
1. Find all characters occur along with its occurring time using HashMap. While doing this, we can examine whether a string can form a valid permutation or not, if invalid, simply return empty list.
2. Construct string (characters) permutation for the first half of the palindrome using backtracking. Construct an answer and add it to result list.

~~~
// 266. Palindrome Permutation (Easy)
public boolean canPermutePalindrome(String s) {
    if (s == null || s.length() == 0) {
        return true; // TO-DO
    }

    Map<Character, Integer> map = new HashMap<Character, Integer>();
    for (int i = 0; i < s.length(); i++) {
        char ch = s.charAt(i);
        if (map.containsKey(ch)) {
            map.put(ch, map.get(ch) + 1);
        }
        else {
            map.put(ch, 1);
        }
    }

    boolean odd = false;
    for (Map.Entry<Character, Integer> entry : map.entrySet()) {
        if (entry.getValue() % 2 == 1) {
            if (odd == false) {
                odd = true;
            }
            else {
                return false;
            }
        }
    }
    return true;
}

// 267. Palindrome Permutation II
public List<String> generatePalindromes(String s) {
    List<String> ans = new ArrayList<String>();
    if (s == null || s.length() == 0) {
        return ans;
    }

    Map<Character, Integer> map = new HashMap<Character, Integer>();
    for (int i = 0; i < s.length(); i++) {
        char ch = s.charAt(i);
        if (map.containsKey(ch)) {
            map.put(ch, map.get(ch) + 1);
        }
        else {
            map.put(ch, 1);
        }
    }

    String odd = "";
    List<Character> chars = new ArrayList<Character>();
    int size = 0;
    for (Map.Entry<Character, Integer> entry : map.entrySet()) {
        if (entry.getValue() % 2 == 1) {
            if (odd.length() == 0) {
                for (int i = 0; i < entry.getValue() / 2; i++) {
                    chars.add(entry.getKey());
                }
                odd = String.valueOf(entry.getKey());
            }
            else {
                return ans;
            }
        }
        else {
            for (int i = 0; i < entry.getValue() / 2; i++) {
                chars.add(entry.getKey());
            }
        }
    }

    Collections.sort(chars);
    backtracking(ans, new StringBuilder(), chars, new boolean[chars.size()], odd);

    return ans;
}

private void backtracking(List<String> list, StringBuilder sb, List<Character> chars, boolean[] used, String odd) {
    if (sb.length() == chars.size()) {
        list.add(sb.toString() + odd + sb.reverse().toString());
        sb.reverse();
        return;
    }

    for (int i = 0; i < chars.size(); i++) {
        if (i > 0 && chars.get(i) == chars.get(i - 1) && !used[i - 1]) {
            continue;
        }

        if (!used[i]) {
            used[i] = true;
            sb.append(chars.get(i));
            backtracking(list, sb, chars, used, odd);
            sb.deleteCharAt(sb.length() - 1);
            used[i] = false;
        }
    }

~~~

---


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
~~~

## 52. N-Queens II
Follow up for N-Queens problem.
Now, instead outputting board configurations, return the total number of distinct solutions.

#### Solution
Instead of adding a valid N-Queens layout to result, we add the count by 1.

~~~
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
~~~

## 37. Sudoku Solver
Write a program to solve a Sudoku puzzle by filling the empty cells.

Empty cells are indicated by the character '.'.

You may assume that there will be only one unique solution.

#### Solution
Classic backtracking problem.
Iterate all row and column to validate all possibilities.

## 254. Factor Combinations (Medium) *
Numbers can be regarded as product of its factors. For example,

8 = 2 x 2 x 2;
  = 2 x 4.
Write a function that takes an integer n and return all possible combinations of its factors.

Note:
1. You may assume that n is always positive.
2. Factors should be greater than 1 and less than n.

#### Solution
The thing is how to avoid duplications.
For example, 32, we will consider [2,2,2,4] and [4,2,2,2] as duplicates.
LeetCode deleted the description:
"Each combination's factors must be sorted ascending, for example: The factors of 2 and 6 is [2, 6], not [6, 2]."

Set a lower bound in backtacking.

## 291. Word Pattern II
Given a pattern and a string str, find if str follows the same pattern.

Here follow means a full match, such that there is a bijection between a letter in pattern and a non-empty substring in str.

Examples:
pattern = "abab", str = "redblueredblue" should return true.
pattern = "aaaa", str = "asdasdasdasd" should return true.
pattern = "aabb", str = "xyzabcxzyabc" should return false.

#### Solution
A naiive backtracking solution. For example,
pattern "abab"
str "redblueredblue"

1. put('a',"r") into map, and move forward to match pattern "bab" with "edblueredblue"
2. put('b',"e") into map, and move forward to match pattern "ab" with "dblueredblue"
3. when checking pattern 'a' with 'd', it does not match because we have already map pattern 'a' with "r"
3. try checking pattern 'a' with 'db', it does not match
4. try checking pattern 'a' with "dbl", it does not match
5. ...
6. no match found, back to step 2, we put('b',"ed") into the map
7. repeat 3 to 5
8. no match found, back to step 2, until put('b',"edblue") into the map, move forward to match pattern "ab" with "redblue"
9. bingo

~~~
// 291. Word Pattern II
public boolean wordPatternMatch(String pattern, String str) {
    if (pattern == null) {
        return str == null;
    }
    if (pattern.length() == 0) {
        return str.length() == 0;
    }
    Map<Character, String> map = new HashMap<Character, String>();
    return backtrackingPatternMatch(pattern, 0, str, map);
}

private boolean backtrackingPatternMatch(String pattern, int pos, String str, Map<Character, String> map) {
    if (pos == pattern.length()) {
        if (str.length() == 0) {
            return true;
        }
        else {
            return false;
        }
    }
    char ch = pattern.charAt(pos);
    for (int j = 1; j <= str.length(); j++) {
        String s = str.substring(0, j);
        if (!map.containsKey(ch) && !map.containsValue(s)) {
            map.put(ch, s);
            if (backtrackingPatternMatch(pattern, pos + 1, str.substring(j), map)) {
                return true;
            }
            else {
                map.remove(ch);
            }
        }
        else if (map.containsKey(ch) && s.equals(map.get(ch))) {
            if (backtrackingPatternMatch(pattern, pos + 1, str.substring(j), map)) {
                return true;
            }
        }
    }
    return false;
}

~~~

## 294. Flip Game II
You are playing the following Flip Game with your friend: Given a string that contains only these two characters: + and -, you and your friend take turns to flip two consecutive "++" into "--". The game ends when a person can no longer make a move and therefore the other person will be the winner.

Write a function to determine if the starting player can guarantee a win.

For example, given s = "++++", return true. The starting player can guarantee a win by flipping the middle "++" to become "+--+".

Follow up:
Derive your algorithm's runtime complexity.

#### Solution
Iteratively call function by passing new string.

~~~
// 294. Flip Game II
public boolean canWin(String s) {
    if (s == null || s.length() < 0) {
        return false;
    }
    return backtrackingWin(s);
}

private boolean backtrackingWin(String s) {
    for (int i = 0; i < s.length() - 1; i++) {
        if (s.substring(i, i + 2).equals("++")) {
            StringBuilder sb = new StringBuilder();
            sb.append(s.substring(0, i))
              .append("--")
              .append(s.substring(i + 2));
            if (!backtrackingWin(sb.toString())) {
                return true;
            }
        }
    }
    return false;
}
~~~
