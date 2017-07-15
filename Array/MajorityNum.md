
# Majority Element
## 169. Majority Element (Easy)
Given an array of size n, find the majority element. The majority element is the element that appears more than ⌊ n/2 ⌋ times.

You may assume that the array is non-empty and the majority element always exist in the array.

#### Solution
Majority Voting Algorithm. Solve the problem in O(n) time and O(1) space.

~~~
public class Solution {
    public int majorityElement(int[] nums) {
        // edges cases
        if (nums == null || nums.length == 0) return 0;

        // iterate the nums array
        int count = 0;
        int majority = nums[0];
        for (int i = 0; i < nums.length; i++) {
            if (count == 0) {
                majority = nums[i];
                count++;
            }
            else if (nums[i] == majority) {
                count++;
            }
            else {
                count--;
            }
        }

        return majority;
    }

    // [1]
    // [1,1]
    // [1,2,1]
    // [1,1,1,1,2,2,2]
}
~~~

## 229. Majority Element II (Medium) *
Given an integer array of size n, find all elements that appear more than ⌊ n/3 ⌋ times. The algorithm should run in linear time and in O(1) space.

#### Solution
Maintain two candidate using majority voting algorithm and then iterate the array again to validate whether the two candidate are still valid.

~~~
public class Solution {
    public List<Integer> majorityElement(int[] nums) {
        List<Integer> ans = new ArrayList<Integer>();
        if (nums == null || nums.length == 0) return ans;

        int c1 = 0, c2 = 0;
        // int m1 = nums[0], m2 = nums[0]; // bug, test case [1]
        int m1 = 0, m2 = 1;
        for (int i = 0; i < nums.length; i++) {
            // 注意顺序
            if (nums[i] == m1) c1++;
            else if (nums[i] == m2) c2++;
            else if (c1 == 0) {
                m1 = nums[i];
                c1++;
            }
            else if (c2 == 0) {
                m2 = nums[i];
                c2++;
            }
            else {
                c1--;
                c2--;
            }
        }

        c1 = 0;
        c2 = 0;
        for (int num : nums) {
            if (num == m1) c1++;
            if (num == m2) c2++;
        }   

        if (c1 > nums.length / 3) ans.add(m1);
        if (c2 > nums.length / 3) ans.add(m2);

        return ans;
    }
}
~~~

## Majority Element III (LintCode)
Given an array of integers and a number k, the majority number is the number that occurs more than 1/k of the size of the array.
Find it.

Notice
There is only one majority number in the array.

Example
Given [3,1,2,3,2,3,3,4,4,4] and k=3, return 3.

Challenge
O(n) time and O(k) extra space

#### Solution
We maintain k - 1 counters using map. When we need to decrease the counters, the time complexity is k - 1, however, at most, we need to decrease the counter by n / (k - 1) times, so the time complexity is still O(n).

~~~
public class Solution {
    /**
     * @param nums: A list of integers
     * @param k: As described
     * @return: The majority number
     */
    public int majorityNumber(ArrayList<Integer> nums, int k) {
        // k candidates
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int num : nums) {
            if (map.containsKey(num)) map.put(num, map.get(num) + 1);
            else if (map.size() < k) map.put(num, 1);
            else {
                // for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                //     int key = entry.getKey();
                //     int value = entry.getValue();
                //     map.put(key, value - 1);
                //     if (map.get(key) == 0) map.remove(key);
                // } bug version
                Map<Integer, Integer> temp = new HashMap<Integer, Integer>();
                for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                    if (entry.getValue() - 1 > 0) {
                        temp.put(entry.getKey(), entry.getValue() - 1);
                    }
                }
                map = temp;
            }
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            map.put(entry.getKey(), 0);
        }
        for (int num : nums) {
            if (map.containsKey(num)) map.put(num, map.get(num) + 1);
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > nums.size() / k) return entry.getKey();
        }

        return -1;
    }
}

~~~

## Majority Element IV
Given an integer array of size n, find all elements that appear more than ⌊ n/k ⌋ times.

#### Solution
Time complexity: O(nk)
Space complexity: O(k)

~~~
public class Solution {
    public List<Integer> majorityElement(int[] nums) {
        return majorityKElement(nums, 3);
    }

    private List<Integer> majorityKElement(int[] nums, int k) {
        List<Integer> ans = new ArrayList<Integer>();
        if (nums == null || nums.length == 0 || k <= 0) return ans;

        int[] candidates = new int[k - 1];
        int[] counters = new int[k - 1];

        for (int num : nums) {
            boolean found = false;
            for (int i = 0; i < candidates.length; i++) {
                if (num == candidates[i]) {
                    counters[i]++;
                    found = true;
                    break;
                }
            }

            if (found) continue;

            for (int i = 0; i < counters.length; i++) {
                if (counters[i] == 0) {
                    candidates[i] = num;
                    counters[i] = 1;
                    found = true;
                    break;
                }
            }

            if (found) continue;

            for (int i = 0; i < counters.length; i++) {
                counters[i]--;
            }
        }

        Arrays.fill(counters, 0);
        for (int num : nums) {
            for (int i = 0; i < candidates.length; i++) {
                if (num == candidates[i]) {
                    counters[i]++;
                    break;
                }
            }
        }

        for (int i = 0; i < candidates.length; i++) {
            if (counters[i] > nums.length / k) ans.add(candidates[i]);
        }

        return ans;
    }
}
~~~

~~~
Snapchat 电面面经 求讨论！
array里只有一个数字出现超过50%，其他都只有一次。1. return超过50%次的数字。 follow up： array里只有一个数字出现超过一次，其他都只有一次。返回超过超过一次的数字的出现概率。要求：constant space, time: O(n).
** 可能follow-up题目描述有问题
~~~
