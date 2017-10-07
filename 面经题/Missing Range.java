// ## Missing Ranges
// Given a sorted integer array where the range of elements are in the inclusive range [lower, upper], return its missing ranges.
//
// For example, given [0, 1, 3, 50, 75], lower = 0 and upper = 99, return ["2", "4->49", "51->74", "76->99"].
//
// #### Solution
// 这题没算法可言，比较繁琐的数组题，考虑边界情况
// 1. 数组为空，但是lower, upper不为空
// 2. low > nums[0],...,nums[i]
// 3. high == nums[nums.length - 1] or high > nums[nums.length - 1]
// 4. 对数组的数字 +/- 1的时候会有overflow的风险
// 如何快速简洁也出代码

public class Solution {
    public List<String> findMissingRanges(int[] nums, int lower, int upper) {
        List<String> ans = new ArrayList<String>();
        if (nums == null || nums.length == 0) {
            ans.add(helper(lower, upper));
            return ans;
        }
        if (lower < nums[0]) {
            ans.add(helper(lower, nums[0] - 1));
        }
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] <= lower) continue;
            if ((long)nums[i - 1] + 1 < (long)nums[i]) {
              ans.add(helper(nums[i - 1] + 1, nums[i] - 1));
            }
        }
        if (upper > nums[nums.length - 1]) {
            ans.add(helper(nums[nums.length - 1] + 1, upper));
        }
        return ans;
    }

    private String helper(int start, int end) {
        if (start > end) return "";
        StringBuilder sb = new StringBuilder();
        if (start == end) sb.append(start);
        else sb.append(start).append("->").append(end);
        return sb.toString();
    }
}
