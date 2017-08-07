## 125. Valid Palindrome
Given a string, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.

For example,
"A man, a plan, a canal: Panama" is a palindrome.
"race a car" is not a palindrome.

Note:
Have you consider that the string might be empty? This is a good question to ask during an interview.

For the purpose of this problem, we define empty string as valid palindrome.

#### Solution
**注意如何使用Character.toLowerCase(ch), Character.isDigit(ch), Character.isLetter(ch), Character.isLetterOrDigit(ch)接口**

~~~
public class Solution {
    public boolean isPalindrome(String s) {
        if (s == null) return false;
        if (s.length() == 0) return true;
        int i = 0;
        int j = s.length() - 1;
        while (i < j) {
            while (i < j && !Character.isLetterOrDigit(s.charAt(i))) {
                i++;
            }
            while (i < j && !Character.isLetterOrDigit(s.charAt(j))) {
                j--;
            }
            if (Character.toLowerCase(s.charAt(i)) != Character.toLowerCase(s.charAt(j))) return false;
            i++;
            j--;
        }
        return true;
    }
}
~~~

## 214. Shortest Palindrome
Given a string S, you are allowed to convert it to a palindrome by adding characters in front of it. Find and return the shortest palindrome you can find by performing this transformation.

For example:

Given "aacecaaa", return "aaacecaaa".

Given "abcd", return "dcbabcd".

#### Solution
1. Naiive way, we search from mid to check whether it can form a valid palindrome reaching the start of the string. Time O(n^2), but search from start speed up the sol.
2. KMP, build a KMP table for str#str.reverse(). Linear time complexity.

1
~~~
public class Solution {
    public String shortestPalindrome(String s) {
        if (s == null || s.length() <= 1) return s;
        int mid = (s.length() - 1) / 2;
        String str = "";
        for (int i = mid; i >= 0; i--) {
            if (s.charAt(i) == s.charAt(i + 1)) {
               str = generatePalindrome(s, i, i + 1);
               if (str != null) return str;
            }

            str = generatePalindrome(s, i, i);
            if (str != null) return str;
        }
        return str;
    }

    private String generatePalindrome(String s, int l, int r) {
        while (l >= 0 && r < s.length()) {
            if (s.charAt(l) != s.charAt(r)) break;
            l -= 1;
            r += 1;
        }

        if (l >= 0) return null;
        StringBuilder sb = new StringBuilder(s.substring(r));
        return sb.reverse() + s;
    }
}
~~~

2
~~~
public class Solution {
    public String shortestPalindrome(String s) {
        if (s == null || s.length() == 0) return s;
        StringBuilder sb = new StringBuilder(s);
        String str = s + "#" + sb.reverse().toString();
        int[] KMP = buildKMP(str);
        int index = KMP[str.length() - 1];

        StringBuilder sub = new StringBuilder(s.substring(index));
        String ans = sub.reverse().toString() + s;
        return ans;
    }

    private int[] buildKMP(String s) {
        int[] KMP = new int[s.length()];
        int index = 0;
        for (int i = 1; i < s.length(); i++) {
            index = KMP[i - 1];
            if (s.charAt(i) == s.charAt(index)) {
                KMP[i] = index + 1;
            }
            else {
                while (index > 0 && s.charAt(i) != s.charAt(index)) {
                    index = KMP[index - 1];
                }

                if (s.charAt(i) == s.charAt(index)) KMP[i] = index + 1;
            }
        }
        return KMP;
    }
}
~~~

## 5. Longest Palindromic Substring
Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.

Example:
~~~
Input: "babad"
Output: "bab"
Note: "aba" is also a valid answer.
~~~

Example:
~~~
Input: "cbbd"
Output: "bb"
~~~

#### Solution
1. O(n^2) 每个节点作为中间点往两边搜索
2. Manacher’s Algorithm O(n) Solution 可做做到O(n)

Time O(n^2) <br>
~~~
public class Solution {
    public String longestPalindrome(String s) {
        if (s == null || s.length() == 0) return s;
        String ans = "";

        for (int i = 0; i < s.length(); i++) {
            String m = helper(s, i, i);
            String n = helper(s, i, i + 1);
            if (m.length() > ans.length()) ans = m;
            if (n.length() > ans.length()) ans = n;
        }

        return ans;
    }

    private String helper(String s, int start, int end) {
        int i = start;
        int j = end;
        while (i >= 0 && j < s.length()) {
            if (s.charAt(i) != s.charAt(j)) {
                break;
            }
            i--;
            j++;
        }
        return s.substring(i + 1, j);
    }
}
~~~

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

**如何加快速度** <br>

Attempts: 6
~~~
public class Solution {
    public List<List<String>> partition(String s) {
        List<List<String>> ans = new ArrayList<List<String>>();

        // edge cases
        if (s == null) return ans;
        if (s.length() == 0) {
            ans.add(new ArrayList<String>());
            return ans;
        }

        backtrack(s, ans, new ArrayList<String>());
        return ans;
    }

    public void backtrack(String s, List<List<String>> ans, List<String> list) {
        // base case
        if (s.length() == 0) {
            ans.add(new ArrayList<String>(list));
            return;
        }

        for (int i = 1; i <= s.length(); i++) {
            String prefix = s.substring(0, i);
            if (isPalindrome(prefix)) {
                list.add(prefix);
                backtrack(s.substring(i), ans, list);
                list.remove(list.size() - 1);
            }
        }
    }

    private boolean isPalindrome(String s) {
        int i = 0;
        int j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) return false;
            i++;
            j--;
        }
        return true;
    }
}
~~~

## 132. Palindrome Partitioning II (Hard) *
Given a string s, partition s such that every substring of the partition is a palindrome.

Return the minimum cuts needed for a palindrome partitioning of s.

For example, given s = "aab",
Return 1 since the palindrome partitioning ["aa","b"] could be produced using 1 cut.

#### Solution
1. 最笨的DP，if (s[j:i] is palindrome) DP[i] = dp[j] + 1, i和j有n^2个可能性，每次判断if (s[j:i] is palindrome)需要O(n)的时间，总耗时O(n^3), 不出意料TLE, 如何加快速度
2. 可以提前计算一个palindrome lookup table, if (s[i] == s[j]) pal[i][j] = p[i+1][j-1]. **注意2维循环的时候i从高位到低位，j从低位到高位**，这样可以提高速度到O(n^2)
3. Discussion top solution 给出了O(n^2) time, O(n) space的方法

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

Attempt: 没有想出O(n^2)的方法
~~~
public class Solution {
    public int minCut(String s) {
        if (s == null || s.length() == 0) return 0;

        int len = s.length();

        // pre-calculate the palindrom look-up table
        boolean[][] palindrome = new boolean[len][len];
        for (int i = len - 1; i >= 0; i--) {
            for (int j = i; j < len; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    if (j - i < 2) {
                        palindrome[i][j] = true;
                    }
                    else {
                        palindrome[i][j] = palindrome[i + 1][j - 1];
                    }
                }
            }
        }

        int[] dp = new int[len];

        for (int i = 0; i < len; i++) {
            dp[i] = i; // dp[0] = 0
            for (int j = i; j >= 0; j--) {
                if (palindrome[j][i]) {
                    if (j == 0) dp[i] = 0;
                    else dp[i] = Math.min(dp[i], dp[j - 1] + 1);
                }
            }
        }

        return dp[len - 1];
    }
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

## 266. Palindrome Permutation (Easy)
Given a string, determine if a permutation of the string could form a palindrome.

For example,
"code" -> False, "aab" -> True, "carerac" -> True.

#### Solution
1. Two pass的方法，第一遍扫描这个str, 统计每个字符的词频，然后第二遍判断是否出现了>1的奇数词频的char
2. One pass 用set，char不在set里面，把char加进来，char在set里面，把char移除，最后判断set.size() == 0 || set.size() == 1 <br>
这题和409相似

## 409. Longest Palindrome (Easy)
Given a string which consists of lowercase or uppercase letters, find the length of the longest palindromes that can be built with those letters.

This is case sensitive, for example "Aa" is not considered a palindrome here.

Note:
Assume the length of given string will not exceed 1,010.

Example:
~~~
Input:
"abccccdd"

Output:
7

Explanation:
One longest palindrome that can be built is "dccaccd", whose length is 7.
~~~

#### Solution
1. 笨方法也是扫描两边，第一遍统计每个单词出现的频率，第二遍数数
2. One pass, 类似于266，用HashSet, char不在set里面，把char加进来，char在set里面，说明当前的char出现第二次了，count += 2, 最后如果set不为空的话，说明至少有一个char落单了，count += 1

## 267. Palindrome Permutation II
Given a string s, return all the palindromic permutations (without duplicates) of it. Return an empty list if no palindromic permutation could be form.

For example:

Given s = "aabb", return ["abba", "baab"].

Given s = "abc", return [].

#### Solution
We can solve the problem in two steps:
1. Find all characters occur along with its occurring time using HashMap. While doing this, we can examine whether a string can form a valid permutation or not, if invalid, simply return empty list.
2. Construct string (characters) permutation for the first half of the palindrome using backtracking. Construct an answer and add it to result list.

方法1： 用Map统计字符，用List做Backtracking <br>
注意错误写法char ch = '';因为char必须严格遵守一个字符的定义
~~~
public class Solution {
    public List<String> generatePalindromes(String s) {
        List<String> ans = new ArrayList<String>();
        if (s == null) return ans;

        Map<Character, Integer> map = new HashMap<Character, Integer>();
        Set<Character> set = new HashSet<Character>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            map.put(ch, map.getOrDefault(ch, 0) + 1);

            if (set.contains(ch)) set.remove(ch);
            else set.add(ch);
        }

        if (set.size() > 1) return ans;

        // if there is a valid char with lonely 1 occurence set it to ch
        Character ch = null;
        if (set.size() == 1) ch = set.iterator().next();

        // covert the map to char list and sort in lexicography order
        List<Character> list = new ArrayList<Character>();
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            for (int i = 0; i < entry.getValue() / 2; i++) list.add(entry.getKey());
        }
        Collections.sort(list);

        helper(ans, list, new StringBuilder(), ch, new boolean[list.size()]);
        return ans;
    }

    private void helper(List<String> ans, List<Character> list, StringBuilder sb, Character ch, boolean[] used) {
        // base case
        if (sb.length() == list.size()) {
            String reversed = new StringBuilder(sb).reverse().toString();
            StringBuilder newSb = new StringBuilder(sb);
            if (ch != null) newSb.append(ch);
            newSb.append(reversed);
            ans.add(newSb.toString());
            return;
        }

        // backtracking
        for (int i = 0; i < list.size(); i++) {
            if (used[i] || (i > 0 && list.get(i) == list.get(i - 1) && !used[i - 1])) {
                continue;
            }

            sb.append(list.get(i));
            used[i] = true;
            helper(ans, list, sb, ch, used);
            sb.deleteCharAt(sb.length() - 1);
            used[i] = false;
        }
    }
}
~~~

方法2：用int[256]数组统计字符，用char[]数组做bakctracking
~~~
public class Solution {
    public List<String> generatePalindromes(String s) {
        List<String> ans = new ArrayList<String>();
        if (s == null) return ans;

        int[] dict = new int[256];
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            dict[ch]++;
        }

        char[] chars = new char[s.length() / 2];
        int index = 0;
        boolean odd = false;
        Character ch = null;
        for (int i = 0; i < 256; i++) {
            if (dict[i] % 2 != 0) {
                if (odd) return ans;
                odd = true;
                ch = (char) i;
            }
            for (int j = 0; j < dict[i] / 2; j++) {
                chars[index++] = (char) i;
            }
        }

        helper(ans, chars, new StringBuilder(), ch, new boolean[s.length() / 2]);
        return ans;
    }

    private void helper(List<String> ans, char[] chars, StringBuilder sb, Character ch, boolean[] used) {
        // base case
        if (sb.length() == chars.length) {
            String reversed = new StringBuilder(sb).reverse().toString();
            StringBuilder newSb = new StringBuilder(sb);
            if (ch != null) newSb.append(ch);
            newSb.append(reversed);
            ans.add(newSb.toString());
            return;
        }

        // backtracking
        for (int i = 0; i < chars.length; i++) {
            if (used[i] || (i > 0 && chars[i] == chars[i - 1] && !used[i - 1])) {
                continue;
            }

            sb.append(chars[i]);
            used[i] = true;
            helper(ans, chars, sb, ch, used);
            sb.deleteCharAt(sb.length() - 1);
            used[i] = false;
        }
    }
}
~~~

## 336. Palindrome Pairs
Given a list of unique words, find all pairs of distinct indices (i, j) in the given list, so that the concatenation of the two words, i.e. words[i] + words[j] is a palindrome.

Example 1:
Given words = ["bat", "tab", "cat"]
Return [[0, 1], [1, 0]]
The palindromes are ["battab", "tabbat"]
Example 2:
Given words = ["abcd", "dcba", "lls", "s", "sssll"]
Return [[0, 1], [1, 0], [3, 2], [2, 4]]
The palindromes are ["dcbaabcd", "abcddcba", "slls", "llssssll"]

#### Solution
1. Brute force, check every pair of string, time complexity O(n^2k)
2. Brute force, for every word, check whether any str that can make the word a valid palindrome exists, time complexity O(nk^2)

**处理“”的edge cases** <br>
**去除重复** （偷懒用了Set）

~~~
public class Solution {
    public List<List<Integer>> palindromePairs(String[] words) {
        if (words == null || words.length == 0) return new ArrayList<List<Integer>>();

        Map<String, Integer> map = new HashMap<String, Integer>();
        for (int i = 0; i < words.length; i++) {
            map.put(words[i], i);
        }

        Set<List<Integer>> set = new HashSet<List<Integer>>();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            for (int j = 0; j <= word.length(); j++) {
                String prefix = word.substring(0, j);
                String suffix = word.substring(j);
                if (isValidPalindrome(prefix)) {
                    StringBuilder sb = new StringBuilder(suffix);
                    String str = sb.reverse().toString();
                    if (map.containsKey(str) && map.get(str) != i) {
                        List<Integer> list = new ArrayList<Integer>();
                        list.add(map.get(str));
                        list.add(i);
                        set.add(list);
                    }
                }
                if (isValidPalindrome(suffix)) {
                    StringBuilder sb = new StringBuilder(prefix);
                    String str = sb.reverse().toString();
                    if (map.containsKey(str) && map.get(str) != i) {
                        List<Integer> list = new ArrayList<Integer>();
                        list.add(i);
                        list.add(map.get(str));
                        set.add(list);
                    }
                }
            }
        }

        return new ArrayList<List<Integer>>(set);
    }

    private boolean isValidPalindrome(String word) {
        int i = 0;
        int j = word.length() - 1;
        while (i < j) {
            if (word.charAt(i++) != word.charAt(j--)) {
                return false;
            }
        }
        return true;
    }
}
~~~

## 516. Longest Palindromic Subsequence
Given a string s, find the longest palindromic subsequence's length in s. You may assume that the maximum length of s is 1000.

Example 1:
Input:
~~~
"bbbab"
~~~
Output:
~~~
4
~~~
One possible longest palindromic subsequence is "bbbb".
Example 2:
Input:
~~~
"cbbd"
~~~
Output:
~~~
2
~~~
One possible longest palindromic subsequence is "bb".

#### Solution
DP

~~~
public class Solution {
    public int longestPalindromeSubseq(String s) {
        if (s == null || s.length() == 0) return 0;
        int len = s.length();
        int ans = 1;
        int[][] dp = new int[len][len];
        for (int i = len - 1; i >= 0; i--) {
            dp[i][i] = 1;
            for (int j = i + 1; j < len; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                }
                else {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i + 1][j]);
                }
                ans = Math.max(ans, dp[i][j]);
            }
        }

        return ans;
    }
}
~~~

## 479. Largest Palindrome Product
Find the largest palindrome made from the product of two n-digit numbers.

Since the result could be very large, you should return the largest palindrome mod 1337.

Example:

Input: 2

Output: 987

Explanation: 99 x 91 = 9009, 9009 % 1337 = 987

Note:

The range of n is [1,8].

#### Solution
1. 找出能组成的最大product, 用left half生成palindrome.
2. 判断生成的palindrome是否可以被n-digits分解
  - 这里从最大的数字开始检测，为了加快速度一旦发现product > i * i就break
3. 如果不能被分解，那么left--，然后repeat去检测生成的palindrome是不是可以被分解

~~~
public class Solution {
    public int largestPalindrome(int n) {
        if (n <= 0) return 0;
        if (n == 1) return 9;

        long upper = (long) Math.pow(10, n) - 1;
        long lower = upper / 10;
        long maxProduct = upper * upper;
        long left = maxProduct / (long) Math.pow(10, n);

        while (left > lower) {
            long maxPal = generatePalindrome(left);
            for (long i = upper; i > lower; i--) {
                if (maxPal > i * i) break;
                if (maxPal % i == 0) {
                    return (int)(maxPal % 1337);
                }
            }
            left--;
        }

        return 0;        
    }

    public long generatePalindrome(long left) {
        StringBuilder pal = new StringBuilder();
        StringBuilder sb = new StringBuilder(String.valueOf(left));
        pal.append(left).append(sb.reverse());
        return Long.valueOf(pal.toString());
    }
}
~~~
