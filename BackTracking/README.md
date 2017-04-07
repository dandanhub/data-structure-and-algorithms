The java codes here basically summarize my work on backtacking algorithm. The codes are driven by CMU course 08-722 Data Structures for Application Programmers and some LeetCode problems about backtacking.

# BackTracking.java

## 93. Restore IP Addresses (Medium) *
Given a string containing only digits, restore it by returning all possible valid IP address combinations.

For example:
Given "25525511135",

return ["255.255.11.135", "255.255.111.35"]. (Order does not matter)

#### Solution
Check four IP parts one by one. Each IP part should at least have 1 digit and at most 3 digits, and it should between 0 - 255.

## 468. Validate IP Address (Medium) * (Not about backtacking, add here as a supplement for problem 93)
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

# Palindrome.java
## 131. Palindrome Partitioning
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
