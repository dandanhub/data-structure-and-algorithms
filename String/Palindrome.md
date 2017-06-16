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
