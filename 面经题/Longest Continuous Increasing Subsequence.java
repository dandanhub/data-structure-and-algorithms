class Solution {
    public int findLengthOfLCIS(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int index = 0;
        int ans = 0;
        while (index < nums.length) {
            int cnt = 1;
            while (index < nums.length - 1 && nums[index + 1] > nums[index]) {
                index++;
                cnt++;
            }
            ans = Math.max(ans, cnt);
            index++;
        }
        return ans;
    }
}
