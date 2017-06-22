## 139. Word Break (Medium)
Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, determine if s can be segmented into a space-separated sequence of one or more dictionary words. You may assume the dictionary does not contain duplicate words.

For example, given
s = "leetcode",
dict = ["leet", "code"].

Return true because "leetcode" can be segmented as "leet code".

#### Solution
1. One dimension array dp[], dp[i] means whether a substring from 0 to i (exclusive) has a valid word break or not.
2. Transition formula: dp[i] = (dp[0] && dict.contains(s.substring(0, i + 1))) || (dp[1] && dict.contains(s.substring(1, i + 1)))) || ... || (dp[i] && dict.contains(s.substring(i, i + 1)))).

m - the length of s; <br>
n - the size of dict; <br>
k - the avg length of word in dict. <br>
Time Complexity O(nmk). <br>

~~~
public class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        // non-empty string s and non-empty wordDict
        Set<String> set = new HashSet<String>();
        for (String str : wordDict) {
            set.add(str);    
        }

        int len = s.length();
        boolean[] dp = new boolean[len + 1];
        dp[0] = true;
        for (int i = 1; i < len + 1; i++) {
            for (String str : set) {
                int l = str.length(); // the length of the curr word
                if (i >= l && str.equals(s.substring(i - l, i)) && dp[i - l]) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[len];
    }
}
~~~

## 140. Word Break II (Hard)
Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, add spaces in s to construct a sentence where each word is a valid dictionary word. You may assume the dictionary does not contain duplicate words.

Return all such possible sentences.

For example, given
s = "catsanddog",
dict = ["cat", "cats", "and", "sand", "dog"].

A solution is ["cats and dog", "cat sand dog"].

#### Solution
m - the length of s; <br>
n - the size of dict; <br>
k - the avg length of word in dict. <br>
Word Break, use DP time complexity O(nmk). <br>
For 140. Word Break II, typical backtracking + DP (cached intermediate result). <br>

Niiave backtacking got TLE with the test case:
~~~~
"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
["a","aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa","aaaaaaaaaa"]

answer: []
~~~~

Use a HashMap<String, LinkedList<String>> map to store word breaks of substrings, so as to avoid duplicated search.

**Complexity Analysis**
Time: worst case, call backtrack func to check every suffixes of s (fixed end), m times.
Each time, iterate the whole dict and compare string nk.
Check discussion [here](https://stackoverflow.com/questions/21273505/memoization-algorithm-time-complexity).
Space: O(kn) store the word as set.

~~~
public class Solution {
    public List<String> wordBreak(String s, List<String> wordDict) {
        Set<String> set = new HashSet<String>();
        for (String str : wordDict) {
            set.add(str);
        }

        return backtrack(new HashMap<String, List<String>>(), set, s);
    }

    private List<String> backtrack(Map<String, List<String>> cache, Set<String> wordDict, String s) {

        if (cache.containsKey(s)) return cache.get(s);
        List<String> ans = new ArrayList<String>();
        if (s.length() == 0) {
            ans.add(" ");
            return ans;
        }

        for (String str : wordDict) {
            if (s.startsWith(str)) {
                List<String> list = backtrack(cache, wordDict, s.substring(str.length())); // the word break result of substring
                for (String item : list) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str).append(" ").append(item);
                    ans.add(sb.toString().trim());
                }
            }
        }
        cache.put(s, ans);
        return ans;
    }
}
~~~

---

## 79. Word Search
Given a 2D board and a word, find if the word exists in the grid.

The word can be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once.

For example,
Given board =

[
  ['A','B','C','E'],
  ['S','F','C','S'],
  ['A','D','E','E']
]
word = "ABCCED", -> returns true,
word = "SEE", -> returns true,
word = "ABCB", -> returns false.

#### Solution
DFS with backtracking.
What if we cannot allocate new space, then mark visited or not by set board in-place, board[y][x] ^= 256;

**Complexity Analysis**
Time complexity: exponential O(mn * 2^mn)
Space complexity: O(mn)

~~~
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
~~~

## 212. Word Search II
Given a 2D board and a list of words from the dictionary, find all words in the board.

Each word must be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once in a word.

For example,
Given words = ["oath","pea","eat","rain"] and board =

[
  ['o','a','a','n'],
  ['e','t','a','e'],
  ['i','h','k','r'],
  ['i','f','l','v']
]
Return ["eat","oath"].
Note:
You may assume that all inputs are consist of lowercase letters a-z.

#### Solution
1. The key is to use Tries (How to implement Tires?)
2. Then DFS + backtrack search.

**Note**
Always consider the test case
~~~
["a"]
["a"]
~~~
If you trigger backtrack by checking whether x, y is within board, will you add "a" as a valid ans?

To speed up, check the [discussion](https://discuss.leetcode.com/topic/33246/java-15ms-easiest-solution-100-00)

~~~
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
~~~

---

## 127. Word Ladder
Given two words (beginWord and endWord), and a dictionary's word list, find the length of shortest transformation sequence from beginWord to endWord, such that:

Only one letter can be changed at a time.
Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
For example,

Given:
beginWord = "hit"
endWord = "cog"
wordList = ["hot","dot","dog","lot","log","cog"]
As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
return its length 5.

Note:
Return 0 if there is no such transformation sequence.
All words have the same length.
All words contain only lowercase alphabetic characters.
You may assume no duplicates in the word list.
You may assume beginWord and endWord are non-empty and are not the same.

#### Solution
1. We should use BFS.
**No need to build graph!**
2. How to optimize?
- When searching for next word, rather than iterate the wordDict, change the curr word by 1 edit (replace) distance. (Follow up: what if we change the problem to that each step, you can replace/delete/add character?)
-  use two-end BFS (how to code two-end BFS )

**Complexity Analysis**
Time complexity: visit word in dict once O(n), and for each word, we change its possible transformation 26 * len(word). Therefore, in total, the time complexity is O(n * len(word))
Space complexity: Set to store word set O(n*  len(word))

Version 1: Use Queue
~~~
public class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<String>();
        for (String str : wordList) {
            wordSet.add(str);
        }

        Queue<String> queue = new LinkedList<String>();
        queue.offer(beginWord);
        int len = beginWord.length();
        int depth = 1;
        while (!queue.isEmpty()) {
            int count = queue.size();
            while (count > 0) {
                String node = queue.poll();

                if (node.equals(endWord)) return depth; // found the target, return depth

                // add adjacent word to the queue
                for (int i = 0; i < len; i++) {
                    char chi = node.charAt(i);
                    String prefix = node.substring(0, i);
                    String suffix = node.substring(i + 1);
                    for (char chj = 'a'; chj <= 'z'; chj++) {
                        if (chi == chj) continue;
                        StringBuilder sb = new StringBuilder();
                        sb.append(prefix).append(chj).append(suffix);
                        String next = sb.toString();
                        if (wordSet.contains(next)) {
                            queue.offer(next);
                            wordSet.remove(next);
                        }
                    }
                }

                count--;
            }

            depth++;
        }

        return 0;
    }
}
~~~

Version 2: Use Set
~~~
public class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<String>();
        for (String str : wordList) {
            wordSet.add(str);
        }

        int len = beginWord.length();
        Set<String> set = new HashSet<String>();
        set.add(beginWord);
        int depth = 1;
        while (!set.isEmpty()) {
            Set<String> nextSet = new HashSet<String>();
            for (String node : set) {
                if (node.equals(endWord)) return depth; // found the target, return depth

                // add adjacent word to the queue
                for (int i = 0; i < len; i++) {
                    char chi = node.charAt(i);
                    String prefix = node.substring(0, i);
                    String suffix = node.substring(i + 1);
                    for (char chj = 'a'; chj <= 'z'; chj++) {
                        if (chi == chj) continue;
                        StringBuilder sb = new StringBuilder();
                        sb.append(prefix).append(chj).append(suffix);
                        String next = sb.toString();
                        if (wordSet.contains(next)) {
                            nextSet.add(next);
                            wordSet.remove(next);
                        }
                    }
                }
            }
            set = nextSet;
            depth++;
        }

        return 0;
    }
}
~~~

Version 3: Two-end BFS
~~~
public class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<String>();
        for (String str : wordList) {
            wordSet.add(str);
        }

        int len = beginWord.length();
        Set<String> startSet = new HashSet<String>();
        startSet.add(beginWord);

        if (!wordSet.contains(endWord)) return 0; // IMPORTANT: consider that endWord does not exit in the wordList, then we cannot continue to add the endWord to endSet, simply return 0
        Set<String> endSet = new HashSet<String>();
        endSet.add(endWord);

        int depth = 1;
        while (!startSet.isEmpty() && !endSet.isEmpty()) {
            if (startSet.size() > endSet.size()) {
                Set<String> tmp = startSet;
                startSet= endSet;
                endSet = tmp;
            }

            Set<String> nextSet = new HashSet<String>();
            for (String node : startSet) {
                // if (endSet.contains(node)) return depth; // wrong version

                // add adjacent word to the queue
                for (int i = 0; i < len; i++) {
                    char chi = node.charAt(i);
                    String prefix = node.substring(0, i);
                    String suffix = node.substring(i + 1);
                    for (char chj = 'a'; chj <= 'z'; chj++) {
                        if (chi == chj) continue;
                        StringBuilder sb = new StringBuilder();
                        sb.append(prefix).append(chj).append(suffix);
                        String next = sb.toString();
                        if (endSet.contains(next)) return depth + 1;
                        if (wordSet.contains(next)) {
                            nextSet.add(next);
                            wordSet.remove(next);
                        }
                    }
                }
            }
            startSet = nextSet;
            depth++;
        }

        return 0;
    }
}
~~~

## 126. Word Ladder II
Given two words (beginWord and endWord), and a dictionary's word list, find all shortest transformation sequence(s) from beginWord to endWord, such that:

Only one letter can be changed at a time
Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
For example,

Given:
beginWord = "hit"
endWord = "cog"
wordList = ["hot","dot","dog","lot","log","cog"]
Return
  [
    ["hit","hot","dot","dog","cog"],
    ["hit","hot","lot","log","cog"]
  ]
Note:
Return an empty list if there is no such transformation sequence.
All words have the same length.
All words contain only lowercase alphabetic characters.
You may assume no duplicates in the word list.
You may assume beginWord and endWord are non-empty and are not the same.

#### Solution
- Tried to modify Word Ladder I (version 1), but got TLE.

---

## 151. Reverse Words in a String
Given an input string, reverse the string word by word.

For example,
Given s = "the sky is blue",
return "blue is sky the".

## 186. Reverse Words in a String II
Given an input string, reverse the string word by word. A word is defined as a sequence of non-space characters.

The input string does not contain leading or trailing spaces and the words are always separated by a single space.

For example,
Given s = "the sky is blue",
return "blue is sky the".

Could you do it in-place without allocating extra space?

## 557. Reverse Words in a String III
Given a string, you need to reverse the order of characters in each word within a sentence while still preserving whitespace and initial word order.

Example 1:
Input: "Let's take LeetCode contest"
Output: "s'teL ekat edoCteeL tsetnoc"
Note: In the string, each word is separated by single space and there will not be any extra space in the string.

#### Solution
- Without restriction 151 and 557, the problem is easy. When with restriction like 186, we need to do it in-place, we can first reverse the whole string in-place and reverse each word in-place.

---
