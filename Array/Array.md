## 325. Maximum Size Subarray Sum Equals k
Given an array nums and a target value k, find the maximum length of a subarray that sums to k. If there isn't one, return 0 instead.

Note:
The sum of the entire nums array is guaranteed to fit within the 32-bit signed integer range.

Example 1:
Given nums = [1, -1, 5, -2, 3], k = 3,
return 4. (because the subarray [1, -1, 5, -2] sums to 3 and is the longest)

Example 2:
Given nums = [-2, -1, 2, 1], k = 1,
return 2. (because the subarray [-1, 2] sums to 1 and is the longest)

Follow Up:
Can you do it in O(n) time?

#### Solution
直接能想到的就是O(n^2)的brute force的方法，要求O(n)如何解决 <br>
类似的sum的问题，O(n)时间的要求，都可以考虑使用HashMap

~~~
public class Solution {
    public int maxSubArrayLen(int[] nums, int k) {
        // edge case
        if (nums == null || nums.length == 0) return 0;
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        int sum = 0;
        int len = 0;
        map.put(sum, -1);
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (map.containsKey(sum - k)) {
                int left = map.get(sum - k);
                len = Math.max(len, i - left);
            }
            map.putIfAbsent(sum, i);
        }

        return len;
    }
}
~~~

## 525. Contiguous Array
Given a binary array, find the maximum length of a contiguous subarray with equal number of 0 and 1.

Example 1:
~~~
Input: [0,1]
Output: 2
Explanation: [0, 1] is the longest contiguous subarray with equal number of 0 and 1.
~~~
Example 2:
~~~
Input: [0,1,0]
Output: 2
Explanation: [0, 1] (or [1, 0]) is a longest contiguous subarray with equal number of 0 and 1.
~~~
Note: The length of the given binary array will not exceed 50,000.

#### Solution
325的变形题目，用hashtable保存sum结果。需要注意的是，为了方便处理，遇到0的时候对sum--操作
~~~
public class Solution {

    public int findMaxLength(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        Map<Integer, Integer> map = new HashMap<Integer, Integer>();

        int sum = 0;
        map.put(0, -1);
        int len = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) sum++;
            else sum--;

            if (map.containsKey(sum)) {
                len = Math.max(len, i - map.get(sum));
            }
            else map.put(sum, i);
        }

        return len;
    }
}
~~~

## 209. Minimum Size Subarray Sum
Given an array of n positive integers and a positive integer s, find the minimal length of a contiguous subarray of which the sum >= s. If there isn't one, return 0 instead.

For example, given the array [2,3,1,2,4,3] and s = 7,
the subarray [4,3] has the minimal length under the problem constraint.

#### Solution
Two pointers
~~~
public class Solution {
    public int minSubArrayLen(int s, int[] nums) {
        if (nums == null) {
            return 0;
        }

        int sum = 0;
        int len = Integer.MAX_VALUE;
        int i = 0, j = 0;
        while (j < nums.length) {
            sum += nums[j];
            j++;

            // move left pointer
            while (sum >= s) {
                len = Math.min(len, j - i);
                sum -= nums[i];
                i++;
            }
        }

        return len == Integer.MAX_VALUE ? 0 : len;
    }
}
~~~

---


## 41. First Missing Positive
Given an unsorted integer array, find the first missing positive integer.

For example,
Given [1,2,0] return 3,
and [3,4,-1,1] return 2.

Your algorithm should run in O(n) time and uses constant space.

#### Solution
进行swap的三个条件：
1. 当前num[i] > 0
2. 当前num[i]不在正确的index位置上
3. 当前nums[i] - 1在范围内
4. **正确index位置上的数字是错误的，处理重复数字**

Since the problem requires to use constant space, so we mark whether an integer appears in the array itself.
For example, "3 4 1 -1", we scan the array.
1. current element 3 -> swap(nums, 1, 2) -> "1 4 3 -1"
2. current element 1 -> 1 is in the right postision, no swap -> move forward
3. current element 4 -> swap(nums, 1, 3) -> "1 -1 3 4"
4. current element -1 -> -1 < 0 -> move forward
5. current element 3 -> 3 is in the right postision, no swap -> move forward
6. current element 4 -> 4 is in the right postision, no swap -> move forward
Note corner case when there exist duplicate numbers, like [1,1].

Attempt: 1
~~~
public class Solution {
    public int firstMissingPositive(int[] nums) {
        if (nums == null || nums.length == 0) return 1;
        int i = 0;
        while (i < nums.length) {
            // nums[i] is positive and not in its right position
            if (nums[i] > 0 && nums[i] - 1 < nums.length && nums[i] - 1 != i && nums[nums[i] - 1] != nums[i]) {
                swap(nums, i, nums[i] - 1);
            }
            else {
                i++;
            }
        }

        // scan the array again to find the 1st missing pos num
        for (int j = 0; j < nums.length; j++) {
            if (nums[j] - 1 != j) return j + 1;
        }

        return nums.length + 1;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
~~~

## 321. Create Maximum Number
Given two arrays of length m and n with digits 0-9 representing two numbers. Create the maximum number of length k <= m + n from digits of the two. The relative order of the digits from the same array must be preserved. Return an array of the k digits. You should try to optimize your time and space complexity.

Example 1:
nums1 = [3, 4, 6, 5]
nums2 = [9, 1, 2, 5, 8, 3]
k = 5
return [9, 8, 6, 5, 3]

Example 2:
nums1 = [6, 7]
nums2 = [6, 0, 4]
k = 5
return [6, 7, 6, 0, 4]

Example 3:
nums1 = [3, 9]
nums2 = [8, 9]
k = 3
return [9, 8, 9]

#### Solution
1. 找出array1中i个digit最大的数字，找出array2中k-i个digit最大的数字
2. 合并array1和array2得到的最大数字
- 在合并数组的时候，如何判定取array1还是array2的数字，考虑测试用例 <br>
~~~
33338
333

33338
339
~~~
- 如何加快代码速度和解题速度
3. 这题可以分解为找出array中k个digit能组成的最大数字，以及合并两个array，在保持原有数组顺序的基础上，得到最大数字

~~~
public class Solution {
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int m = nums1.length;
        int n = nums2.length;
        int[] ans = new int[k];
        for (int i = 0; i <= k; i++) {
            if (i <= m && k - i <= n) {
                int[] array1 = maxNumberFromArray(nums1, i);
                int[] array2 = maxNumberFromArray(nums2, k - i);
                int[] merged = merge(array1, array2);
                if (compareArray(merged, 0, ans, 0)) ans = merged;
            }
        }

        return ans;
    }

    // compare int[] nums1 with int[] nums2
    private boolean compareArray(int[] nums1, int s1, int[] nums2, int s2) {
        int i = s1, j = s2;
        int m = nums1.length, n = nums2.length;
        while (i < m && j < n && nums1[i] == nums2[j]) {
            i++;
            j++;
        }

        // verbose
        if ((i < m && j < n && nums1[i] > nums2[j]) || (i < m && j == n && nums1[i] >= nums1[s1])
                 || (i == m && j < n && nums2[j] < nums2[s2])) return true;

        return false;
    }

    // merge two num array
    private int[] merge(int[] nums1, int[] nums2) {
        int i = 0, j = 0, k = 0;
        int m = nums1.length, n = nums2.length;
        int[] ans = new int[m + n];
        while (i < m && j < n) {
            if (nums1[i] > nums2[j]) {
                ans[k++] = nums1[i++];
            }
            else if (nums1[i] < nums2[j]) {
                ans[k++] = nums2[j++];
            }
            else { // equal
                if (compareArray(nums1, i, nums2, j)) ans[k++] = nums1[i++];
                else ans[k++] = nums2[j++];
            }
        }

        while (i < m) {
            ans[k++] = nums1[i++];
        }
        while (j < n) {
            ans[k++] = nums2[j++];
        }

        return ans;
    }

    // 119ms
    // generate max k-digit number from one array
    private int[] maxNumberFromArray(int[] nums, int k) {
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < nums.length; i++) {
            while (nums.length - i + stack.size() > k && !stack.isEmpty() && nums[i] > stack.peek()) {
                stack.pop();
            }
            if (stack.size() < k) {
                stack.push(nums[i]);
            }
        }

        int[] ans = new int[k];
        int index = k - 1;
        while (!stack.isEmpty() && index >= 0) {
            ans[index--] = stack.pop();
        }

        return ans;
    }

    // 15 ms
    // generate max k-digit number from one array
    private int[] maxNumberFromArray(int[] nums, int k) {
        int[] ans = new int[k];
        int top = 0;
        for (int i = 0; i < nums.length; i++) {
            while (nums.length - i + top > k && top > 0 && nums[i] > ans[top - 1]) {
                top--;
            }
            if (top < k) ans[top++] = nums[i];
        }

        return ans;
    }
}
~~~

## 643. Maximum Average Subarray I (++)*
Given an array consisting of n integers, find the contiguous subarray of given length k that has the maximum average value. And you need to output the maximum average value.

Example 1:
~~~
Input: [1,12,-5,-6,50,3], k = 4
Output: 12.75
Explanation: Maximum average is (12-5-6+50)/4 = 51/4 = 12.75
~~~
Note:
1 <= k <= n <= 30,000.
Elements of the given array will be in the range [-10,000, 10,000].

#### Solution
滑动窗口，注意如果取Double的最小值，应该是Double.MIN_VALUE.

Attempt: 3
~~~
public class Solution {
    public double findMaxAverage(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) return 0.0;

        double sum = 0;
        for (int i = 0; i < k; i++) sum += nums[i];
        double res = sum;

        for (int i = k; i < nums.length; i++) {
            sum += nums[i] - nums[i - k];
            res = Math.max(res, sum);
        }

        return res / k;
    }
}
~~~
