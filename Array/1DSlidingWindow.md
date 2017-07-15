## 346. Moving Average from Data Stream
Given a stream of integers and a window size, calculate the moving average of all integers in the sliding window.

For example,
~~~
MovingAverage m = new MovingAverage(3);
m.next(1) = 1
m.next(10) = (1 + 10) / 2
m.next(3) = (1 + 10 + 3) / 3
m.next(5) = (10 + 3 + 5) / 3
~~~

#### Solution
Method 1: 用Deque来保存当前window的元素, 185ms
~~~
public class MovingAverage {
    Deque<Integer> deque;
    long sum;
    int size;

    /** Initialize your data structure here. */
    public MovingAverage(int size) {
        deque = new ArrayDeque<Integer>();
        sum = 0;
        this.size = size;
    }

    public double next(int val) {
        if (deque.size() >= size) {
            int prev = deque.pollFirst();
            sum -= prev;
        }
        deque.offerLast(val);
        sum += val;
        return (double) sum / (double) deque.size();
    }
}

/**
 * Your MovingAverage object will be instantiated and called as such:
 * MovingAverage obj = new MovingAverage(size);
 * double param_1 = obj.next(val);
 */
~~~

Method 2: 这个[discussion](https://discuss.leetcode.com/topic/44108/java-o-1-time-solution)启发，不用deque，用数组，但是维护一个index是要被replaced的元素

~~~
那么1T的数据假设内存放不下的话，是否有什么办法呢？那只能求助于放在硬盘上了，比如放在数据库里。假如说数据不全在内存里，我怎么可以知道最近的1T的数据的average是多少呢？我需要去load最近的1T的数据么？答案是我们记录在硬盘上的值，可以用 PrefixSum 的形式。就是前缀和。那么当你需要计算某一段的和的时候，只需要用当前的前缀和前去之前某个时刻的前缀和就可以了。这样对于硬盘来说，只是读写1次。
~~~

## 239. Sliding Window Maximum
Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position.

For example,
Given nums = [1,3,-1,-3,5,3,6,7], and k = 3.
~~~
Window position                Max
---------------               -----
[1  3  -1] -3  5  3  6  7       3
 1 [3  -1  -3] 5  3  6  7       3
 1  3 [-1  -3  5] 3  6  7       5
 1  3  -1 [-3  5  3] 6  7       5
 1  3  -1  -3 [5  3  6] 7       6
 1  3  -1  -3  5 [3  6  7]      7
 ~~~
Therefore, return the max sliding window as [3,3,5,5,6,7].

Note:
You may assume k is always valid, ie: 1 ≤ k ≤ input array's size for non-empty array.

Follow up:
Could you solve it in linear time?

#### Solution
最基本的可以想到O(nk)的解法，难点是在O(n)的时间内解决问题。
在k-sized的window中，如果下一个数比前面所有的数都大，那么可以放心把前k-1个数替换掉，因为确定下一个数是(i-k, i)内最大的数；而如果下一个数比前面的数小，我们需要保留这个数，因为可能它会是后面某个window的最大值。
1. **用deque记录保存的数组的index**
~~~
public class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 1) return nums;

        int[] ans = new int[nums.length - k + 1];
        int index = 0;
        Deque<Integer> deque = new ArrayDeque<Integer>();
        for (int i = 0; i < nums.length; i++) {
            // pop out all out of boudary number
            while (!deque.isEmpty() && deque.peek() < i - k + 1) {
                deque.poll();
            }

            // pop up smaller nums
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }

            deque.offer(i);

            if (i >= k - 1) ans[index++] = nums[deque.peekFirst()];
        }
        return ans;
    }
}
~~~

Method 2: 如果不用deque，如何解决？参考[discussion](https://discuss.leetcode.com/topic/26480/o-n-solution-in-java-with-two-simple-pass-in-the-array/7)
"With some simple comparison, you can see this solution is somewhat equivalent to the deque solution.
Because of the property of sliding window, you can find all these "anchor" points (advocated in OP's example), and then each sliding window can be divided into two parts. The idea is to find the maximum of the left part and the maximum of the right part, and then obtain the maxima by comparing the two maximums. The keen observation is that all the left maximums can be found by a right-to-left transverse, while all the right maximums can be found by a left-to-right transverse."
