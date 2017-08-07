# Backtracking

## 39. Combination Sum (Medium)
Given a set of candidate numbers (C) (without duplicates) and a target number (T), find all unique combinations in C where the candidate numbers sums to T.

The same repeated number may be chosen from C unlimited number of times.

Note:
All numbers (including target) will be positive integers.
The solution set must not contain duplicate combinations.
For example, given candidate set [2, 3, 6, 7] and target 7,
A solution set is:
~~~
[
  [7],
  [2, 2, 3]
]
~~~

#### Solution
**这道题有几个关键点，让题目变得简单 1. without duplicates 2. All numbers (including target) > 0**

Time Complexity: Exponential O(2^n)
Space Complexity: the size of res list

Attempt: 3
~~~
public class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        if (candidates == null || candidates.length == 0) return ans;
        backtrack(ans, new ArrayList<Integer>(), 0, candidates, target, 0);
        return ans;
    }

    private void backtrack(List<List<Integer>> ans, ArrayList<Integer> list, int sum, int[] candidates, int target, int start) {
        if (sum == target) {
            ans.add(new ArrayList<Integer>(list));
            return;
        }

        if (sum > target) return; // backtrack when reach unvalid node

        for (int i = start; i < candidates.length; i++) {
            list.add(candidates[i]);
            backtrack(ans, list, sum + candidates[i], candidates, target, i);
            list.remove(list.size() - 1);
        }
    }
}
~~~

## 40. Combination Sum II (Medium)
Given a collection of candidate numbers (C) and a target number (T), find all unique combinations in C where the candidate numbers sums to T.

Each number in C may only be used once in the combination.

Note:
All numbers (including target) will be positive integers.
The solution set must not contain duplicate combinations.
For example, given candidate set [10, 1, 2, 7, 6, 1, 5] and target 8,
A solution set is:
~~~
[
  [1, 7],
  [1, 2, 5],
  [2, 6],
  [1, 1, 6]
]
~~~

#### Solution
**题目描述限定让问题变得简单，All numbers (including target) > 0**
**因为输入数组可以包含重复数字，关键是如何避免重复**

Time Complexity: Exponential O(2^n)
Space Complexity: the size of res list

Attempts: 3
~~~
public class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        if (candidates == null || candidates.length == 0) return ans;

        // sort the array
        Arrays.sort(candidates);
        backtrack(ans, new ArrayList<Integer>(), 0, candidates, target, 0);
        return ans;
    }

    private void backtrack(List<List<Integer>> ans, List<Integer> list, int sum, int[] nums, int target, int start) {
        if (sum == target) {
            ans.add(new ArrayList<Integer>(list));
        }

        // cz all nums > 0
        if (sum > target) {
            return;
        }

        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i - 1]) continue;
            list.add(nums[i]);
            // backtrack(ans, list, sum + nums[i], nums, target, start + 1); // bug
            backtrack(ans, list, sum + nums[i], nums, target, i + 1);
            list.remove(list.size() - 1);
        }
    }
}
~~~

## 216. Combination Sum III (Medium) (backtracking problem, input not 1D array)
Find all possible combinations of k numbers that add up to a number n, given that only numbers from 1 to 9 can be used and each combination should be a unique set of numbers.

~~~
Example 1:

Input: k = 3, n = 7

Output:

[[1,2,4]]

Example 2:

Input: k = 3, n = 9

Output:

[[1,2,6], [1,3,5], [2,3,4]]
~~~

#### Solution
Attempts: 4
~~~
public class Solution {
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        if (k <= 0 || n <= 0) return ans; // edge cases

        backtrack(ans, new ArrayList<Integer>(), k, n, 1);
        return ans;
    }

    private void backtrack(List<List<Integer>> ans, List<Integer> list, int k, int n, int start) {
        // base case
        if (k == 0) {
            if (n == 0) {
                // ans.add(new ArrayList<Integer>()); // bug
                ans.add(new ArrayList<Integer>(list));
            }
            return;
        }

        for (int i = start; i <= 9; i++) {
            list.add(i);
            // backtrack(ans, list, k + 1, n - i, i); // bug
            // backtrack(ans, list, k - 1, n - i, i); // bug
            backtrack(ans, list, k - 1, n - i, i + 1); // each num used only once
            list.remove(list.size() - 1);
        }
    }
}
~~~

## 377. Combination Sum IV
Given an integer array with all positive numbers and no duplicates, find the number of possible combinations that add up to a positive integer target.

Example:
~~~
nums = [1, 2, 3]
target = 4

The possible combination ways are:
(1, 1, 1, 1)
(1, 1, 2)
(1, 2, 1)
(1, 3)
(2, 1, 1)
(2, 2)
(3, 1)

Note that different sequences are counted as different combinations.

Therefore the output is 7.
~~~

Follow up:
What if negative numbers are allowed in the given array?
How does it change the problem?
What limitation we need to add to the question to allow negative numbers?

#### Solution
Method 1: Top-down recursion
~~~
public class Solution {
    public int combinationSum4(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        return combinationSum4Helper(nums, target, map);
    }

    public int combinationSum4Helper(int[] nums, int target, Map<Integer, Integer> map) {
        if (target == 0) {
            return 1;
        }
        if (map.containsKey(target)) {
            return map.get(target);
        }

        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] <= target) {
                int sub = combinationSum4Helper(nums, target - nums[i], map);
                map.put(target - nums[i], sub);
                ans += sub;
            }
        }
        return ans;
    }
}
~~~

Method 2: Bottom-up solution
~~~
public class Solution {
    public int combinationSum4(int[] nums, int target) {
        if (nums == null || nums.length == 0) return 0;

        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int i = 1; i < target + 1; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (i >= nums[j]) dp[i] += dp[i - nums[j]];
            }
        }
        return dp[target];
    }
}
~~~

# HashMap
## 128. Longest Consecutive Sequence (Hard) *
Given an unsorted array of integers, find the length of the longest consecutive elements sequence.

For example,
Given [100, 4, 200, 1, 3, 2],
The longest consecutive elements sequence is [1, 2, 3, 4]. Return its length: 4.

Your algorithm should run in O(n) complexity.

#### Solution
如果是O(nlgn)的话，我们可以先排序然后再遍历一遍即可。
1. 题目明确要求O(n), 使用HashMap, 每次遍历注意更新上边界和下边界的值
2. 使用HashSet, 类似于DFS一样去遍历数组的每个节点，每个节点只会被遍历一次，整体速度O(n)

Method 1
~~~
public class Solution {
    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int ans = 1;
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) continue;

            int left = map.getOrDefault(nums[i] - 1, 0);
            int right = map.getOrDefault(nums[i] + 1, 0);
            int count = left + right + 1;

            map.put(nums[i], count);
            ans = Math.max(ans, count);

            // update upper and lowe bound
            map.put(nums[i] - left, count);
            map.put(nums[i] + right, count);
        }

        return ans;
    }
}
~~~

Method 2
~~~
public class Solution {
    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int ans = 1;
        Set<Integer> set = new HashSet<Integer>();
        for (int num : nums) set.add(num);

        for (int num : nums) {
            if (set.contains(num)) {
                // remove the visited num
                set.remove(num);

                // dfs up
                int up = num;
                while (set.contains(up + 1)) {
                    set.remove(up + 1);
                    up++;
                }

                // dfs down
                int down = num;
                while (set.contains(down - 1)) {
                    set.remove(down - 1);
                    down--;
                }

                ans = Math.max(ans, up - down + 1);
            }
        }

        return ans;
    }
}
~~~

~~~
[面试经验] 电面 Google
第一题：给一个array,找出最长的连续片段   比如：[5,2,3,4,5,8,9]   就是2，3，4，5，返回4
我觉得这道题很眼熟，但是忘了最优解了，所以最开始写了一个看似两层循环但是time O(n)，space O(n)的。但是小哥觉得还可以优化，所以开始提示我了，然后我在自己代码的基础上改了改最后改成了time:O(n),space:O(1)最优解
~~~
