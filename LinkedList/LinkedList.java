/**
 * Basic operations on singly-linked list.
 * 
 */
public class LinkedList {
    
    /**
     * Definition for singly-linked list.
     * @author dandanshi
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
}

