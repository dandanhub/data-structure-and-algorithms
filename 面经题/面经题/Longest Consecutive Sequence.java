// ## 128. Longest Consecutive Sequence (Hard) *
// Given an unsorted array of integers, find the length of the longest consecutive elements sequence.
//
// For example,
// Given [100, 4, 200, 1, 3, 2],
// The longest consecutive elements sequence is [1, 2, 3, 4]. Return its length: 4.
//
// Your algorithm should run in O(n) complexity.
//
// #### Solution
// 如果是O(nlgn)的话，我们可以先排序然后再遍历一遍即可。
// 1. 题目明确要求O(n), 使用HashMap, 每次遍历注意更新上边界和下边界的值
// 2. 使用HashSet, 类似于DFS一样去遍历数组的每个节点，每个节点只会被遍历一次，整体速度O(n)
//
// Method 1
// O(n) time and O(n) space
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


// Method 2
//
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
// ~~~
