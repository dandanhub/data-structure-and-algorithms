class Solution {
    public boolean judgePoint24(int[] nums) {
        if (nums == null || nums.length != 4) return false;

        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        permutation(ans, new ArrayList<Integer>(), nums, new boolean[4]);

        for (List<Integer> list : ans) {
            List<Double> values = calculate(list, 0, 3);
            for (Double d : values) {
                if (Math.abs(d - 24) <= 0.0000000001) return true;
            }
        }
        return false;
    }

    private void permutation(List<List<Integer>> ans, List<Integer> list, int[] nums, boolean[] used) {
        // base case
        if (list.size() == 4) {
            ans.add(new ArrayList<Integer>(list));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (used[i] || (i > 0 && nums[i] == nums[i - 1] && !used[i - 1])) continue;
            used[i] = true;
            list.add(nums[i]);
            permutation(ans, list, nums, used);
            list.remove(list.size() - 1);
            used[i] = false;
        }
    }

    private List<Double> calculate(List<Integer> list, int start, int end) {
        List<Double> ans = new ArrayList<Double>();

        if (start == end) {
            double num = (double) list.get(start);
            ans.add(num);
            return ans;
        }

        for (int i = start; i < end; i++) {
            List<Double> left = calculate(list, start, i);
            List<Double> right = calculate(list, i + 1, end);
            for (double num1 : left) {
                for (double num2 : right) {
                    ans.add(num1 + num2);
                    ans.add(num1 - num2);
                    ans.add(num1 * num2);
                    ans.add(num1 / num2);
                }
            }
        }
        return ans;
    }

    // [4, 1, 8, 7]
    // [1, 2, 1, 2]
    // [1, 9, 1, 2]
}
