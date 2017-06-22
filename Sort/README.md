Sort.md basically summarizes my work on Sorting algorithm.
The codes are driven by CMU course 08-722 Data Structures for Application Programmers and some LeetCode problems related about Sorting.

**About Arrays.sort() function in Java**
Java sort in Arrays, implementation note: The sorting algorithm is a Dual-Pivot Quicksort by Vladimir Yaroslavskiy, Jon Bentley, and Joshua Bloch. This algorithm offers O(n log(n)) performance on many data sets that cause other quicksorts to degrade to quadratic performance, and is typically faster than traditional (one-pivot) Quicksort implementations.


## 296. Best Meeting Point (Hard) *
A group of two or more people wants to meet and minimize the total travel distance. You are given a 2D grid of values 0 or 1, where each 1 marks the home of someone in the group. The distance is calculated using Manhattan Distance, where distance(p1, p2) = |p2.x - p1.x| + |p2.y - p1.y|.

For example, given three people living at (0,0), (0,4), and (2,2):
~~~~
1 - 0 - 0 - 0 - 1
|   |   |   |   |
0 - 0 - 0 - 0 - 0
|   |   |   |   |
0 - 0 - 1 - 0 - 0
~~~~
The point (0,2) is an ideal meeting point, as the total travel distance of 2+2+2=6 is minimal. So return 6.

Hint:
Try to solve it in one dimension first. How can this solution apply to the two dimension case?

#### Solution
The key here is to understand the medium minimizes the sum of absolute deviations. Read more about the mathematic proof from [here](http://math.stackexchange.com/questions/113270/the-median-minimizes-the-sum-of-absolute-deviations).
1. Find the medium of row.
2. Find the medium of column.
3. Calculate the distance of each data point between the medium point (row and column).

## 524. Longest Word in Dictionary through Deleting (Medium)
Given a string and a string dictionary, find the longest string in the dictionary that can be formed by deleting some characters of the given string. If there are more than one possible results, return the longest word with the smallest lexicographical order. If there is no possible result, return the empty string.

Example 1:
~~~~
Input:
s = "abpcplea", d = ["ale","apple","monkey","plea"]

Output:
"apple"
~~~~

Example 2:
~~~~
Input:
s = "abpcplea", d = ["a","b","c"]

Output:
"a"
~~~~

Note:
All the strings in the input will only contain lower-case letters.
The size of the dictionary won't exceed 1,000.
The length of all the strings in the input won't exceed 1,000.

#### Solution
1. The first solution came to me is sorting the list by string length and lexicography. And then iterate the array to find the longest "matching" string.
2. The [leetcode discussion](https://leetcode.com/problems/longest-word-in-dictionary-through-deleting/#/solutions) inspired me that we don't need to sort at first. We can simply iterate through the list to check whether a word is "matching" or not. In addition, we use a variable to mark the longest word. If a current "matching" word is longest or comes before the longest, we update the longest word to the current one.
