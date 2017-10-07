/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
   public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        Queue<ListNode> pq = new PriorityQueue<ListNode>(new Comparator<ListNode>() {
            public int compare(ListNode node1, ListNode node2) {
              return node1.val - node2.val;
            }
        });

        for (int i = 0; i < lists.length; i++) {
            if (lists[i] != null) {
                pq.add(lists[i]);
            }
        }

        ListNode newHead = new ListNode(0);
        ListNode pointer = newHead;
        while (!pq.isEmpty()) {
            ListNode cur = pq.poll();
            pointer.next = cur;
            pointer = pointer.next;
            if (cur.next != null) {
                pq.add(cur.next);
            }
        }

        return newHead.next;
    }
}
