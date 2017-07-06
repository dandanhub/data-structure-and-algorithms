## 206. Reverse Linked List
Reverse a singly linked list.

#### Solution
确保一遍bug free <br>
**prev初始值设置成null**

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
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode prev = null;
        ListNode curr = head;
        ListNode post = curr.next;
        while (curr != null) {
            curr.next = prev;
            prev = curr;
            curr = post;
            if (post != null) post = post.next;
        }

        return prev;
    }
}
~~~

## 138. Copy List with Random Pointer
A linked list is given such that each node contains an additional random pointer which could point to any node in the list or null.

Return a deep copy of the list.

#### Solution
1. O(n) time and O(n) space.
The naiive solution is to use a hash map which stores the mapping from random pointer
in original list to its corresponding node in copy list.
In such way, we can copy the random pointer in 2nd scan.
2. O(n) time and O(1) space.
A better solution from [this LeetCode discussion](https://discuss.leetcode.com/topic/7594/a-solution-with-constant-space-complexity-o-1-and-linear-time-complexity-o-n).
"The idea is to associate the original node with its copy node in a single linked list.
In this way, we don't need extra space to keep track of the new nodes."
a) 1st iteration: duplicate each node and insert duplicated note right behind the original node
b) 2nd iteration: copy random pointer by using cur.next.random = cur.random.next.
c) 3rd iteration: separate the list to two list: the old one and the new copy

**注意空指针的判断和处理** <br>
Attempt: 3
~~~
/**
 * Definition for singly-linked list with a random pointer.
 * class RandomListNode {
 *     int label;
 *     RandomListNode next, random;
 *     RandomListNode(int x) { this.label = x; }
 * };
 */
public class Solution {
    public RandomListNode copyRandomList(RandomListNode head) {
        if (head == null) return head;

        // duplicate the list wz copying random pointers
        RandomListNode curr = head;
        RandomListNode post = head.next;
        while (curr != null) {
            RandomListNode copied = new RandomListNode(curr.label);
            // insert the copied
            curr.next = copied;
            copied.next = post;
            curr = post;
            if (post != null) post = post.next;
        }

        // duplicate the random pointers
        curr = head;
        while (curr != null) {
            if (curr.random != null) curr.next.random = curr.random.next; // fix 1st bug
            curr = curr.next.next;
        }

        // split the list
        curr = head;
        RandomListNode newHead = head.next;
        RandomListNode newCurr = newHead;
        while (curr != null) {
            curr.next = curr.next.next;
            curr = curr.next;

            if (curr != null) {
                newCurr.next = curr.next; // fix 2nd bug
                newCurr = curr.next;
            }
        }

        return newHead;
    }
}
~~~
