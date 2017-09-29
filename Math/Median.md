## 295. Find Median from Data Stream
Median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value. So the median is the mean of the two middle value.

Examples:
[2,3,4] , the median is 3

[2,3], the median is (2 + 3) / 2 = 2.5

Design a data structure that supports the following two operations:

void addNum(int num) - Add a integer number from the data stream to the data structure.
double findMedian() - Return the median of all elements so far.
For example:
~~~
addNum(1)
addNum(2)
findMedian() -> 1.5
addNum(3)
findMedian() -> 2
~~~

#### Solution
就是用两个heap，一个最大堆，一个最小堆，来maintain相对顺序。<br>
下面这段代码是如果odd number of ints, minHeap holds one more int than maxHeap
~~~
class MedianFinder {
    PriorityQueue<Integer> minHeap;
    PriorityQueue<Integer> maxHeap;

    /** initialize your data structure here. */
    public MedianFinder() {
        minHeap = new PriorityQueue<Integer>();
        maxHeap = new PriorityQueue<Integer>(Collections.reverseOrder());
    }

    public void addNum(int num) {
        if (minHeap.isEmpty() || num >= minHeap.peek()) {
            minHeap.offer(num);
        }
        else {
            maxHeap.offer(num);
        }

        if (minHeap.size() - maxHeap.size() > 1) {
            maxHeap.offer(minHeap.poll());
        }
        else if (maxHeap.size() > minHeap.size()) {
            minHeap.offer(maxHeap.poll());
        }
    }

    public double findMedian() {
        int size = minHeap.size() + maxHeap.size();
        if (size % 2 == 1) {
            return minHeap.peek();
        }
        else {
            return (minHeap.peek() + maxHeap.peek()) / 2.0;
        }
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
~~~

## 480. Sliding Window Median
Median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value. So the median is the mean of the two middle value.

Examples:
[2,3,4] , the median is 3

[2,3], the median is (2 + 3) / 2 = 2.5

Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position. Your job is to output the median array for each window in the original array.

For example,
Given nums = [1,3,-1,-3,5,3,6,7], and k = 3.
~~~
Window position                Median
---------------               -----
[1  3  -1] -3  5  3  6  7       1
 1 [3  -1  -3] 5  3  6  7       -1
 1  3 [-1  -3  5] 3  6  7       -1
 1  3  -1 [-3  5  3] 6  7       3
 1  3  -1  -3 [5  3  6] 7       5
 1  3  -1  -3  5 [3  6  7]      6
~~~
Therefore, return the median sliding window as [1,-1,-1,3,5,6].

Note:
You may assume k is always valid, ie: k is always smaller than input array's size for non-empty array.

#### Solution
PriorityQueue remove任意元素O(n)时间，所以这题如果使用PQ的话无法做到优化
~~~
In java, there are two remove functions.
- remove() -> This is to remove the head/root, it takes O(logN) time.
- remove(Object o) -> This is to remove an arbitrary object. Finding this object takes O(N) time, and removing it takes O(logN) time.
~~~

使用TreeMap O(lgn) remove
