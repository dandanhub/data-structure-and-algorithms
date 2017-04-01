/**
 * Basic operations on singly-linked list.
 * @author dandanshi
 * 
 */
public class SinglyLinkedList {
    
    /**
     * Definition for singly-linked list.
     */
    private class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
        }
    }
    
    // 203. Remove Linked List Elements
    public ListNode removeElements(ListNode head, int val) {
        if (head == null) {
            return head;
        }
        
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        ListNode prev = dummy;
        ListNode curr = head;
        while (curr != null) {
            if (curr.val == val) {
                prev.next = curr.next;
            }
            else {
                prev = prev.next;
            }
            curr = curr.next;
        }
        return dummy.next;
    }
    
    /*
    // 206. Reverse Linked List, iteratively
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
    
        ListNode prev = head;
        ListNode cur = head.next;
        head.next = null;
        ListNode next = null;
        while (cur != null) {
            next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        
        return prev;
    }
    */
    
    // 206. Reverse Linked List, recursively
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        
        return reverseListRecursively(head, null);
    }
    
    public ListNode reverseListRecursively(ListNode node, ListNode prev) {
        if (node == null) {
            return prev;
        }
        
        ListNode next = node.next;
        node.next = prev;
        prev = node;
        return reverseListRecursively(next, prev);
    }
    
    /*
    test case:
    []
    [1]
    [1,2]
    [1,2,3]
    [1,2,3,4]
    [1,1,1,1,1,1,1,1,1,1,1,1,2,2,3,4,5,6,7,7,7,8,8,9]
    */
    
    // 92. Reverse Linked List II
    // Reverse a linked list from position m to n. Do it in-place and in one-pass.
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if (head == null || head.next == null) {
            return head;
        }
        
        int count = 1;
        // dummy header
        ListNode left = new ListNode(-1); 
        left.next = head;
        ListNode curr = head;
        
        // get the node, left, before reversing
        while (count != m) {
            left = left.next;
            curr = curr.next;
            count++;
        }
        
        // record the beginning of reverse part
        ListNode start = curr; 
        
        // use three pointers, prev, curr and next to reverse the list from m to n
        ListNode prev = curr;
        curr = curr.next;
        prev.next = null;
        ListNode next = null;
        while (count != n) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
            count++;
        }
        
        // point the next of left to the beginning of the reversed list
        left.next = prev;
        // point the next of the end of the revered list to the remaning part of the list
        start.next = curr;
        
        // corner case when m is 1, head is also changed
        if (m == 1) {
            return left.next;
        }
        return head;
    }
    
    /*
    test case
    []
    0
    0
    [5]
    1
    1
    [1,5]
    1
    1
    [1,5]
    1
    2
    [1,2,3]
    1
    1
    [1,2,3,4,5]
    1
    5
    [1,2,3,4,5]
    2
    4
    [1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5]
    3
    10
    */
    
    /*
     * 25. Reverse Nodes in k-Group
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        // k is a positive integer 
        // and is less than or equal to the length of the linked list
        if (head == null || head.next == null || k == 1) {
            return head;
        }
        
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        // node before is used be record the end node of last K group  
        ListNode before = dummy;
        ListNode start = head;
        ListNode end = head;
        
        while (true) {
            int count = 1;
            
            // NOTE: need to add end != null
            // consider when the list len is a multiple of k
            // after the last K group, the cur node will be null
            while (count != k && end != null && end.next != null) {
                end = end.next;
                count++;
            }
            if (count != k) {
                before.next = start;
                break;
            }
            
            // the same process of reverse singly linked list
            // but we will terminate when meeting the end node
            ListNode prev = start;
            ListNode cur = start.next;
            start.next = null;
            ListNode next = null;
            while (prev != end) {
                next = cur.next;
                cur.next = prev;
                prev = cur;
                cur = next;
            }
            
            // link the end node of last K group with the start of the current K group
            before.next = prev;
            before = start;
            start = cur;
            end = start;
        }
        return dummy.next;
    }
    
    /*
    test case:
    []
    0
    [1]
    1
    [1,2,3,4,5]
    1
    [1,2,3,4,5]
    4
    [1,2,3,4,5]
    4
    [1,2,3,4,5]
    3
    [1,2,3,4,5,6,7,8,9,10,11,12]
    3
    [1,2,3,4,5,6,7,8,9,10,11,12]
    5
    */
    
    // 143. Reorder List
    public void reorderList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return;
        }
        
        // Step 1. Find the node in the middle.
        // using a fast pointer that moves 2 steps every time 
        // and a slow pointer that moves 1 step every time.
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        
        ListNode right = slow.next;
        slow.next = null;
        
        // Step 2. Reverse the right half of the list.
        ListNode prev = right;
        ListNode cur = prev.next;
        prev.next = null;
        ListNode next = null;
        while (cur != null) {
            next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        
        // Step 3. Merge the left half of the list with the reversed right half of the list.
        right = prev;
        ListNode left = head;
        ListNode leftNext = left;
        ListNode rightNext = right;
        while (right != null) {
            leftNext = left.next;
            rightNext = right.next;
            left.next = right;
            right.next = leftNext;
            left = leftNext;
            right = rightNext;
        }
    }
    
    /*
    []
    [1,2]
    [1,2,3]
    [1,2,3,4]
    [1,2,3,4,5]
    [1,2,3,4,5,6]
    [1,2,3,4,5,6,7]
    [1,2,3,4,5,6,7,8]
    [1,2,3,4,5,6,7,8,9]
    */
    /**
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode(int x) {
     *         val = x;
     *         next = null;
     *     }
     * }
     */
    
    // 160. Intersection of Two Linked Lists
    public class Solution {
        public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
            if (headA == null || headB == null) {
                return null;
            }
            
            ListNode a = headA;
            int lenA = 0;
            while (a != null) {
                a = a.next;
                lenA++;
            }
            
            ListNode b = headB;
            int lenB = 0;
            while (b != null) {
                b = b.next;
                lenB++;
            }
            
            ListNode fast = lenB > lenA ? headB : headA;  
            for (int i = 0; i < Math.abs(lenB - lenA); i++) {
                fast = fast.next;
            }
            ListNode slow = lenB > lenA ? headA : headB;
            while (fast != null && slow != null) {
                if (slow == fast) {
                    return slow;
                }
                fast = fast.next;
                slow = slow.next;
            }
            return null;
        }
    }
    
    // 160. Intersection of Two Linked Lists
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        
        ListNode a = headA;
        ListNode b = headB;
        while (a != b) {
            a = (a != null ? a.next : headB);
            b = (b != null ? b.next : headA);
        }
        return a;
    }
    
    // 328. Odd Even Linked List
    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return head;
        }

        ListNode odd = head;
        ListNode even = head.next;
        ListNode headEven = head.next;
        while (even != null && even.next != null) {
            odd.next = even.next;
            even.next = odd.next.next;
            odd.next.next = headEven;
            odd = odd.next;
            even = even.next;
        }
        
        return head;
    }
    
    // 19. Remove Nth Node From End of List
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) {
            return null;
        }
        
        ListNode p1 = head;
        ListNode p2 = head;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pre = dummy;
        
        int count = 1;
        while (count <= n && p2 != null)
        {
            p2 = p2.next;
            count++;
        }
        
        while (p2 != null)
        {
            pre = pre.next;
            p1 = p1.next;
            p2 = p2.next;
        }
        pre.next = p1.next;
        
        return dummy.next;
    }
    
    // 21. Merge Two Sorted Lists
    /**
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode(int x) { val = x; }
     * }
     */
    public class Solution {
        public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
            if (l1 == null || l2 == null) {
                return l1 == null ? l2 : l1;
            }
            
            ListNode p1 = l1;
            ListNode p2 = l2;
            ListNode dummy = new ListNode(0);
            ListNode m = dummy;
            while (p1 != null && p2 != null) {
                if (p1.val <= p2.val) {
                    m.next = p1;
                    p1 = p1.next;
                }
                else {
                    m.next = p2;
                    p2 = p2.next;
                }
                m = m.next;
            }
            
            while (p1 != null) {
                m.next = p1;
                p1 = p1.next;
                m = m.next;
            }
            
            while (p2 != null) {
                m.next = p2;
                p2 = p2.next;
                m = m.next;
            }
            
            return dummy.next;
        }
    }
    
    
}

