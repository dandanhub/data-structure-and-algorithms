The java codes here basically summarize my work on Sorting algorithm. The codes are driven by CMU course 08-722 Data Structures for Application Programmers and some LeetCode problems related about Sorting.

## Arrays.sort
Java sort in Arrays, implementation note: The sorting algorithm is a Dual-Pivot Quicksort by Vladimir Yaroslavskiy, Jon Bentley, and Joshua Bloch. This algorithm offers O(n log(n)) performance on many data sets that cause other quicksorts to degrade to quadratic performance, and is typically faster than traditional (one-pivot) Quicksort implementations.

## 252. Meeting Rooms (Easy)
Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei), determine if a person could attend all meetings.

For example,
Given [[0, 30],[5, 10],[15, 20]],
return false.

#### Solution
Simply sort the intervals by the starting times. Once the intervals array is sorted, we iterate through the array.
If there is any interval whose ending time is later than its next interval's starting time, return false.

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

    It gives the following code which takes O(n^2) time.
  ~~~~
  int num = 1;
  boolean flag = false;
  for (int i = 1; i < intervals.length; i++) {
      flag = false;
      for (int j = 0; j < num; j++) {
          if (intervals[j].end <= intervals[i].start) {
              intervals[j].start = intervals[i].start;
              intervals[j].end = intervals[i].end;
              flag = true;
              break;
          }
      }
      if (!flag) {
          intervals[num].start = intervals[i].start;
          intervals[num].end = intervals[i].end;
          num++;
        }
  }
  ~~~~

2. The [discussion](https://discuss.leetcode.com/topic/20958/ac-java-solution-using-min-heap) inspires me that I actually don't need to check the next meeting to be arranged with all rooms. We only need to check it with the room whose meeting ended (or will end) at first. Therefore, we can leverage a priority queue here.

## 280. Wiggle Sort (Medium)
Given an unsorted array nums, reorder it in-place such that nums[0] <= nums[1] >= nums[2] <= nums[3]....

For example, given nums = [3, 5, 2, 1, 6, 4], one possible answer is [1, 6, 2, 5, 3, 4].

#### Solution
1. My naiive solution is to first sort the array and move the elements (right part, number bigger than medium) into the right position. The time complexity is O(n^2).

2. Refer to this [discussion](https://discuss.leetcode.com/topic/23871/java-o-n-solution), actually we don't need to sort the array. One pass iteration can do the trick.
  * For odd position, if the number is smaller than its prev number, swap it with its prev number.
  * For even position, if the number is bigger than its prev number, swap it with its prev number.

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

1. The naiive solution in Wiggle Sort I won't work, consider case [1,3,1,3,2,2]. Shifting the numbers one by one cannot break the ties of same numbers. However, we can twist it a little bit using virtual index to put number into the right position. Please refer to this [discussion](https://discuss.leetcode.com/topic/41464/step-by-step-explanation-of-index-mapping-in-java/2).

2. Instead of sorting the array with O(nlogn) time complexity, we can find the medium in O(n) time and put the number smaller than medium into the left part and numbers bigger than medium into the right part. How to find the medium in O(n) time? It is what in 215. Kth Largest Element in an Array. We can use quick sort.

  Since every time we abandon half of the array, the time complexity is n + n/2 + n/4 + ... + 1 = 2n - 1 = O(n)
