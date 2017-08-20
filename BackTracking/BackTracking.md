## 78. Subsets
Given a set of distinct integers, nums, return all possible subsets.

Note: The solution set must not contain duplicate subsets.

For example,
If nums = [1,2,3], a solution is:
~~~
[
  [3],
  [1],
  [2],
  [1,2,3],
  [1,3],
  [2,3],
  [1,2],
  []
]
~~~

#### Solution

~~~
public class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        if (nums == null || nums.length == 0) return ans;
        helper(ans, new ArrayList<Integer>(), 0, nums);
        return ans;
    }

    private void helper(List<List<Integer>> ans, List<Integer> list, int start, int[] nums) {
        ans.add(new ArrayList<Integer>(list));

        for (int i = start; i < nums.length; i++) {
            list.add(nums[i]);
            helper(ans, list, i + 1, nums);
            list.remove(list.size() - 1);
        }
    }
}
~~~

## 90. Subsets II
Given a collection of integers that might contain duplicates, nums, return all possible subsets.

Note: The solution set must not contain duplicate subsets.

For example,
If nums = [1,2,2], a solution is:
~~~
[
  [2],
  [1],
  [1,2,2],
  [2,2],
  [1,2],
  []
]
~~~

#### Solution
1. 先排序
2. 对于有序数组处理可能的重复的情况

~~~
public class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        if (nums == null || nums.length == 0) return ans;
        Arrays.sort(nums);
        helper(ans, new ArrayList<Integer>(), 0, nums);
        return ans;
    }

    private void helper(List<List<Integer>> ans, List<Integer> list, int start, int[] nums) {
        ans.add(new ArrayList<Integer>(list));
        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i - 1]) continue;
            list.add(nums[i]);
            helper(ans, list, i + 1, nums);
            list.remove(list.size() - 1);
        }
    }
}
~~~

## 93. Restore IP Addresses (Medium) *
Given a string containing only digits, restore it by returning all possible valid IP address combinations.

For example:
Given "25525511135",

return ["255.255.11.135", "255.255.111.35"]. (Order does not matter)

#### Solution
Check four IP parts one by one. Each IP part should at least have 1 digit and at most 3 digits, and it should between 0 - 255.

~~~
public class Solution {
    public List<String> restoreIpAddresses(String s) {
        List<String> list = new ArrayList<String>();
        if (s == null || s.length() < 4) return list;
        helper(list, 0, "", s, 0);
        return list;
    }

    private void helper(List<String> list, int count, String str, String s, int start) {
        // base case
        if (start == s.length() && count == 4) {
            list.add(str);
            return;
        }
        else if (count >= 4 || start == s.length()) {
            return;
        }

        for (int i = 1; i <= 3 && start + i <= s.length(); i++) {
            if (Integer.parseInt(s.substring(start, start + i)) <= 255) {
                String newStr = str + s.substring(start, start + i);
                if (count < 3) newStr = newStr + ".";
                helper(list, count + 1, newStr, s, start + i);
            }

            // 0 is the starting char
            if (s.charAt(start) == '0') return;
        }
    }
}

/*
test cases
"123"
"000"
"0000"
"25525511135"
"51232342"
"19216810062"
"10001"
"0279245587303"
*/
~~~

## 468. Validate IP Address (Medium) * (Not about backtracking, add here as a supplement for problem 93)
Write a function to check whether an input string is a valid IPv4 address or IPv6 address or neither.

IPv4 addresses are canonically represented in dot-decimal notation, which consists of four decimal numbers, each ranging from 0 to 255, separated by dots ("."), e.g.,172.16.254.1;

Besides, leading zeros in the IPv4 is invalid. For example, the address 172.16.254.01 is invalid.

IPv6 addresses are represented as eight groups of four hexadecimal digits, each group representing 16 bits. The groups are separated by colons (":"). For example, the address 2001:0db8:85a3:0000:0000:8a2e:0370:7334 is a valid one. Also, we could omit some leading zeros among four hexadecimal digits and some low-case characters in the address to upper-case ones, so 2001:db8:85a3:0:0:8A2E:0370:7334 is also a valid IPv6 address(Omit leading zeros and using upper cases).

However, we don't replace a consecutive group of zero value with a single empty group using two consecutive colons (::) to pursue simplicity. For example, 2001:0db8:85a3::8A2E:0370:7334 is an invalid IPv6 address.

Besides, extra leading zeros in the IPv6 is also invalid. For example, the address 02001:0db8:85a3:0000:0000:8a2e:0370:7334 is invalid.

Note: You may assume there is no extra space or special characters in the input string.

Example 1:
~~~~
Input: "172.16.254.1"
Output: "IPv4"
Explanation: This is a valid IPv4 address, return "IPv4".
~~~~

Example 2:
~~~~
Input: "2001:0db8:85a3:0:0:8A2E:0370:7334"
Output: "IPv6"
Explanation: This is a valid IPv6 address, return "IPv6".
~~~~

Example 3:
~~~~
Input: "256.256.256.256"
Output: "Neither"
Explanation: This is neither a IPv4 address nor a IPv6 address.
~~~~

#### Solution
1. I checked IPv4 and IPv6 separately. If a string contains “.”, then check it with IPv4 function. If a string contains ":", check it with IPv6 function.
2. For IPv4, I split string by ".", and then validate four number parts one by one.
3. For IPv6, I split string by ":", and then validate eight number parts one by one.
I failed the test case: "2001:0db8:85a3:0:0:8A2E:0370:7334:". The issue here is by using `String[] tokens = IP.split(":");`, it will give 8 tokens as result. So we need to consider the corner case where the first and last character is ':'.

~~~
// 468. Validate IP Address
// Check whether a given string is a valid IP address
public String validIPAddress(String IP) {
      if (IP == null || IP.length() == 0) {
          return "Neither";
      }

      if (IP.contains(".") && isValidIPv4(IP)) {
          return "IPv4";
      }
      else if (IP.contains(":") && isValidIPv6(IP)){
          return "IPv6";
      }

      return "Neither";
  }

  // Check whether a given string is a valid IPv4 address
  private boolean isValidIPv4(String IP) {
      if (IP.charAt(0) == '.' || IP.charAt(IP.length() - 1) == '.') {
          return false;
      }
      String[] tokens = IP.split("\\.");
      if (tokens.length != 4) {
          return false;
      }
      for (int i = 0; i < tokens.length; i++) {
          String str = tokens[i];
          if (str.length() == 0 || str.length() > 3 || (str.length() > 1 && str.charAt(0) == '0')
                  || !str.matches("[0-9]+") || Integer.parseInt(str) > 255) {
                      return false;
                  }
      }
      return true;
  }

  // Check whether a string is a valid IPv6 address
  private boolean isValidIPv6(String IP) {
      if (IP.charAt(0) == ':' || IP.charAt(IP.length() - 1) == ':') {
          return false;
      }
      String[] tokens = IP.split(":");
      if (tokens.length != 8) {
          return false;
      }
      for (int i = 0; i < tokens.length; i++) {
          String str = tokens[i].toLowerCase();
          if (str.length() == 0 || str.length() > 4 || !str.matches("[0-9a-f]+")) {
              return false;
          }
      }
      return true;
  }

  /*
  test cases
  "2001:0db8:85a3:0:0:8A2E:0370:7334:"
  ",2001:0db8:85a3:0:0:8A2E:0370:7334:"
  ":2001:0db8:85a3:0:0:8A2E:0370:7334"
  "0.0.0"
  "172.16.254.1.1"
  "172.16.254.1."
  ".172.16.254.1"
  "172.16.254.1:1"
  "172.16.254.1"
  "172.16.254.01"
  "172.abc.1e4.01"
  "172.abc.1e4.01"
  "2001:0db8:85a3::8A2E:0370:7334"
  "2001:0db8:85a3:0000:0000:8a2e:0370:7334"
  "2001:db8:85a3:0:0:8A2E:0370:7334"
  "2001:db8:85a3:0:0:8A2E4:0:7334"
  "02001:0db8:85a3:0000:0000:8a2e:0370:7334"
  "20EE:Fb8:85a3:0:0:8A2E:0370:7334"
  */
~~~

---

## 254. Factor Combinations (Medium) *
Numbers can be regarded as product of its factors. For example,

8 = 2 x 2 x 2;
  = 2 x 4.
Write a function that takes an integer n and return all possible combinations of its factors.

Note:
1. You may assume that n is always positive.
2. Factors should be greater than 1 and less than n.

#### Solution
The thing is how to avoid duplications.
For example, 32, we will consider [2,2,2,4] and [4,2,2,2] as duplicates.
LeetCode deleted the description:
"Each combination's factors must be sorted ascending, for example: The factors of 2 and 6 is [2, 6], not [6, 2]."

Set a lower bound in backtacking.

## 291. Word Pattern II
Given a pattern and a string str, find if str follows the same pattern.

Here follow means a full match, such that there is a bijection between a letter in pattern and a non-empty substring in str.

Examples:
pattern = "abab", str = "redblueredblue" should return true.
pattern = "aaaa", str = "asdasdasdasd" should return true.
pattern = "aabb", str = "xyzabcxzyabc" should return false.

#### Solution
A naiive backtracking solution. For example,
pattern "abab"
str "redblueredblue"

1. put('a',"r") into map, and move forward to match pattern "bab" with "edblueredblue"
2. put('b',"e") into map, and move forward to match pattern "ab" with "dblueredblue"
3. when checking pattern 'a' with 'd', it does not match because we have already map pattern 'a' with "r"
3. try checking pattern 'a' with 'db', it does not match
4. try checking pattern 'a' with "dbl", it does not match
5. ...
6. no match found, back to step 2, we put('b',"ed") into the map
7. repeat 3 to 5
8. no match found, back to step 2, until put('b',"edblue") into the map, move forward to match pattern "ab" with "redblue"
9. bingo

~~~
// 291. Word Pattern II
public boolean wordPatternMatch(String pattern, String str) {
    if (pattern == null) {
        return str == null;
    }
    if (pattern.length() == 0) {
        return str.length() == 0;
    }
    Map<Character, String> map = new HashMap<Character, String>();
    return backtrackingPatternMatch(pattern, 0, str, map);
}

private boolean backtrackingPatternMatch(String pattern, int pos, String str, Map<Character, String> map) {
    if (pos == pattern.length()) {
        if (str.length() == 0) {
            return true;
        }
        else {
            return false;
        }
    }
    char ch = pattern.charAt(pos);
    for (int j = 1; j <= str.length(); j++) {
        String s = str.substring(0, j);
        if (!map.containsKey(ch) && !map.containsValue(s)) {
            map.put(ch, s);
            if (backtrackingPatternMatch(pattern, pos + 1, str.substring(j), map)) {
                return true;
            }
            else {
                map.remove(ch);
            }
        }
        else if (map.containsKey(ch) && s.equals(map.get(ch))) {
            if (backtrackingPatternMatch(pattern, pos + 1, str.substring(j), map)) {
                return true;
            }
        }
    }
    return false;
}

~~~

## 294. Flip Game II
You are playing the following Flip Game with your friend: Given a string that contains only these two characters: + and -, you and your friend take turns to flip two consecutive "++" into "--". The game ends when a person can no longer make a move and therefore the other person will be the winner.

Write a function to determine if the starting player can guarantee a win.

For example, given s = "++++", return true. The starting player can guarantee a win by flipping the middle "++" to become "+--+".

Follow up:
Derive your algorithm's runtime complexity.

#### Solution
Iteratively call function by passing new string.

~~~
// 294. Flip Game II
public boolean canWin(String s) {
    if (s == null || s.length() < 0) {
        return false;
    }
    return backtrackingWin(s);
}

private boolean backtrackingWin(String s) {
    for (int i = 0; i < s.length() - 1; i++) {
        if (s.substring(i, i + 2).equals("++")) {
            StringBuilder sb = new StringBuilder();
            sb.append(s.substring(0, i))
              .append("--")
              .append(s.substring(i + 2));
            if (!backtrackingWin(sb.toString())) {
                return true;
            }
        }
    }
    return false;
}
~~~

## 526. Beautiful Arrangement
Suppose you have N integers from 1 to N. We define a beautiful arrangement as an array that is constructed by these N numbers successfully if one of the following is true for the ith position (1 ? i ? N) in this array:

The number at the ith position is divisible by i.
i is divisible by the number at the ith position.
Now given N, how many beautiful arrangements can you construct?

Example 1:
~~~
Input: 2
Output: 2
Explanation:

The first beautiful arrangement is [1, 2]:

Number at the 1st position (i=1) is 1, and 1 is divisible by i (i=1).

Number at the 2nd position (i=2) is 2, and 2 is divisible by i (i=2).

The second beautiful arrangement is [2, 1]:

Number at the 1st position (i=1) is 2, and 2 is divisible by i (i=1).

Number at the 2nd position (i=2) is 1, and i (i=2) is divisible by 1.
~~~

Note:
N is a positive integer and will not exceed 15.

#### Solution
Backtrack
~~~
class Solution {
    private int count = 0;

    public int countArrangement(int N) {
        if (N < 1) return 0;
        if (N == 1) return 1;

        boolean[] used = new boolean[N + 1];
        helper(used, 1);

        return count;
    }

    private void helper(boolean[] used, int index) {
        if (index == used.length) {
            count++;    
        }

        for (int i = 1; i < used.length; i++) {
            if (!used[i] && (index % i == 0 || i % index == 0)) {
                used[i] = true;
                helper(used, index + 1);
                used[i] = false;
            }
        }
    }
}
~~~
