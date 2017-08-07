## 141. Linked List Cycle

Given a linked list, determine if it has a cycle in it.

Follow up:
Can you solve it without using extra space?

#### Solution
1. Method 1: Hash table to mark visited ListNode
2. Method 2: Two pointers, slow and fast

Attempt: 1
~~~
/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public boolean hasCycle(ListNode head) {
        if (head == null) return false;
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null) {
            fast = fast.next;
            if (fast != null) fast = fast.next;
            slow = slow.next;

            if (fast != null && fast == slow) return true;
        }

        return false;
    }
}
~~~

## 142. Linked List Cycle II (+++)

Given a linked list, return the node where the cycle begins. If there is no cycle, return null.

Note: Do not modify the linked list.

Follow up:
Can you solve it without using extra space?

#### Solution

Attempt: 2
~~~
/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public ListNode detectCycle(ListNode head) {
        if (head == null) return null;

        ListNode slow = head;
        ListNode fast = head;

        while (fast != null) {
            fast = fast.next;
            if (fast != null) fast = fast.next;
            slow = slow.next;

            if (fast != null && slow == fast) break;
        }

        if (fast == null) return null;

        ListNode start = head;
        while (start != slow) {
            slow = slow.next;
            start = start.next;
        }

        return start;
    }
}
~~~
