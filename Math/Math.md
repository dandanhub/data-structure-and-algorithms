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
