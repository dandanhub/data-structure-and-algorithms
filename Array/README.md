# Array.java

## 243. Shortest Word Distance (Easy)
Given a list of words and two words word1 and word2, return the shortest distance between these two words in the list.

For example,
Assume that words = ["practice", "makes", "perfect", "coding", "makes"].

Given word1 = “coding”, word2 = “practice”, return 3.
Given word1 = "makes", word2 = "coding", return 1.

#### Solution
1. Solution 1: Very Naiive Soluion.
- Put indexes where word1 appears in array list 1.
- Put indexes where word2 appears in array list 2.
- Use two for loop to count minimal distance between indexes in array list 1 and indexes in array list 2.
2. Solution 2: Keep two the latest indexes where word1 and word2 appear.

## 244. Shortest Word Distance II (Medium)
This is a follow up of Shortest Word Distance. The only difference is now you are given the list of words and your method will be called repeatedly many times with different parameters. How would you optimize it?

Design a class which receives a list of words in the constructor, and implements a method that takes two words word1 and word2 and return the shortest distance between these two words in the list.

For example,
Assume that words = ["practice", "makes", "perfect", "coding", "makes"].

Given word1 = “coding”, word2 = “practice”, return 3.
Given word1 = "makes", word2 = "coding", return 1.

Note:
You may assume that word1 does not equal to word2, and word1 and word2 are both in the list.

#### Solution
Use a HashMap<String, List<Integer>> to store the indexes of each string.
When calling shortest(), get the indexes list of two strings, and calculate the shortest distance between the two lists.

## 245. Shortest Word Distance III
This is a follow up of Shortest Word Distance. The only difference is now word1 could be the same as word2.

Given a list of words and two words word1 and word2, return the shortest distance between these two words in the list.

word1 and word2 may be the same and they represent two individual words in the list.

For example,
Assume that words = ["practice", "makes", "perfect", "coding", "makes"].

Given word1 = “makes”, word2 = “coding”, return 1.
Given word1 = "makes", word2 = "makes", return 3.

Note:
You may assume word1 and word2 are both in the list.

#### Solution
When word1 equals to word2, add the code below based on 243. Shortest Word Distance (Easy):
~~~~
if (str.equals(word1) && str.equals(word2)) {
  if (pos1 <= pos2) {
    pos1 = i;
  }  else {
    pos2 = i;
  }
}
~~~~
