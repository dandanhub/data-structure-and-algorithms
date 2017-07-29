## 334. Increasing Triplet Subsequence
Given an unsorted array return whether an increasing subsequence of length 3 exists or not in the array.

Formally the function should:
Return true if there exists i, j, k
such that arr[i] < arr[j] < arr[k] given 0 ≤ i < j < k ≤ n-1 else return false.
Your algorithm should run in O(n) time complexity and O(1) space complexity.

Examples:
Given [1, 2, 3, 4, 5],
return true.

Given [5, 4, 3, 2, 1],
return false.

#### Solution
维护x1, x2，分别是当前最小和次小，如果能找到num同时比他们大，则返回true.

~~~
public class Solution {
    public boolean increasingTriplet(int[] nums) {
        if (nums == null || nums.length < 3) return false;
        int x1 = Integer.MAX_VALUE, x2 = Integer.MAX_VALUE;

        for (int num : nums) {
            if (num <= x1) x1 = num;
            else if (num <= x2) x2 = num;
            else return true;
        }

        return false;
    }
}
~~~

#### Follow up: Increasing K Subsequence
如果判断是否存在长度为K的递增序列，如何处理？
得到最长递增序列，然后判断长度是否大于K

~~~
public class Solution {
    public boolean increasingTriplet(int[] nums) {
        if (nums == null || nums.length < 3) return false;
        int x1 = Integer.MAX_VALUE, x2 = Integer.MAX_VALUE;

        // generilize triple to K
        int[] res = new int[3];
        int len = 0;
        for (int num : nums) {
            int index = binarySearch(res, 0, len, num);
            res[index] = num;
            if (index == len) {
                len++;
                if (len == 3) return true;
            }
        }

        return false;
    }

    private int binarySearch(int[] nums, int l, int r, int target) {
        while (l < r) {
            int m = (r - l) / 2 + l;
            if (nums[m] == target) return m;
            else if (target > nums[m]) l = m + 1;
            else r = m;
        }

        return r;
    }
}
~~~

## 300. Longest Increasing Subsequence O(nlgn)
Given an unsorted array of integers, find the length of longest increasing subsequence.

For example,
Given [10, 9, 2, 5, 3, 7, 101, 18],
The longest increasing subsequence is [2, 3, 7, 101], therefore the length is 4. Note that there may be more than one LIS combination, it is only necessary for you to return the length.

Your algorithm should run in O(n2) complexity.

Follow up: Could you improve it to O(n log n) time complexity?

#### Solution
Method 1: DP O(n^2) time, O(n) space
~~~
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
~~~

Method 2: Binary Search, 维护一个递增数组, time O(nlgn), space O(n)
~~~
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

        return right;
    }
}
~~~

## 354. Russian Doll Envelopes
You have a number of envelopes with widths and heights given as a pair of integers (w, h). One envelope can fit into another if and only if both the width and height of one envelope is greater than the width and height of the other envelope.

What is the maximum number of envelopes can you Russian doll? (put one inside other)

Example:
Given envelopes = [[5,4],[6,4],[6,7],[2,3]], the maximum number of envelopes you can Russian doll is 3 ([2,3] => [5,4] => [6,7]).

#### Solution
1. 根据heights排序, Arrays.sort(envelopes, new Comparator<int[]> {});
2. 对"sorted"的list，根据widths寻找最长递增序列

Method 1: DP O(n^2)
~~~
public class Solution {
    public int maxEnvelopes(int[][] envelopes) {
        if (envelopes == null || envelopes.length == 0 || envelopes[0].length == 0) return 0;

        // sort the array based on height
        Arrays.sort(envelopes, new Comparator<int[]>() {
            public int compare(int[] e1, int[] e2) {
                // if (e1[1] == e2[1]) return e1[0] - e2[0];
                return e1[1] - e2[1];
            }
        });

        int n = envelopes.length;
        int ans = 0;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        for (int i = 0; i < envelopes.length; i++) {
            for (int j = 0; j < i; j++) {
                if (envelopes[i][1] > envelopes[j][1] && envelopes[i][0] > envelopes[j][0]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            ans = Math.max(ans, dp[i]);
        }

        return ans;
    }
}
~~~

Method 2: 寻找最大递增序列的时候使用O(nlgn)的算法 <br>
**在排序的时候put envelope with greater width of the same height at first**
~~~
public class Solution {
    public int maxEnvelopes(int[][] envelopes) {
        if (envelopes == null || envelopes.length == 0 || envelopes[0].length == 0) return 0;

        // sort the array based on height
        Arrays.sort(envelopes, new Comparator<int[]>() {
            public int compare(int[] e1, int[] e2) {
                // tricky part : put envelope with greater width of the same height at first
                if (e1[1] == e2[1]) return e2[0] - e1[0];
                return e1[1] - e2[1];
            }
        });

        int n = envelopes.length;
        int len = 0;
        int[] nums = new int[n];

        for (int i = 0; i < envelopes.length; i++) {
            int index = binarySearch(nums, 0, len, envelopes[i][0]);
            nums[index] = envelopes[i][0];
            if (index == len) len++;

        }

        return len;
    }

    private int binarySearch(int[] nums, int l, int r, int target) {
        while (l < r) {
            int m = (r - l) / 2 + l;
            if (nums[m] == target) return m;
            else if (target > nums[m]) l = m + 1;
            else r = m;
        }
        return r;
    }
}
~~~

## 646. Maximum Length of Pair Chain
You are given n pairs of numbers. In every pair, the first number is always smaller than the second number.

Now, we define a pair (c, d) can follow another pair (a, b) if and only if b < c. Chain of pairs can be formed in this fashion.

Given a set of pairs, find the length longest chain which can be formed. You needn't use up all the given pairs. You can select pairs in any order.

Example 1:
Input: [[1,2], [2,3], [3,4]]
Output: 2
Explanation: The longest chain is [1,2] -> [3,4]
Note:
The number of given pairs will be in the range [1, 1000].

#### Solution
1. Method 1: DP O(n^2)
  - 先根据first num排序
  - 然后根据规则找出最长递增序列
~~~
public class Solution {
    public int findLongestChain(int[][] pairs) {
        if (pairs == null || pairs.length == 0 || pairs[0].length == 0) {
            return 0;
        }

        Arrays.sort(pairs, new Comparator<int[]>() {
            public int compare(int[] e1, int[] e2) {
                if (e1[0] == e2[0]) return e1[1] - e2[1];
                return e1[0] - e2[0];
            }    
        });

        int n = pairs.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        int len = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (pairs[i][0] > pairs[j][1]) dp[i] = Math.max(dp[i], dp[j] + 1);
            }
            len = Math.max(len, dp[i]);
        }

        return len;
    }
}
~~~

2. Method 2: O(nlgn)
  - 排序按照第二个数字排序（结束时间）
  - 然后遍历一遍找到最长的不冲突
~~~
public class Solution {
    public int findLongestChain(int[][] pairs) {
        if (pairs == null || pairs.length == 0 || pairs[0].length == 0) {
            return 0;
        }

        Arrays.sort(pairs, new Comparator<int[]>() {
            public int compare(int[] e1, int[] e2) {
                if (e1[1] == e2[1]) return e1[0] - e2[0];
                return e1[1] - e2[1];
            }    
        });

        int n = pairs.length;
        int i = 0;
        int len = 0;
        while (i < n) {
            int currEnd = pairs[i][1];
            len++;
            while (i < n && pairs[i][0] <= currEnd) {
                i++;
            }
        }

        return len;
    }
}
~~~

## 329. Longest Increasing Path in a Matrix

Given an integer matrix, find the length of the longest increasing path.

From each cell, you can either move to four directions: left, right, up or down. You may NOT move diagonally or move outside of the boundary (i.e. wrap-around is not allowed).

Example 1:
~~~
nums = [
  [9,9,4],
  [6,6,8],
  [2,1,1]
]
~~~
Return 4
The longest increasing path is [1, 2, 6, 9].

Example 2:
~~~
nums = [
  [3,4,5],
  [3,2,6],
  [2,2,1]
]
~~~
Return 4
The longest increasing path is [3, 4, 5, 6]. Moving diagonally is not allowed.

#### Solution
慢版本：从每个点开始，向合法的上下左右走，dist[i][j]记录从出发点到matrix[j][j]的距离。初始化是dist[i][j]为1，一旦找到出现距离大于dist[i][j]的情况，那么更新dist[i][j]。由于这样造成了很多重复，performance不是很好。

改良版：从每个点出发，记录从这个点作为起始点，能reach的最大距离，如果在DFS的过程中到达了访问过的点就不再重复访问，这样避免了重复。
