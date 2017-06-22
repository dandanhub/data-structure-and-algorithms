
## 75. Sort Colors
Given an array with n objects colored red, white or blue, sort them so that objects of the same color are adjacent, with the colors in the order red, white and blue.

Here, we will use the integers 0, 1, and 2 to represent the color red, white, and blue respectively.

Note:
You are not suppose to use the library's sort function for this problem.

click to show follow up.

Follow up:
A rather straight forward solution is a two-pass algorithm using counting sort.
First, iterate the array counting number of 0's, 1's, and 2's, then overwrite array with total number of 0's, then 1's and followed by 2's.

Could you come up with an one-pass algorithm using only constant space?

#### Solution
One pass with swap.

~~~
public class Solution {
    public void sortColors(int[] nums) {
        if (nums == null || nums.length <= 1) return;
        int left = 0;
        int right = nums.length - 1;
        int i = 0;
        while (i <= right) {
            if (nums[i] == 0) {
                if (i == left) i++;
                else swap(nums, i, left); // nums[left] is 0 now
                left++;
            }
            else if (nums[i] == 2) {
                if (i == right) i++;
                else swap(nums, i, right); // nums[right] is 2 now
                right--;
            }
            else {
                i++;
            }
        }
    }

    private void swap(int[] nums, int i, int j) { // typo compile error
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
~~~

~~~
Pocket gem店面被坑
第一题是sort color的变种
这题的精髓不是应该在one-pass就解决，O(1)的复杂度么？好吧，这哥们给了我一个类(他当时还没有给出object的中的结构，我就根本不知道不可以change object中char c的值，你要是让我设计类你就直说啊)。下面的类结构是我自己写的（data纯属打酱油，char代表颜色）但这样onepass就不能实现，也根本不是count sort了，完全就只能用swap来解，楼主给出的答案, 要求sort的顺序r, g, b. more info on 1point3acres.com http://www.1point3acres.com/bbs/thread-141007-1-1.html

sort color，follow up是sort n colors.

sort color，和lc的区别是sort的是对象数组，需要自己写comparator

~~~

---

## 280. Wiggle Sort (Medium)
Given an unsorted array nums, reorder it in-place such that nums[0] <= nums[1] >= nums[2] <= nums[3]....

For example, given nums = [3, 5, 2, 1, 6, 4], one possible answer is [1, 6, 2, 5, 3, 4].

#### Solution
Refer to this [discussion](https://discuss.leetcode.com/topic/23871/java-o-n-solution), actually we don't need to sort the array. One pass iteration can do the trick.
* For odd position, if the number is smaller than its prev number, swap it with its prev number.
* For even position, if the number is bigger than its prev number, swap it with its prev number.

**如何想到O(n)的方法是关键**

~~~
public class Solution {
    public void wiggleSort(int[] nums) {
        if (nums == null || nums.length == 0) return;

        for (int i = 1; i < nums.length; i++) {
            if (i % 2 == 0) { // even pos, the num should be smaller than its prev num
                if (nums[i] > nums[i - 1]) {
                    swap(nums, i, i - 1);
                }
            }
            else { // odd pos, the num should be larger than its prev num
                if (nums[i] < nums[i - 1]) {
                    swap(nums, i, i - 1);
                }
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
~~~

## 324. Wiggle Sort II (Medium)
Given an unsorted array nums, reorder it such that nums[0] < nums[1] > nums[2] < nums[3]....

Example:
(1) Given nums = [1, 5, 1, 1, 6, 4], one possible answer is [1, 4, 1, 5, 1, 6].
(2) Given nums = [1, 3, 2, 2, 3, 1], one possible answer is [2, 3, 1, 3, 1, 2].

Note:
You may assume all input has valid answer.

Follow Up:
Can you do it in O(n) time and/or in-place with O(1) extra space?

#### Solution
Compared with 280. Wiggle Sort, this question adds one more requirement that no equals between adjacent numbers is allowed. Then, how to handle the case where input arrays have duplicated numbers?

1. The naiive solution in Wiggle Sort I won't work, consider case [1,3,1,3,2,2]. Shifting the numbers one by one cannot break the ties of same numbers.
2. Instead of sorting the array with O(nlogn) time complexity, we can find the medium in O(n) time and put the number smaller than medium into the left part and numbers bigger than medium into the right part. How to find the medium in O(n) time? It is what in 215. Kth Largest Element in an Array. We can use quick sort. Since every time we abandon half of the array, the time complexity is n + n/2 + n/4 + ... + 1 = 2n - 1 = O(n). <br>
Please refer to this [discussion](https://discuss.leetcode.com/topic/41464/step-by-step-explanation-of-index-mapping-in-java/2).



---

## 148. Sort List
Sort a linked list in O(n log n) time using constant space complexity.

#### Solution
Use merge sort, **不要忘记在分治的时候把list断开，不然会死循环** <br>
提醒自己注意在查询链表mid node的时候，考虑奇数，偶数，和只有二个节点的情况

Attemp: 2 (bug forgot to separate the original list)
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
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;

        // find the mid and tail node
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode p1 = dummy;
        ListNode p2 = dummy;
        while (p1 != null && p1.next != null) {
            p1 = p1.next;
            p1 = p1.next;
            p2 = p2.next;
        }

        // seperate the list
        ListNode right = p2.next;
        p2.next = null;

        // sort the first half of the list
        ListNode l1 = sortList(head);

        // sort the second half of the list
        ListNode l2 = sortList(right);

        // merge the two sorted lists
        return mergeList(l1, l2);
    }

    public ListNode mergeList(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        ListNode dummy = new ListNode(0);
        ListNode head = dummy;
        ListNode p1 = l1;
        ListNode p2 = l2;
        while (p1 != null && p2 != null) {
            if (p1.val <= p2.val) {
                head.next = p1;
                p1 = p1.next;
            }
            else {
                head.next = p2;
                p2 = p2.next;
            }
            head = head.next;
        }

        if (p1 != null) {
            head.next = p1;
        }

        if (p2 != null) {
            head.next = p2;
        }

        return dummy.next;
    }
}
~~~

---

## 252. Meeting Rooms (Easy)
Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei), determine if a person could attend all meetings.

For example,
Given [[0, 30],[5, 10],[15, 20]],
return false.

#### Solution
Simply sort the intervals by the starting times. Once the intervals array is sorted, we iterate through the array.
If there is any interval whose ending time is later than its next interval's starting time, return false.

Attempt: 3 (complile error)
~~~
/**
 * Definition for an interval.
 * public class Interval {
 *     int start;
 *     int end;
 *     Interval() { start = 0; end = 0; }
 *     Interval(int s, int e) { start = s; end = e; }
 * }
 */
public class Solution {
    public boolean canAttendMeetings(Interval[] intervals) {
        if (intervals == null || intervals.length <= 1) return true;
        Arrays.sort(intervals, new Comparator<Interval>() { // bug use Collections
           public int compare(Interval i1, Interval i2) {
                if (i1.start == i2.start) { // bug typo =
                    return i1.end - i2.end;
                }
                return i1.start - i2.start;
           }
        });

        for (int i = 0; i < intervals.length - 1; i++) {
            if (intervals[i].end > intervals[i + 1].start) return false;
        }

        return true;
    }
}
~~~

## 253. Meeting Rooms II (Medium) *
Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei), find the minimum number of conference rooms required.

For example,
Given [[0, 30],[5, 10],[15, 20]],
return 2.

#### Solution
1. My first thought is to build an array representing rooms. Each room is associated with an meeting interval, with starting and ending times. It is the current meeting held on the room. Initially, there is one room.
- First, sort the intervals array based on their starting times.
- Second, iterate through the intervals array. If there is room available, arrange the meeting to the room.
- If there is no room available (the meeting to be arranged conflicts with all meetings currently held on existing rooms), create a new room.
2. The [discussion](https://discuss.leetcode.com/topic/20958/ac-java-solution-using-min-heap) inspires me that I actually don't need to check the next meeting to be arranged with all rooms. We only need to check it with the room whose meeting ended (or will end) at first. Therefore, we can leverage a priority queue here.

**注意当遍历到下一个Interval没有冲突的时候，要更新pq**

Attempt: 2 (bug forgot to update queue when no conflicts)
~~~
/**
 * Definition for an interval.
 * public class Interval {
 *     int start;
 *     int end;
 *     Interval() { start = 0; end = 0; }
 *     Interval(int s, int e) { start = s; end = e; }
 * }
 */
public class Solution {
    public int minMeetingRooms(Interval[] intervals) {
        if (intervals == null || intervals.length == 0) return 0;
        Arrays.sort(intervals, new Comparator<Interval>() {
           public int compare(Interval i1, Interval i2) {
                if (i1.start == i2.start) return i1.end - i2.end;
                return i1.start - i2.start;
           }
        });

        PriorityQueue<Interval> pq = new PriorityQueue<Interval>(new Comparator<Interval>() {
            public int compare(Interval i1, Interval i2) {
                if (i1.end == i2.end) return i1.start - i2.start;
                return i1.end - i2.end;
            }
        });

        // add one room
        pq.offer(intervals[0]);

        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i].start < pq.peek().end) {
                // need to add one more room
                pq.offer(intervals[i]);
            }
            else {
                pq.poll();
                pq.offer(intervals[i]);
            }
        }

        return pq.size();
    }
}
~~~

~~~
Facebook
见到面经里有好几个提到facebook的onsite，有一个类似meeting room的问题，让求的不是需要多少room，而是给出重合最多的时间点，或者说有最多meeting的时间点？

更新pq的时候，记录时间

Airbnb
meeting room 找T1-T2内所有人空的时间段
~~~

---
