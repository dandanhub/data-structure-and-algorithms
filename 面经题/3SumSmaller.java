// O(n^2)
// Attempt: 3
// ~~~
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
