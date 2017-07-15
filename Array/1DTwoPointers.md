# Two Pointers

## 1. Two Sum (Easy)
Given an array of integers, return **indices** of the two numbers such that they add up to a specific target.

**You may assume that each input would have exactly one solution**, and you **may not use the same element twice**.

Example:
~~~
Given nums = [2, 7, 11, 15], target = 9,

Because nums[0] + nums[1] = 2 + 7 = 9,
return [0, 1].
~~~

#### Solution
Time complexity: O(n)
Space complexity: O(n)
Attempt: 1 time
~~~
public class Solution {
    public int[] twoSum(int[] nums, int target) {
        if (nums == null || nums.length == 0) return new int[0];

        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                int[] ans = new int[2];
                ans[0] = map.get(target - nums[i]);
                ans[1] = i;
                return ans;
            }
            map.put(nums[i], i);
        }

        return new int[0];
    }
}
~~~

## 15. 3Sum (Medium)
Given an array S of n integers, are there elements a, b, c in S such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.

Note: The solution set must not contain duplicate triplets.

For example, given array S = [-1, 0, 1, 2, -1, -4],

A solution set is:
[
  [-1, 0, 1],
  [-1, -1, 2]
]

#### Solution
Time complexity: O(n^2)
Space complexity: O(1)
Attempt: 1

**如何避免重复是重点**

~~~
public class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        if (nums == null || nums.length == 0) return ans;

        // sort the array
        Arrays.sort(nums);

        // iterate through the array
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;  // avoid dup
            int target = 0 - nums[i];

            // two pointers
            int m = i + 1;
            int n = nums.length - 1;
            while (m < n) {
                if (nums[m] + nums[n] == target) {
                    List<Integer> list = new ArrayList<Integer>();
                    list.add(nums[i]);
                    list.add(nums[m]);
                    list.add(nums[n]);
                    ans.add(list);
                    m++;
                    n--;
                    while (m < n && n < nums.length - 1 && nums[n] == nums[n + 1]) n--; // avoid dup
                    while (m < n && m > i + 1 && nums[m] == nums[m - 1]) m++;
                }
                else if (nums[m] + nums[n] > target) {
                    n--;
                }
                else {
                    m++;
                }
            }
        }

        return ans;
    }
}
~~~

## 16. 3Sum Closest (Medium)
Given an array S of n integers, find three integers in S such that the sum is closest to a given number, target. Return the sum of the three integers. You may assume that each input would have exactly one solution.

~~~
    For example, given array S = {-1 2 1 -4}, and target = 1.

    The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
~~~

#### Solution
Time complexity: O(n^2)
Space complexity: O(1)
Attempt: 2

**注意题目要求返回的结果形式**

~~~
public class Solution {
    public int threeSumClosest(int[] nums, int target) {
        // to confirm how to handle the edge cases
        if (nums == null || nums.length < 3) return 0;

        // init the return value ans
        int ans = nums[0] + nums[1] + nums[2];

        // sort the array
        Arrays.sort(nums);

        // iterate through the arry
        for (int i = 0; i < nums.length; i++) {
            // two pointers
            int a = i + 1;
            int b = nums.length - 1;
            while (a < b) {
                int sum = nums[i] + nums[a] + nums[b];
                if (Math.abs(ans - target) > Math.abs(sum - target)) {
                    ans = sum;
                }

                if (sum == target) {
                    return target;
                }
                else if (sum > target) {
                    b--;
                }
                else {
                    a++;
                }
            }
        }

        return ans;
    }
}
~~~

## 259. 3Sum Smaller (Medium)
Given an array of n integers nums and a target, find the number of index triplets i, j, k with 0 <= i < j < k < n that satisfy the condition nums[i] + nums[j] + nums[k] < target.

For example, given nums = [-2, 0, 1, 3], and target = 2.

Return 2. Because there are two triplets which sums are less than 2:
~~~
[-2, 0, 1]
[-2, 0, 3]
~~~
Follow up:
Could you solve it in O(n^2) runtime?

#### Solution
1. Clearly, we have a O(n^3) naiive solution.
2. Using binary search, we can optimize to O(n^2logn).
3. Two pointers, O(n^2)

**如何在O(n^2)的时间解决问题**
**这道题目有一道变形 611. Valid Triangle Number**

O(n^2logn)
Attempt: 1
~~~
public class Solution {
    public int threeSumSmaller(int[] nums, int target) {
        if (nums == null || nums.length == 0) return 0;

        // sort the array
        Arrays.sort(nums);

        int count = 0;

        // iterate the nums
        for (int i = 0; i < nums.length - 2; i++) {
            for (int j = i + 1; j < nums.length - 1; j++) {
                int num = target - nums[i] - nums[j];
                int l = j + 1;
                int r = nums.length - 1;
                if (nums[r] < num) {
                    count += r - j;
                    continue;
                }
                if (nums[l] >= num) {
                    continue;
                }
                while (r - l >= 2) {
                    int m = (r - l) / 2 + l;
                    if (nums[m] < num) {
                        l = m;
                    }
                    else {
                        r = m;
                    }
                }
                count += l - j;
            }
        }

        return count;
    }
}
~~~

O(n^2)
Attempt: 3
~~~
public class Solution {
    public int threeSumSmaller(int[] nums, int target) {
        if (nums == null || nums.length == 0) return 0;

        // sort the array
        Arrays.sort(nums);

        int count = 0;

        // iterate the nums
        for (int i = 0; i < nums.length - 2; i++) {
            // two pointers
            int l = i + 1;
            int r = nums.length - 1;
            while (l < r) {
                if (nums[i] + nums[l] + nums[r] < target) {
                    count += r - l;
                    l++;
                }
                else {
                    r--;
                }
            }
        }

        return count;
    }
}
~~~

## 611. Valid Triangle Number (Medium)
Given an array consists of non-negative integers, your task is to count the number of triplets chosen from the array that can make triangles if we take them as side lengths of a triangle.

Example 1:
~~~
Input: [2,2,3,4]
Output: 3
Explanation:
Valid combinations are:
2,3,4 (using the first 2)
2,3,4 (using the second 2)
2,2,3
~~~

Note:
The length of the given array won't exceed 1000.
The integers in the given array are in the range of [0, 1000].

#### Solution
这些类似的问题都最终转化成，在sorted array找出 a + b == target的问题

**两个指针如何移动 1. 固定右指针（大），移动左指针 或者 2. 固定左指针（小），移动右指针**

O(n^2)
Attempt: 3
~~~
public class Solution {
    public int triangleNumber(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        Arrays.sort(nums); // sort the array

        int count = 0; // return result

        // iterate the array
        for (int i = nums.length - 1; i >= 0; i--) {
            int l = 0;
            int r = i - 1;
            while (l < r) {
                if (nums[l] + nums[r] > nums[i]) {
                    count += r - l;
                    r--;
                }
                else {
                    l++;
                }
            }
        }

        return count;
    }
}
~~~

## 456. 132 Pattern
Given a sequence of n integers a1, a2, ..., an, a 132 pattern is a subsequence ai, aj, ak such that i < j < k and ai < ak < aj. Design an algorithm that takes a list of n numbers as input and checks whether there is a 132 pattern in the list.

Note: n will be less than 15,000.

Example 1:
~~~
Input: [1, 2, 3, 4]

Output: False

Explanation: There is no 132 pattern in the sequence.
~~~
Example 2:
~~~
Input: [3, 1, 4, 2]

Output: True

Explanation: There is a 132 pattern in the sequence: [1, 4, 2].
~~~
Example 3:
~~~
Input: [-1, 3, 2, 0]

Output: True

Explanation: There are three 132 patterns in the sequence: [-1, 3, 2], [-1, 3, 0] and [-1, 2, 0].
~~~

#### Solution
1. O(n^2) method : fix j, find min from range(0, j - 1), check whether in range(j + 1, nums.length - 1) has a number less than nums[j] and greater than min.
2. O(n) method : using stack.  

Method 1 O(n^2) <br>
Attempt: 1
~~~
public class Solution {
    public boolean find132pattern(int[] nums) {
        if (nums == null || nums.length < 3) return false;

        // fix j
        for (int j = 1; j < nums.length - 1; j++) {
            // find min num in range from 0 to j - 1, and make it as i
            int min = nums[0];
            // int index = 0; // no need to track index
            for (int i = 1; i < j; i++) {
                if (nums[i] < min) {
                    min = nums[i];
                    // index = i;
                }
            }

            // found a valid i
            if (min < nums[j]) {
                // find k in range from j + 1 to nums.length - 1
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[k] < nums[j] && nums[k] > min) return true;
                }
            }
        }

        return false;
    }
}
~~~

Stack O(n)
~~~
public class Solution {
    public boolean find132pattern(int[] nums) {
        Stack<Integer> stack = new Stack<Integer>();
        int min = Integer.MAX_VALUE;
        for (int num : nums) {
            if (num < min) {
                min = num;
            }
            else {
                while (!stack.empty()) {
                    if (num <= stack.peek()) break;
                    stack.pop();
                    if (num < stack.pop()) return true;
                }
                stack.push(num);
                stack.push(min);
            }
        }
        return false;
    }
}
~~~

## 18. 4Sum (Medium)
Given an array S of n integers, are there elements a, b, c, and d in S such that a + b + c + d = target? Find all unique quadruplets in the array which gives the sum of target.

Note: The solution set must not contain duplicate quadruplets.
~~~
For example, given array S = [1, 0, -1, 0, -2, 2], and target = 0.

A solution set is:
[
  [-1,  0, 0, 1],
  [-2, -1, 1, 2],
  [-2,  0, 0, 2]
]
~~~

#### Solution

O(n^3)
Attempt: 1
~~~
public class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        if (nums == null || nums.length == 0) return ans;

        // sort the array
        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue; // to avoid dup
            for (int j = i + 1; j < nums.length - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue; // to avoid dup
                int newTarget = target - nums[i] - nums[j];
                // two pointers
                int l = j + 1;
                int r = nums.length - 1;
                while (l < r) {
                    int sum = nums[l] + nums[r];
                    if (sum == newTarget) {
                        List<Integer> list = new ArrayList<Integer>();
                        list.add(nums[i]);
                        list.add(nums[j]);
                        list.add(nums[l]);
                        list.add(nums[r]);
                        ans.add(list);
                        l++;
                        r--;
                        while (l < r && l > j + 1 && nums[l] == nums[l - 1]) l++;
                        while (l < r && r < nums.length - 1 && nums[r] == nums[r + 1]) r--;
                    }
                    else if (sum > newTarget) r--;
                    else l++;
                }
            }
        }

        return ans;
    }
}
~~~

## 454. 4Sum II (Medium)
Given four lists A, B, C, D of integer values, compute how many tuples (i, j, k, l) there are such that A[i] + B[j] + C[k] + D[l] is zero.

To make problem a bit easier, all A, B, C, D have same length of N where 0 ≤ N ≤ 500. All integers are in the range of -228 to 228 - 1 and the result is guaranteed to be at most 231 - 1.

Example:
~~~
Input:
A = [ 1, 2]
B = [-2,-1]
C = [-1, 2]
D = [ 0, 2]

Output:
2

Explanation:
The two tuples are:
1. (0, 0, 0, 1) -> A[0] + B[0] + C[0] + D[1] = 1 + (-2) + (-1) + 2 = 0
2. (1, 1, 0, 0) -> A[1] + B[1] + C[0] + D[0] = 2 + (-1) + (-1) + 0 = 0
~~~

#### Solution
Time complexity: O(n^2)
Space complexity: O(n^2) // can we do better?

**注意题目要求的返回值是tuples的个数，第一次尝试看错题意**

Attempt: 2
~~~
public class Solution {
    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        int N = A.length;
        Map<Integer, Integer> m1 = new HashMap<Integer, Integer>();
        Map<Integer, Integer> m2 = new HashMap<Integer, Integer>(); // we can optimize space cost by not using the second map
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int s1 = A[i] + B[j];
                m1.put(s1, m1.getOrDefault(s1, 0) + 1);
                int s2 = C[i] + D[j];
                m2.put(s2, m2.getOrDefault(s2, 0) + 1);
            }
        }

        int count = 0;
        for (Map.Entry<Integer, Integer> entry : m1.entrySet()) {
            int num = entry.getKey();
            if (m2.containsKey(-num)) {
                int c1 = entry.getValue();
                int c2 = m2.get(-num);
                count += c1 * c2;
            }
        }

        return count;
    }
}
~~~

V2 which uses one map
~~~
public class Solution {
    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {

        Map<Integer, Integer> m1 = new HashMap<Integer, Integer>();
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B.length; j++) {
                int s = A[i] + B[j];
                m1.put(s, m1.getOrDefault(s, 0) + 1);
            }
        }

        int count = 0;
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < D.length; j++) {
                int s = C[i] + D[j];
                if (m1.containsKey(-s)) {
                    count += m1.get(-s);
                }
            }
        }

        return count;
    }
}
~~~

## 11. Container With Most Water (Medium)
Given n non-negative integers a1, a2, ..., an, where each represents a point at coordinate (i, ai). n vertical lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0). Find two lines, which together with x-axis forms a container, such that the container contains the most water.

Note: You may not slant the container and n is at least 2.

#### Solution
Two pointers <br>
Old version of code will get TLE with new leetcode test code.
How to improve?

~~~
public class Solution {
    public int maxArea(int[] height) {
        if (height == null || height.length == 0) return 0;

        int area = 0;
        int i = 0, j = height.length - 1;
        while (i < j) {
            area = Math.max(area, (j - i) * Math.min(height[i], height[j]));
            int left = height[i];
            int right = height[j];

            if (left >= right) {
                // do while to accelerate the process
                while (i < j && right >= height[j]) {
                    j--;
                }
            }
            else {
                while (i < j && left >= height[i]) {
                    i++;
                }
            }
        }
        return area;
    }
}
~~~

## 42. Trapping Rain Water
Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it is able to trap after raining.

For example,
Given [0,1,0,2,1,0,1,3,2,1,2,1], return 6.

#### Solution
1. 最基本的思路是我们扫描数组三遍，第一遍从左到右，算出每个bar左边的最大值；第二遍从右向左，算出每个bar右边的最大值；然后第三遍扫描数组，累加每个bar能够存储的水量，area += Math.min(left[i], right[i]) - height[i].
2. 用 two pointers 做到扫描一遍，分别维护leftMax和rightMax
- 如果leftMax <= rightMax, 那么我们可以把当前left所指的bar加入最终结果，area += leftMax - height[i]
- 如果leftMax > rightMax, 那么我们可以把当前right所指的bar加入最终结果，area += rightMax - height[i]

Attempt: 2
~~~
public class Solution {
    public int trap(int[] height) {
        int i = 0;
        int j = height.length - 1;
        int area = 0;
        int leftmax = 0;
        int rightmax = 0;
        while(i < j) {
            leftmax = Math.max(height[i], leftmax); // 注意更新leftMax和rightMax
            rightmax = Math.max(height[j], rightmax);
            if(leftmax < rightmax) {
                area += leftmax - height[i];
                i++;
            }
            else {
                area += rightmax - height[j];
                j--;
            }
        }

        return area;
    }
}
~~~
