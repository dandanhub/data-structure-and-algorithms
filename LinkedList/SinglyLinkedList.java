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
        ListNode(int x) { val = x;}
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
    
}

