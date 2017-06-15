## KMP

#### What is KMP?
We can refer to [this Chinese blog](http://www.ruanyifeng.com/blog/2013/05/Knuth%E2%80%93Morris%E2%80%93Pratt_algorithm.html).

#### How to build KMP table?

- A B C D A B D
- 0 0 0 0 1 2 0

- Prefix and Postfix
- A: [] and [] -> 0
- AB: [A] and [B] -> 0
- ABC: [A, AB] and [C, BC] -> 0
- ABCD: [A, AB, ABC] and [D, CD, BCD] -> 0
- ABCDA: [A, AB, ABC, ABCD] and [A, DA, CDA, BCDA] -> 1
- ABCDAB: [A, AB, ABC, ABCD, ABCDB] and [B, AB, DAB, CDAB, BCDAB] -> 2
- ...

When thinking about how to build KMP table, think it as failure pointer.
Use test case "ababcaabc".

#### 28. Implement strStr()
Implement strStr().

Returns the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.

###### Solution
1. Naiive way
m - the length of haystack
n - the length of needle
time complexity O(mn)
2. Apply KMP.
time complexity O(m + n)
"Since the two portions of the algorithm have, respectively, complexities of O(k) and O(n), the complexity of the overall algorithm is O(n + k). So the total cost of a KMP search is linear in the number of characters of string and pattern."

~~~
public class Solution {
    /*
    * 28. Implement strStr()
    * Using KMP
    */
    public int strStr(String haystack, String needle) {
        // "", "", return 0
        if (needle == null || needle.length() == 0) return 0;
        if (haystack == null || haystack.length() == 0) return -1;

        int[] KMP = buildKMP(needle);
        int i = 0, j = 0;
        // O(n)
        while (i < haystack.length() && j < needle.length()) {
            if (haystack.charAt(i) == needle.charAt(j)) {
                j++;
                i++;
            }
            else {
                if (j > 0) {
                    // 移动位数 = 已匹配的字符数 - 对应的部分匹配值
                    j = KMP[j - 1]; // move j to KMP[j - 1] and keep i in cur pos
                }
                else { // the first char not matching
                  i++;
                }
            }
        }
        if (j == needle.length()) return i - needle.length();
        return -1;
    }

    // O(m)
    private int[] buildKMP(String str) {
        if (str == null || str.length() == 0) return new int[0];

        int len = str.length();
        int[] KMP = new int[len];
        int index = 0;
        for (int i = 1; i < len; i++) {
            index = KMP[i - 1];
            if (str.charAt(index) == str.charAt(i)) {
                KMP[i] = index + 1;
            }
            else {
                while (index > 0 && str.charAt(i) != str.charAt(index)) {
                    index = KMP[index - 1];
                }
                if (str.charAt(i) == str.charAt(index)) KMP[i] = index + 1;
            }
        }
        System.out.println(Arrays.toString(KMP));
        return KMP;
    }
}
~~~

#### 214. Shortest Palindrome
Given a string S, you are allowed to convert it to a palindrome by adding characters in front of it. Find and return the shortest palindrome you can find by performing this transformation.

For example:
Given "aacecaaa", return "aaacecaaa".
Given "abcd", return "dcbabcd".

###### Solution
Here we only need to build KMP table to find the longest palindrome starting from 0 position.

1. Build a new string, e.g. for input catacb, build new string catacb#bcatac.
2. Build KMP table on the new string.
3. The longest palindrome staring from 0 is the value of the last character in the table.

It takes O(n) to get the solution. The code to build KMP table is kind of complicated.
