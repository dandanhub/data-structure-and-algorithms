## 68. Text Justification
Given an array of words and a length L, format the text such that each line has exactly L characters and is fully (left and right) justified.

You should pack your words in a greedy approach; that is, pack as many words as you can in each line. Pad extra spaces ' ' when necessary so that each line has exactly L characters.

Extra spaces between words should be distributed as evenly as possible. If the number of spaces on a line do not divide evenly between words, the empty slots on the left will be assigned more spaces than the slots on the right.

For the last line of text, it should be left justified and no extra space is inserted between words.

For example,
words: ["This", "is", "an", "example", "of", "text", "justification."]
L: 16.

Return the formatted lines as:
~~~
[
   "This    is    an",
   "example  of text",
   "justification.  "
]
~~~
Note: Each word is guaranteed not to exceed L in length.

#### Solution
1. 找出一行能放得单词index的start和end
2. helper function根据start index和end index构建String.

Verbose Version
~~~
public class Solution {
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> list = new ArrayList<String>();
        if (words == null || words.length == 0) return list;

        for (int i = 0; i < words.length; i++) {
            int start = i; // inclusive
            int len = 0;
            while (i < words.length && len + words[i].length() <= maxWidth) {
                len += words[i++].length() + 1;
            }
            if (i != start) i--;
            int end = i; // inclusive

            String str = buildStr(words, maxWidth, start, end);
            list.add(str);
        }

        return list;
    }

    private String buildStr(String[] words, int maxWidth, int s, int e) {
        int len = 0;
        StringBuilder sb = new StringBuilder();

        if (e == words.length - 1 || s == e) { // last row
            for (int i = s; i < e; i++) {
                sb.append(words[i]);
                sb.append(" ");
            }
            sb.append(words[e]);
            while (sb.length() < maxWidth) sb.append(" ");
            return sb.toString();
        }

        for (int i = s; i <= e; i++) {
            len += words[i].length();
        }

        // construct space str
        int space = maxWidth - len;
        int gap = e - s;
        StringBuilder spaceSb = new StringBuilder();
        for (int i = 0; i < space / gap; i++)  spaceSb.append(" ");
        String spaceStr = spaceSb.toString();

        int reminder = space % gap;

        for (int i = s; i < e; i++) {
            sb.append(words[i]);
            sb.append(spaceStr);
            if (reminder > 0) {
                sb.append(" ");
                reminder--;
            }
        }
        sb.append(words[e]);

        return sb.toString();
    }
}
~~~

~~~
Round 1 : 经典老题，text justification，LC68，但是条件做了细小的改动，要求只有当每行是一个单词时左对齐，其余的每行全都均匀分布，需要注意一下判断条件。
~~~

~~~
Greedy is not optimal, old MS Word use this strategy.
~~~

## MIT Text Justification
Split text into "good lines". <br>
text = list of words <br>
badness(i, j) -> use words[i : j] as line <br>
- (page width - total width) ^ 3
- infinite if don't fit
Target: minimize the total badness <br>

1. Subproblem: suffixes words[i:] <br>
num of subproblems n
2. Guess: where to start 2nd line <br>
num of guess <= n - i = O(n)
3. Recurrence relation: min(dp(i) = dp(j) + badness(i, j) for j in range(i+1, n+1))   (python) <br>
for (int j = i; j < len; j++) dp[i] = Math.max(dp[i], badness[i][j] + dp[j])
4. topo order, n, n-1, ..., 0

Draft version
~~~
public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> list = new ArrayList<String>();
        if (words == null || words.length == 0) return list;

        int len = words.length;
        int[] dp = new int[len + 1]; // text justification cost of substring(i, len)
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[len] = 0;

        int[] parents = new int[len];
        // parents[len] = len;

        for (int i = len - 1; i >= 0; i--) {
           for (int j = i; j < len; j++) {
               int value = badness(words, maxWidth, i, j);
               if (value == -1) break;
               if (value + dp[j + 1] < dp[i]) {
                   dp[i] = value + dp[j + 1];
                   parents[i] = j;
               }
           }
        }

        for (int i = 0; i < len; i++) System.out.print(parents[i] + " ");
        System.out.println("");
        return list;
    }
~~~
