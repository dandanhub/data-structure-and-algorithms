## 2. Add Two Numbers (Easy)
You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.

You may assume the two numbers do not contain any leading zero, except the number 0 itself.

Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
Output: 7 -> 0 -> 8

#### Solution
注意最后要处理carry

Attempt: 1
~~~
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        ListNode p1 = l1;
        ListNode p2 = l2;
        ListNode head = new ListNode(0);
        ListNode p = head;
        int carry = 0;
        while (p1 != null || p2 != null) {
            int sum = carry;
            if (p1 != null) {
                sum += p1.val;
                p1 = p1.next;
            }
            if (p2 != null) {
                sum += p2.val;
                p2 = p2.next;
            }

            carry = sum / 10;
            ListNode newNode = new ListNode(sum % 10);
            p.next = newNode;
            p = p.next;
        }

        if (carry != 0) {
            ListNode newNode = new ListNode(carry);
            p.next = newNode;
        }

        return head.next;
    }
}
~~~

## 396. Rotate Function
Given an array of integers A and let n to be its length.

Assume Bk to be an array obtained by rotating the array A k positions clock-wise, we define a "rotation function" F on A as follow:

F(k) = 0 * Bk[0] + 1 * Bk[1] + ... + (n-1) * Bk[n-1].

Calculate the maximum value of F(0), F(1), ..., F(n-1).

Note:
n is guaranteed to be less than 105.

Example:
~~~
A = [4, 3, 2, 6]

F(0) = (0 * 4) + (1 * 3) + (2 * 2) + (3 * 6) = 0 + 3 + 4 + 18 = 25
F(1) = (0 * 6) + (1 * 4) + (2 * 3) + (3 * 2) = 0 + 4 + 6 + 6 = 16
F(2) = (0 * 2) + (1 * 6) + (2 * 4) + (3 * 3) = 0 + 6 + 8 + 9 = 23
F(3) = (0 * 3) + (1 * 2) + (2 * 6) + (3 * 4) = 0 + 2 + 12 + 12 = 26

So the maximum value of F(0), F(1), F(2), F(3) is F(3) = 26.
~~~

#### Solution
O(n) time
~~~
class Solution {
    public int maxRotateFunction(int[] A) {
        if (A == null || A.length == 0) return 0;

        int sum = 0;
        int F = 0;
        for (int i = 0; i < A.length; i++) {
            sum += A[i];
            F += i * A[i];
        }

        int ans = F;
        int len = A.length;
        for (int i = 1; i < len; i++) {
            F = F + sum - len * A[len - i];
            ans = Math.max(ans, F);
        }
        return ans;
    }
}
~~~

## 258. Add Digits
Given a non-negative integer num, repeatedly add all its digits until the result has only one digit.

For example:

Given num = 38, the process is like: 3 + 8 = 11, 1 + 1 = 2. Since 2 has only one digit, return it.

Follow up:
Could you do it without any loop/recursion in O(1) runtime?

#### Solution
Follow up in O(1) time
~~~
class Solution {
    public int addDigits(int num) {
        return 1 + (num - 1) % 9;
    }
}
~~~

## 172. Factorial Trailing Zeroes (+++)
Given an integer n, return the number of trailing zeroes in n!.

Note: Your solution should be in logarithmic time complexity.

#### Solution
return n/5 + n/25 + n/125 + n/625 + n/3125+...;
~~~
class Solution {
    public int trailingZeroes(int n) {
        return n == 0 ? 0 : n / 5 + trailingZeroes(n / 5);
    }
}
~~~

Iterative
~~~
class Solution {
    public int trailingZeroes(int n) {
        int count = 0;
        while (n > 0) {
            count += n / 5;
            n /= 5;
        }
        return count;
    }
}
~~~

## 202. Happy Number
Write an algorithm to determine if a number is "happy".

A happy number is a number defined by the following process: Starting with any positive integer, replace the number by the sum of the squares of its digits, and repeat the process until the number equals 1 (where it will stay), or it loops endlessly in a cycle which does not include 1. Those numbers for which this process ends in 1 are happy numbers.

Example: 19 is a happy number

12 + 92 = 82
82 + 22 = 68
62 + 82 = 100
12 + 02 + 02 = 1

#### Solution
HashSet + Math
~~~
class Solution {
    public boolean isHappy(int n) {
        if (n <= 0) return false;
        Set<Integer> set = new HashSet<Integer>();
        set.add(n);
        while (true) {
            n = helper(n);
            if (n == 1) return true;
            if (set.contains(n)) return false;
            set.add(n);
        }
    }

    private int helper(int n) {
        int num = 0;
        while (n != 0) {
            num += (n % 10) * (n % 10);
            n /= 10;
        }
        return num;
    }
}
~~~

## 7. Reverse Integer
Reverse digits of an integer.

Example1: x = 123, return 321
Example2: x = -123, return -321

click to show spoilers.

Have you thought about this?
Here are some good questions to ask before coding. Bonus points for you if you have already thought through this!

If the integer's last digit is 0, what should the output be? ie, cases such as 10, 100.

Did you notice that the reversed integer might overflow? Assume the input is a 32-bit integer, then the reverse of 1000000003 overflows. How should you handle such cases?

For the purpose of this problem, assume that your function returns 0 when the reversed integer overflows.

#### Solution
Method 1: Use a int array to store digits, actually no need
~~~
class Solution {
    public int reverse(int x) {
        if ((x >= 0 && x < 10) || (x < 0 && x > -10)) return x;

        int sign = 1;
        long num = x;
        if (x < 0) {
            sign = -1;
            num = -x;
        }

        int N = 10;
        int[] digits = new int[N];
        int index = 0;
        while (num > 0) {
            digits[index++] = (int) num % 10;
            num /= 10;
        }

        long res = 0;
        for (int i = 0; i < index; i++) {
            res = 10 * res + digits[i];
        }
        res = sign * res;

        if ((res > 0 && res > (long) Integer.MAX_VALUE)
            || (res < 0 && res < (long) Integer.MIN_VALUE))  {
            return 0;
        }

        return (int) res;
    }
}
~~~

Method 2: Without using array
~~~
class Solution {
    public int reverse(int x) {
        if ((x >= 0 && x < 10) || (x < 0 && x > -10)) return x;

        int sign = 1;
        long num = x;
        if (x < 0) {
            sign = -1;
            num = -x;
        }

        long res = 0;
        while (num > 0) {
            res = 10 * res + num % 10;
            num /= 10;
        }
        res = sign * res;

        if ((res > 0 && res > (long) Integer.MAX_VALUE)
            || (res < 0 && res < (long) Integer.MIN_VALUE))  {
            return 0;
        }

        return (int) res;
    }
}
~~~
