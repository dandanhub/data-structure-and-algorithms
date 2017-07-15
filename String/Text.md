## 418. Sentence Screen Fitting
Given a rows x cols screen and a sentence represented by a list of non-empty words, find how many times the given sentence can be fitted on the screen.

Note:

A word cannot be split into two lines.
The order of words in the sentence must remain unchanged.
Two consecutive words in a line must be separated by a single space.
Total words in the sentence won't exceed 100.
Length of each word is greater than 0 and won't exceed 10.
1 ≤ rows, cols ≤ 20,000.
Example 1:
~~~
Input:
rows = 2, cols = 8, sentence = ["hello", "world"]

Output:
1

Explanation:
hello---
world---

The character '-' signifies an empty space on the screen.
~~~

Example 2:
~~~
Input:
rows = 3, cols = 6, sentence = ["a", "bcd", "e"]

Output:
2

Explanation:
a-bcd-
e-a---
bcd-e-

The character '-' signifies an empty space on the screen.
~~~

Example 3:
~~~
Input:
rows = 4, cols = 5, sentence = ["I", "had", "apple", "pie"]

Output:
1

Explanation:
I-had
apple
pie-I
had--

The character '-' signifies an empty space on the screen.
~~~

#### Solution
最开始的方法，每个单词遍历判断，但是很不高效。考虑如果单词很短很少，但是cols很长。下面这段代码基本上是要TLE的，794 ms.
~~~
public class Solution {
    public int wordsTyping(String[] sentence, int rows, int cols) {
        int count = 0;
        int curr = 0;
        int i = 0;
        while (i < rows) {
            int j = 0;
            while (j < cols) {
                if (sentence[curr].length() > cols) return 0;
                if (cols - j >= sentence[curr].length()) {
                    j += sentence[curr].length() + 1;
                    curr = curr + 1; // move index of sentence forward
                    if (curr == sentence.length) {
                        count++;
                        // if the same row cannot fit more sentence
                        if (cols - j < sentence[0].length()) {
                            count = count * (rows / (i + 1));
                            i = rows - rows % (i + 1) - 1;
                        }
                        curr = 0;
                    }
                }
                else break;
            }
            i++;
        }

        return count;
    }
}
~~~

在Method 1的基础上优化，21ms，考虑到rows和cols都很大的情况
~~~
public class Solution {
    public int wordsTyping(String[] sentence, int rows, int cols) {
        int count = 0;
        int curr = 0;

        int len = 0;
        for (int k = 0; k < sentence.length; k++) len += sentence[k].length() + 1;

        int i = 0;
        while (i < rows) {
            int j = 0;
            while (j < cols) {
                if (sentence[curr].length() > cols) return 0;
                if (cols - j >= sentence[curr].length()) {
                    if (cols - j >= len) {
                        count += cols / len;
                        j = cols - (cols % len);
                    }
                    else {
                        j += sentence[curr].length() + 1;
                        curr = curr + 1; // move index of sentence forward
                    }
                    if (curr == sentence.length) {
                        count++;
                        // if the same row cannot fit more sentence
                        if (cols - j < sentence[0].length()) {
                            count = count * (rows / (i + 1));
                            i = rows - rows % (i + 1) - 1;
                        }
                        curr = 0;
                    }
                }
                else break;
            }
            i++;
        }

        return count;
    }
}
~~~

例外一个做法是参考[discussion](https://discuss.leetcode.com/topic/62455/21ms-18-lines-java-solution)
