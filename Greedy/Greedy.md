## 621. Task Scheduler
Given a char array representing tasks CPU need to do. It contains capital letters A to Z where different letters represent different tasks.Tasks could be done without original order. Each task could be done in one interval. For each interval, CPU could finish one task or just be idle.

However, there is a non-negative cooling interval n that means between two same tasks, there must be at least n intervals that CPU are doing different tasks or just be idle.

You need to return the least number of intervals the CPU will take to finish all the given tasks.

Example 1:
~~~
Input: tasks = ['A','A','A','B','B','B'], n = 2
Output: 8
Explanation: A -> B -> idle -> A -> B -> idle -> A -> B.
~~~

Note:
The number of tasks is in the range [1, 10000].
The integer n is in the range [0, 100].

#### Solution
出现次数最高的char决定了最终长度
~~~
public class Solution {
    public int leastInterval(char[] tasks, int n) {
        if (tasks == null || tasks.length == 0) return 0;
        if (n <= 0) return tasks.length;

        // calculate dict freq
        int[] dict = new int[26];
        for (int i = 0; i < tasks.length; i++) {
            dict[tasks[i] - 'A']++;
        }

        Arrays.sort(dict);
        int i = 25;
        // find the count of char that have the highest freq
        while (i > 0 && dict[i] == dict[i - 1]) {
            i--;
        }
        // if ((26 - i == n && i > 0 && dict[i - 1] != 0) || 26 - i > n) return tasks.length;
        return Math.max(tasks.length, (n + 1) * (dict[25] - 1) + (26 - i));
    }
}
~~~

## 358. Rearrange String k Distance Apart
Given a non-empty string s and an integer k, rearrange the string such that the same characters are at least distance k from each other.

All input strings are given in lowercase letters. If it is not possible to rearrange the string, return an empty string "".

Example 1:
s = "aabbcc", k = 3

Result: "abcabc"

The same letters are at least distance 3 from each other.
Example 2:
s = "aaabc", k = 3

Answer: ""

It is not possible to rearrange the string.
Example 3:
s = "aaadbbcc", k = 2

Answer: "abacabcd"

Another possible answer is: "abcabcda"

The same letters are at least distance 2 from each other.

#### Solution
每次选取下一个合法的且出现频率最高的字符 <br>
Method 1: 由于题目限定只有26个小写字母，所以可以O(n)的时间选取出合法的字符
~~~
public class Solution {
    public String rearrangeString(String s, int k) {
        if (s == null || s.length() == 0 || k <= 0) return s;

        int[] dict = new int[26];
        for (int i = 0; i < s.length(); i++) {
            dict[s.charAt(i) - 'a']++;
        }

        boolean[] visited = new boolean[26];
        StringBuilder sb = new StringBuilder();
        while (sb.length() < s.length()) {
            // select a valid char with the highest freq
            int index = -1;
            int max = 0;
            for (int i = 0; i < 26; i++) {
                if (dict[i] > max && !visited[i]) {
                    max = dict[i];
                    index = i;
                }
            }

            if (index == -1) return "";
            // add char into res
            sb.append((char)('a' + index));
            dict[index]--;
            visited[index] = true;
            if (sb.length() >= k) visited[sb.charAt(sb.length() - k) - 'a'] = false;
        }

        return sb.toString();
    }
}
~~~

TODO Method 2: 如果变成数字，则需要用PQ来选取最高词频的合法字符


~~~
2016-10-15
第一题就是给你一堆task和一个cooldown time，执行相同的task一定要过了cooldown time那么长的gap才可以，不需要reorder，求最后一共的时间。我用的hashmap，但是第一次电面太紧张，写了好久，run了两次testcase，还好他没发现啥bug，然后说一下时间空间复杂度，看看能不能优化。

第二题是第一题的follow up，可以reorder，求最短时间。时间不多了，只要我写一下思路。我一开始没反应过来，想用hashmap，然后说了半天发现应该用heap。。。把frequency排序。

我就是写的LC358，基本一样的，只不过facebook给的是integer序号，leetcode是字母
~~~
