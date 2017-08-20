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
