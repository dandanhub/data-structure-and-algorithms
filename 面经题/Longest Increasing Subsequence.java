// index of the search key, if it is contained in the array; otherwise, (-(insertion point) - 1). The insertion point is defined as the point at which the key would be inserted into the array: the index of the first element greater than the key, or a.length if all elements in the array are less than the specified key. Note that this guarantees that the return value will be >= 0 if and only if the key is found.

// ## 300. Longest Increasing Subsequence O(nlgn)
// Given an unsorted array of integers, find the length of longest increasing subsequence.
//
// For example,
// Given [10, 9, 2, 5, 3, 7, 101, 18],
// The longest increasing subsequence is [2, 3, 7, 101], therefore the length is 4. Note that there may be more than one LIS combination, it is only necessary for you to return the length.
//
// Your algorithm should run in O(n2) complexity.
//
// Follow up: Could you improve it to O(n log n) time complexity?
//
// #### Solution
// Method 1: DP O(n^2) time, O(n) space
// ~~~
public class Solution {
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        int ans = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) dp[i] = Math.max(dp[i], dp[j] + 1);
            }
            ans = Math.max(ans, dp[i]);
        }

        return ans;
    }
}
// ~~~

// Method 2: Binary Search, 维护一个递增数组, time O(nlgn), space O(n)
// ~~~
public class Solution {
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        int[] res = new int[n];
        int len = 0;

        for (int i = 0; i < n; i++) {
            int index = binarySearch(res, 0, len, nums[i]);
            res[index] = nums[i];
            if (index == len) {
                len++;
            }
        }

        return len;
    }

    private int binarySearch(int[] nums, int left, int right, int target) {
        while (left < right) {
            int m = (right - left) / 2 + left;
            if (nums[m] == target) return m;
            else if (nums[m] > target) right = m;
            else left = m + 1;
        }

        return left;
    }
}
// ~~~

// Java自带binarySearch

class Solution {
    public int lengthOfLIS(int[] nums) {
        // sanity check
        if (nums == null || nums.length == 0) return 0;

        int len = nums.length;
        int[] res = new int[len];
        int index = 0;
        for (int num : nums) {
            int binIndex = Arrays.binarySearch(res, 0, index, num);
            if (binIndex >= 0) {
                continue;
            }
            binIndex = -binIndex - 1;
            res[binIndex] = num;
            if (binIndex == index) {
                index++;
            }
        }
        return index;
    }
}
