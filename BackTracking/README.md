The java codes here basically summarize my work on backtacking algorithm. The codes are driven by CMU course 08-722 Data Structures for Application Programmers and some LeetCode problems about backtacking.

# BackTracking.java

## 93. Restore IP Addresses (Medium) *
Given a string containing only digits, restore it by returning all possible valid IP address combinations.

For example:
Given "25525511135",

return ["255.255.11.135", "255.255.111.35"]. (Order does not matter)

#### Solution
Check four IP parts one by one. Each IP part should at least have 1 digit and at most 3 digits, and it should between 0 - 255.

## 468. Validate IP Address (Medium) * (Not about backtacking, add here as a supplement for problem 93)
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

# Palindrome.java

The java file contains solutions to leetcode question about
palindrome. Some of the problems use backtracking.

## 131. Palindrome Partitioning (Medium) *
Given a string s, partition s such that every substring of the partition is a palindrome.

Return all possible palindrome partitioning of s.

For example, given s = "aab",
Return
~~~~
[
  ["aa","b"],
  ["a","a","b"]
]
~~~~

#### Solution
Use backtracking template.
1. Given a string s, if s.substring(0, i) is a valid palindrome, then recursively check the palindrome partition of s.substring(i).
2. The base case of recursion is when a string is empty, we can add the partitions to final result and return.
3. Handle the base return case carefully.

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

## 52. N-Queens II
Follow up for N-Queens problem.
Now, instead outputting board configurations, return the total number of distinct solutions.

#### Solution
Instead of adding a valid N-Queens layout to result, we add the count by 1.

## 37. Sudoku Solver
Write a program to solve a Sudoku puzzle by filling the empty cells.

Empty cells are indicated by the character '.'.

You may assume that there will be only one unique solution.

#### Solution
Classic backtracking problem.
Iterate all row and column to validate all possibilities.
