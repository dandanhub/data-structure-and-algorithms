The java codes here basically summarize my work on hash table. The codes are driven by CMU course 08-722 Data Structures for Application Programmers and some LeetCode problems related about hash table.

# HashTable.java

## 202. Happy Number (Easy) *
Write an algorithm to determine if a number is "happy".

A happy number is a number defined by the following process: Starting with any positive integer, replace the number by the sum of the squares of its digits, and repeat the process until the number equals 1 (where it will stay), or it loops endlessly in a cycle which does not include 1. Those numbers for which this process ends in 1 are happy numbers.

Example: 19 is a happy number

12 + 92 = 82
82 + 22 = 68
62 + 82 = 100
12 + 02 + 02 = 1

#### Solution
1. The first thought is to store numbers we have seen into a hash set and once we meet a num we have seen before, we are in a loop. Then the number is not a happy number. The space complexity is O(n).
2. The [leetcode discussion](https://discuss.leetcode.com/topic/12587/my-solution-in-c-o-1-space-and-no-magic-math-property-involved) provides a solution with O(1) time space. The idea is similar to detect loop in a linked list. We have two numbers, the fast one move two steps further while the slow one move one step at each time.

## 187. Repeated DNA Sequences (Medium)
All DNA is composed of a series of nucleotides abbreviated as A, C, G, and T, for example: "ACGAATTCCG". When studying DNA, it is sometimes useful to identify repeated sequences within the DNA.

Write a function to find all the 10-letter-long sequences (substrings) that occur more than once in a DNA molecule.

For example,
~~~~
Given s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT",

Return:
["AAAAACCCCC", "CCCCCAAAAA"].
~~~~

#### Solution
Iterate the string in one-pass with 10-character sliding windows. Use a map to store 10-character string we have seen, if a string shows two or more times, add it into the final result.

## 266. Palindrome Permutation (Easy) *
Given a string, determine if a permutation of the string could form a palindrome.

For example,
"code" -> False, "aab" -> True, "carerac" -> True.

#### Solution
We count the time that a character occurs in the string. If there is more than one character occurs odd time, then the string cannot form a palindrome. If there is only one character occurs odd time, then return true.

# ValidWordAbbr.java
## 288. Unique Word Abbreviation (Medium) *
The problem description is not so clear, refer to [leetcode discussion](https://discuss.leetcode.com/topic/37254/let-me-explain-the-question-with-better-examples) for clear description.
1. My naiive solution is to use a data structure `Map<String, List<String>> map;`, where the key is the abbreviated string and the value is all words in the dictionary abbreviated to the key.
  - To determine whether a word is unique, I retrieve the corresponding list by searching abbreviated word.
  - Then, I iterate through the list to determine whether is any dictionary word other than the query one, that has the same abbreviation.

  2. The naiive solution is slow because it iterate the retrieved list. We can improve it much according to the [leetcode discussion](https://discuss.leetcode.com/topic/30533/java-solution-with-one-hashmap-string-string-beats-90-of-submissions)
