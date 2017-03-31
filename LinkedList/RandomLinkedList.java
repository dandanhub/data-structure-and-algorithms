import java.util.HashMap;

/**
 * A linked list is given such that each node 
 * contains an additional random pointer which could point to any 
 * @author dandanshi
 */
public class RandomLinkedList {
    /**
     * Definition for singly-linked list with a random pointer.
     */
    private class RandomListNode {
        int label;
        RandomListNode next, random;
        RandomListNode(int x) { this.label = x; }
    }
    
    /*
     * 138. Copy List with Random Pointer
     * Return a deep copy of the list.
     * Naiive solution with O(n) time and O(n) space.
     */
    /*
    public RandomListNode copyRandomList(RandomListNode head) {
        if (head == null) {
            return head;
        }
        
        HashMap<RandomListNode, RandomListNode> map = new HashMap<RandomListNode, RandomListNode>();
        RandomListNode curr = head;
        RandomListNode dummy = new RandomListNode(0);
        RandomListNode newCurr = dummy;
         
        while (curr != null) {
            RandomListNode newNode = new RandomListNode(curr.label);
            newCurr.next = newNode;
            map.put(curr, newNode);
            curr = curr.next;
            newCurr = newCurr.next;
        }
        
        curr = head;
        newCurr = dummy.next;
        while (curr != null) {
            RandomListNode randomNode = curr.random;
            if (randomNode != null) {
                newCurr.random = map.get(randomNode);
            }
            curr = curr.next;
            newCurr = newCurr.next;
        }
        return dummy.next;
    }
    */
    
    /*
     * 138. Copy List with Random Pointer
     * Return a deep copy of the list.
     * Improved solution with O(n) time and O(1) space.
     */
    public RandomListNode copyRandomList(RandomListNode head) {
        if (head == null) {
            return head;
        }
        
        RandomListNode curr = head;
        while (curr != null) {
            RandomListNode node = new RandomListNode(curr.label);
            RandomListNode tmp = curr.next;
            curr.next = node;
            node.next = tmp;
            curr = tmp;
        }
        
        curr = head;
        while (curr != null) {
            if (curr.random != null) {
                curr.next.random = curr.random.next;
            }
            curr = curr.next.next;
        }
        
        curr = head;
        RandomListNode newHead = head.next;
        RandomListNode newCurr = newHead;
        while (curr != null) {
            curr.next = newCurr.next;
            curr = curr.next;
            if (curr != null) {
                newCurr.next = curr.next;
                newCurr = newCurr.next;
            }
        }
        
        return newHead;
    }
    
}
