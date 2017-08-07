## 523. Continuous Subarray Sum (+++)

Given a list of non-negative numbers and a target integer k, write a function to check if the array has a continuous subarray of size at least 2 that sums up to the multiple of k, that is, sums up to n*k where n is also an integer.

Example 1:
~~~
Input: [23, 2, 4, 6, 7],  k=6
Output: True
Explanation: Because [2, 4] is a continuous subarray of size 2 and sums up to 6.
~~~

Example 2:
~~~
Input: [23, 2, 6, 4, 7],  k=6
Output: True
Explanation: Because [23, 2, 6, 4, 7] is an continuous subarray of size 5 and sums up to 42.
~~~

Note:
The length of the array won't exceed 10,000.
You may assume the sum of all the numbers is in the range of a signed 32-bit integer.

#### Solution
1. Prefix sum 计算数组遍历到index的prefix sum
2. 用map存prefix sum % k的结果
3. 注意处理k == 0的情况
~~~
public class Solution {
    public boolean checkSubarraySum(int[] nums, int k) {
        if (nums == null || nums.length == 0) return false;

        if (k == 0) {
            for (int i = 1; i < nums.length; i++) {
                if (nums[i] == 0 && nums[i - 1] == 0) return true;
            }
            return false;
        }

        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        int res = 0;
        map.put(0, -1);
        for (int i = 0; i < nums.length; i++) {
            res += nums[i];
            if (map.containsKey(res % k) && i - map.get(res % k) > 1) {
                return true;
            }
            else if (!map.containsKey(res % k)) {
                map.put(res % k, i);
            }
        }
        return false;
    }
}
~~~

## 560. Subarray Sum Equals K (+)
Given an array of integers and an integer k, you need to find the total number of continuous subarrays whose sum equals to k.

Example 1:
~~~
Input:nums = [1,1,1], k = 2
Output: 2
~~~

Note:
The length of the array is in range [1, 20,000].
The range of numbers in the array is [-1000, 1000] and the range of the integer k is [-1e7, 1e7].

#### Solution
1. Prefix sum, 使用map来保存，key是prefix sum, value是等于这个sum的个数
2. 注意初始化的时候，往map里面存一个dummy的结点map.put(0, 1)，表示刚开始的时候prefix sum为0，且有一个
~~~
public class Solution {
    public int subarraySum(int[] nums, int k) {
        if (nums == null || nums.length == 0) return 0;

        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(0, 1);
        int res = 0;
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            res += nums[i];
            if (map.containsKey(res - k)) {
                count += map.get(res - k);
            }
            map.put(res, map.getOrDefault(res, 0) + 1);
        }

        return count;
    }
}
~~~

## 53. Maximum Subarray
Find the contiguous subarray within an array (containing at least one number) which has the largest sum.

For example, given the array [-2,1,-3,4,-1,2,1,-5,4],
the contiguous subarray [4,-1,2,1] has the largest sum = 6.

click to show more practice.

More practice:
If you have figured out the O(n) solution, try coding another solution using the divide and conquer approach, which is more subtle.

#### Solution

~~~
public class Solution {
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int max = nums[0];
        int sum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (sum < 0) sum = 0;
            sum += nums[i];
            max = Math.max(max, sum);
        }
        return max;
    }
}
~~~

## 152. Maximum Product Subarray

Find the contiguous subarray within an array (containing at least one number) which has the largest product.

For example, given the array [2,3,-2,4],
the contiguous subarray [2,3] has the largest product = 6.

#### Solution
扫描数组，维护一个当前最大值和最小值
- 最大值等于max(max(max * nums[i], min * nums[i]), nums[i])
- 最小值等于min(min(max * nums[i], min * nums[i]), nums[i])

~~~
public class Solution {
    public int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int ans = nums[0];
        int max = nums[0];
        int min = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int temp = max;
            max = Math.max(Math.max(nums[i] * max, nums[i] * min), nums[i]);
            min = Math.min(Math.min(nums[i] * temp, nums[i] * min), nums[i]);
            ans = Math.max(ans, max);
        }

        return ans;
    }
}
~~~
