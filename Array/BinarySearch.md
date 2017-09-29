# Binary Search in 1D Array
## 287. Find the Duplicate Number
Given an array nums containing n + 1 integers where each integer is between 1 and n (inclusive), prove that at least one duplicate number must exist. Assume that there is only one duplicate number, find the duplicate one.

Note:
1. You must not modify the array (assume the array is read only).
2. You must use only constant, O(1) extra space.
3. Your runtime complexity should be less than O(n2).
4. There is only one duplicate number in the array, but it could be repeated more than once.

#### Solution
题目限定只有一个重复的数字让问题变得简单

Attempt: 2 (bug when counting)
~~~
public class Solution {
    public int findDuplicate(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int l = 1;
        int r = nums.length - 1;
        while (l < r) {
            int m = (r - l) / 2 + l;
            int count = 0;
            for (int i = 0; i < nums.length; i++) {
                // if (nums[i] <= m) count++; // bug
                if (nums[i] >= l && nums[i] <= m) count++;
            }
            if (count > m - l + 1) {
                r = m;
            }
            else {
                l = m + 1;
            }
        }
        return l;
    }
}
~~~

## 4. Median of Two Sorted Arrays (Hard) *
There are two sorted arrays nums1 and nums2 of size m and n respectively.

Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).

Example 1:
~~~
nums1 = [1, 3]
nums2 = [2]

The median is 2.0
~~~

Example 2:
~~~
nums1 = [1, 2]
nums2 = [3, 4]

The median is (2 + 3)/2 = 2.5
~~~

#### Solution
Convert the problem to find the kth element.
When we apply binary search, what is the base case?
How to handle different cases when the array length is odd and even?

*The solution will get TLE in new leetcode test*

~~~
public class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int len = m + n;

        if ((len & 1) == 1) { // odd
            return findKthNumber(nums1, 0, nums2, 0, len / 2 + 1);
        }

        return (findKthNumber(nums1, 0, nums2, 0, len / 2) + findKthNumber(nums1, 0, nums2, 0, len / 2 + 1)) / 2.0;
    }

    private int findKthNumber(int[] nums1, int s1, int[] nums2, int s2, int k) {
        if (s1 >= nums1.length) {
            return nums2[s2 + k - 1];
        }

        if (s2 >= nums2.length) {
            return nums1[s1 + k - 1];
        }

        if (k == 1) return Math.min(nums1[s1], nums2[s2]);

        int key1 = s1 + k / 2 - 1 >= nums1.length ? Integer.MAX_VALUE : nums1[s1 + k / 2 - 1];
        int key2 = s2 + k / 2 - 1 >= nums2.length ? Integer.MAX_VALUE : nums2[s2 + k / 2 - 1];

        if (key1 > key2) {
            return findKthNumber(nums1, s1, nums2, s2 + k / 2, k - k / 2);
        }

        return findKthNumber(nums1, s1 + k / 2, nums2, s2, k - k / 2);
    }
}
~~~

Iterative
~~~
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int len = m + n;

        if (len % 2 == 1) {
            return helper(nums1, nums2, len / 2 + 1);          
        }
        else {
            return (helper(nums1, nums2, len / 2) + helper(nums1, nums2, len / 2 + 1)) / 2.0;
        }
    }

    private int helper(int[] nums1, int[] nums2, int k) {
        int s1 = 0;
        int s2 = 0;
        while (k > 1) {
            int mid1 = s1 + k / 2 - 1 >= nums1.length ? Integer.MAX_VALUE : nums1[s1 + k / 2 - 1];
            int mid2 = s2 + k / 2 - 1 >= nums2.length ? Integer.MAX_VALUE : nums2[s2 + k / 2 - 1];

            if (mid1 >= mid2) {
                s2 += k / 2;
                k -= k / 2;
            }
            else {
                s1 += k / 2;
                k -= k / 2;
            }
        }

        if (s1 >= nums1.length) return nums2[s2];
        if (s2 >= nums2.length) return nums1[s1];
        return nums1[s1] <= nums2[s2] ? nums1[s1] : nums2[s2];
    }
}
~~~

## 153. Find Minimum in Rotated Sorted Array (Medium)
Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

(i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).

Find the minimum element.

You may assume no duplicate exists in the array.

#### Solution
Compare the num in middle with the num in right.

**二个要注意的点**
1. **为什么是和右边比较呢？**
**我们来想一下如果和左边比较的话，遇到这种情况的时候无法区分0,1,2和1,2,0**
2. **这题注明了数组不包含重复，如果包含重复见题目154**

Time Complexity: O(logn)
Space Complexity: O(1)

Attempts: 3
~~~
public class Solution {
    public int findMin(int[] nums) {
        // edge cases
        if (nums == null || nums.length == 0) return 0;

        int l = 0;
        int r = nums.length - 1;
        while (l < r) {
            int m = (r - l) / 2 + l;
            if (nums[m] < nums[r]) {
                r = m;
            }
            else if (nums[m] > nums[r]){
                l = m + 1;
            }
        }

        return nums[l];
    }
}
~~~

## 154. Find Minimum in Rotated Sorted Array II (Medium)
Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

(i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).

Find the minimum element.

The array may contain duplicates.

#### Solution
A follow up problem where the array may contains duplicates.

Attempts: 1
~~~
public class Solution {
    public int findMin(int[] nums) {
        // edge cases
        if (nums == null || nums.length == 0) return 0;

        int l = 0;
        int r = nums.length - 1;
        while (l < r) {
            int m = (r - l) / 2 + l; // get the mid
            if (nums[m] < nums[r]) {
                r = m;
            }
            else if (nums[m] > nums[r]) {
                l = m + 1;
            }
            else {
                // cannot determine which side the min number is, e.g. [0, 1, 1], [1, 1, 1], [2, 1, 1]
                // but we know sure r-- will not move out min number from search range
                r--;
            }
        }
        return nums[l];
    }
}
~~~

## 33. Search in Rotated Sorted Array (Medium)
Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

(i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).

You are given a target value to search. If found in the array return its index, otherwise return -1.

You may assume no duplicate exists in the array.

#### Solution
1. Based on *Find Minimum in Rotated Sorted Array*, find pivot ele and apply binary search twice.
2. Revised binary search in rotated array. **感觉很容易出错**

**题目指明不包含重复元素，如果有重复元素需要如何改变？详见 81. Search in Rotated Sorted Array II**

1
~~~
public class Solution {
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;
        int pivot = findPivot(nums);

        int left = binarySearch(nums, 0, pivot - 1, target);
        int right = binarySearch(nums, pivot, nums.length - 1, target);
        if (left == -1 && right == -1) return -1;
        return left == -1 ? right : left;
    }

    private int binarySearch(int[] nums, int s, int e, int target) {
        int l = s;
        int r = e;
        while (l <= r) {
            int m = (r - l) / 2 + l;
            if (nums[m] == target) return m;
            else if (nums[m] > target) {
                r = m - 1;
            }
            else {
                l = m + 1;
            }
        }
        return -1;
    }

    private int findPivot(int[] nums) {
        int l = 0;
        int r = nums.length - 1;
        while (l < r) {
            int m = (r - l) / 2 + l;
            if (nums[m] < nums[r]) {
                r = m;
            }
            else if (nums[m] > nums[r]){
                l = m + 1;
            }
            else {
                r--;
            }
        }
        return l;
    }
}
~~~

2
~~~
public class Solution {
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;

        int l = 0;
        int r = nums.length - 1;
        while (l <= r) {
            int m = (r - l) / 2 + l;
            if (nums[m] == target) return m;
            else if (nums[m] > nums[r]) { // left part is sorted
                if (target >= nums[l] && target < nums[m]) { // target is in the sorted left part
                    r = m - 1;
                }
                else {
                    l = m + 1;
                }
            }
            else { // right part is sorted
                if (target > nums[m] && target <= nums[r]) { // target is in the sorted right part
                    l = m + 1;
                }
                else {
                    r = m - 1;
                }
            }
        }

        return -1;
    }
}
~~~

## 81. Search in Rotated Sorted Array II (Medium) *
Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

(i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).

Write a function to determine if a given target is in the array.

The array may contain duplicates.

#### Solution
1. 不能沿用找到最小值再二分查找的办法，因为这个方法无法保证找到的最小元素是Pivot
2. 和最右边元素比较，在I的基础上改进，当nums[m] == nums[r]的时候，r--

Time Complexity: Worst Case O(n)
~~~
public class Solution {
    public boolean search(int[] nums, int target) {
        if (nums == null || nums.length == 0) return false;

        int l = 0;
        int r = nums.length - 1;
        while (l <= r) {
            int m = (r - l) / 2 + l;
            if (nums[m] == target) return true;
            else if (nums[m] > nums[r]) { // left part is sorted
                if (target >= nums[l] && target < nums[m]) { // target is in the sorted left part
                    r = m - 1;
                }
                else {
                    l = m + 1;
                }
            }
            else if (nums[m] < nums[r]) { // right part is sorted
                if (target > nums[m] && target <= nums[r]) { // target is in the sorted right part
                    l = m + 1;
                }
                else {
                    r = m - 1;
                }
            }
            else {
                r--;
            }
        }

        return false;
    }
}
~~~

~~~
[面试经验] Yahoo 电面； LC 33. Search in Rotated Sorted Array， follow-up Questions
 如果给的数组可能是ascending order， 也可能是 descending order ， 问应该怎么做； 我直接想的是， 写一个针对ascending的函数， 再有一个针对descending的函数， 对給的target调用两次
 但是interviewer说应该先check 给的数组， 确定到底是ascending的还是descending的；
 这个我没想出来。 不能直接对数组元素做两两比较， 因为数组是rotated；  我觉得应该是想办法找3个元素比较， 但是不确定怎么做。

 2. 当确定了input array是ascending 还是 descedning之后， 怎么在LC33 代码的基础上实现solution。
    我想的是， 添加一堆if-else 分支；
    但是interviewer说应该有更简洁的方法。 这个大家怎么想？

~~~

~~~
[面试经验] Uber面经分享
题目： How to insert a number in a rotated sorted array?
e.g: 给一个数组[4,5,8,9,1,2] target=10 --> [4,5,8,9,10,1,2]
当target=7 要返回 [4,5,7,8,9,1,2]
要求 要inplace（不要额外空间）， 时间复杂度要快于线性。
** 可能面经描述有问题，因为向数组插入元素，涉及到复制元素和位移，一般是O(n)，
~~~


# Binary Search in 2D Array
## 74. Search a 2D Matrix
Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:

Integers in each row are sorted from left to right.
The first integer of each row is greater than the last integer of the previous row.
For example,

Consider the following matrix:
~~~
[
  [1,   3,  5,  7],
  [10, 11, 16, 20],
  [23, 30, 34, 50]
]
~~~
Given target = 3, return true.

#### Solution

Attempt: 3 <br>
bug 1: 输入是matrix, 粗心用了nums
bug 2: 在转换成[][]的时候 [m / w][m % w]，粗心用了h
bug 3: 传统的二分查找结束的条件是l <= r
~~~
public class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return false;
        int h = matrix.length;
        int w = matrix[0].length;
        int l = 0;
        int r = h * w - 1;
        while (l <= r) {
            int m = (r - l) / 2 + l;
            if (target == matrix[m / w][m % w]) return true;
            else if (target > matrix[m / w][m % w]) l = m + 1;
            else r = m - 1;
        }
        return false;
    }
}
~~~

## 240. Search a 2D Matrix II (Optimal Solution not BS)
Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:

Integers in each row are sorted in ascending from left to right.
Integers in each column are sorted in ascending from top to bottom.
For example,

Consider the following matrix:
~~~
[
  [1,   4,  7, 11, 15],
  [2,   5,  8, 12, 19],
  [3,   6,  9, 16, 22],
  [10, 13, 14, 17, 24],
  [18, 21, 23, 26, 30]
]
~~~
Given target = 5, return true.

Given target = 20, return false.

#### Solution
1. Naiive way is to do binary search in each row, time O(nlogm)
2. 从右上角开始比较，if (target > matrix[row][col]) row++; else col--;

~~~
public class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return false;
        int h = matrix.length;
        int w = matrix[0].length;

        int row = 0;
        int col = w - 1;
        while (row < h && col >= 0) {
            if (target == matrix[row][col]) return true;
            else if (target > matrix[row][col]) {
                row++;
            }
            else {
                col--;
            }
        }
        return false;
    }
}
~~~

## 378. Kth Smallest Element in a Sorted Matrix
Given a n x n matrix where each of the rows and columns are sorted in ascending order, find the kth smallest element in the matrix.

Note that it is the kth smallest element in the sorted order, not the kth distinct element.

Example:

matrix = [
   [ 1,  5,  9],
   [10, 11, 13],
   [12, 13, 15]
],
k = 8,

return 13.
Note:
You may assume k is always valid, 1 ≤ k ≤ n2.

#### Solution
1. 暴力破解，Use max heap maintaining size of K, time O(mnlogk), space O(k), m is matrix height n is matrix width. 完全没有利用到sorted matrix的特点。
2. 类似于 Merge K Sorted Array, 利用了matrix每一个row都sorted的特点，但是没有利用matrix每一个col都sorted的特点, time O(klogm)
3. Binary search, 去左上角为最小值，右下角为最大值，O((m+n)log(max-min)), 在查找每一行最mid小的col的时候可以用二分，则复杂度是O(mlognlog(max-min)), log(max-min)最坏情况下为32.

1 O(mnlogk) 37ms
~~~
public class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        // if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(k, Collections.reverseOrder());
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (pq.size() < k) pq.offer(matrix[i][j]);
                else if (pq.size() == k && matrix[i][j] < pq.peek()) {
                    pq.poll();
                    pq.offer(matrix[i][j]);
                }
            }
        }
        return pq.poll();
    }
}
~~~

2 28ms
~~~
public class Solution {
    class Num {
        int value;
        int row;
        int col;

        public Num(int value, int row, int col){
            this.value = value;
            this.row = row;
            this.col = col;
        }
    }

    public int kthSmallest(int[][] matrix, int k) {
        PriorityQueue<Num> pq = new PriorityQueue<Num>(new Comparator<Num>() {
            public int compare(Num n1, Num n2) {
                return n1.value - n2.value;
            }
        });

        int m = matrix.length;
        int n = matrix[0].length;
        for (int i = 0; i < m; i++) {
            pq.offer(new Num(matrix[i][0], i, 0));
        }

        Num smallest = null;
        while (k > 0) {
            smallest = pq.poll();
            k--;
            int row = smallest.row;
            int col = smallest.col + 1;
            if (col < n) {
                Num next = new Num(matrix[row][col], row, col);
                pq.offer(next);
            }
        }

        // exception
        // if (smallest == null)

        return smallest.value;       
    }
}
~~~

3 1ms
~~~
public class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        int h = matrix.length;
        int w = matrix[0].length;
        int low = matrix[0][0];
        int high = matrix[h - 1][w - 1];
        while (low < high) {
            int mid = (high - low) / 2 + low;
            int j = w - 1;
            int count = 0;
            for (int i = 0; i < h; i++) {
                while (j >= 0 && matrix[i][j] > mid) j--;
                count += j + 1;
            }

            if (count >= k) {
                high = mid;
            }
            else {
                low = mid + 1;
            }
        }
        return low;
    }
}
~~~
