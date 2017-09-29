## Missing Ranges
Given a sorted integer array where the range of elements are in the inclusive range [lower, upper], return its missing ranges.

For example, given [0, 1, 3, 50, 75], lower = 0 and upper = 99, return ["2", "4->49", "51->74", "76->99"].

#### Solution
这题没算法可言，比较繁琐的数组题，考虑边界情况
1. 数组为空，但是lower, upper不为空
2. low > nums[0],...,nums[i]
3. high == nums[nums.length - 1] or high > nums[nums.length - 1]
4. 对数组的数字 +/- 1的时候会有overflow的风险
如何快速简洁也出代码
~~~
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
            if ((long)nums[i - 1] + 1 < (long)nums[i]) ans.add(helper(nums[i - 1] + 1, nums[i] - 1));
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
~~~

~~~
first is summary missing range problem. ［0 1 2 45 99］ output “3-44，46-98”
~~~

## 370. Range Addition
Assume you have an array of length n initialized with all 0's and are given k update operations.

Each operation is represented as a triplet: [startIndex, endIndex, inc] which increments each element of subarray A[startIndex ... endIndex] (startIndex and endIndex inclusive) with inc.

Return the modified array after all k operations were executed.

Example:
~~~
Given:

    length = 5,
    updates = [
        [1,  3,  2],
        [2,  4,  3],
        [0,  2, -2]
    ]

Output:

    [-2, 0, 3, 5, 3]
~~~

Explanation:
~~~
Initial state:
[ 0, 0, 0, 0, 0 ]

After applying operation [1, 3, 2]:
[ 0, 2, 2, 2, 0 ]

After applying operation [2, 4, 3]:
[ 0, 2, 5, 5, 3 ]

After applying operation [0, 2, -2]:
[-2, 0, 3, 5, 3 ]
~~~

#### Solution
1. Method 1: O(nk)
2. Method 2: O(n + k) Prefix Sum

Prefix Sum:
~~~
Initial state:
[ 0, 0, 0, 0, 0 ]

After applying operation [1, 3, 2]:
[ 0, 2, 0, 0, -2 ]

After applying operation [2, 4, 3]:
[ 0, 2, 3, 0, -2 ]

After applying operation [0, 2, -2]:
[ -2, 2, 3, 2, -2 ]

Sum Up:
[ -2, 0, 3, 5, 3 ]
~~~

~~~
public class Solution {
    public int[] getModifiedArray(int length, int[][] updates) {
        if (length < 1) return new int[0];
        if (updates == null || updates.length == 0 || updates[0].length != 3) return new int[length];

        int[] nums = new int[length];
        for (int i = 0; i < updates.length; i++) {
            int l = updates[i][0], r = updates[i][1], s = updates[i][2];
            if (l > r) continue;
            if (l >= 0) nums[l] += s;
            if (r + 1 < nums.length) nums[r + 1] -= s;
        }
        for (int i = 1; i < nums.length; i++) {
            nums[i] += nums[i - 1];
        }
        return nums;
    }
}
~~~

## 228. Summary Ranges
Given a sorted integer array without duplicates, return the summary of its ranges.

Example 1:
~~~
Input: [0,1,2,4,5,7]
Output: ["0->2","4->5","7"]
~~~

Example 2:
~~~
Input: [0,2,3,4,6,8,9]
Output: ["0","2->4","6","8->9"]
~~~

#### Solution
Method 1: 比较直接的写法
~~~
class Solution {
    public List<String> summaryRanges(int[] nums) {
        List<String> list = new ArrayList<String>();
        if (nums == null || nums.length == 0) return list;

        int start = nums[0];
        int i = 1;
        while (i < nums.length) {
            if (nums[i] == nums[i - 1] + 1) {
                i++;
                continue;
            }
            list.add(generateString(start, nums[i - 1]));
            start = nums[i];
            i++;
        }
        list.add(generateString(start, nums[nums.length - 1]));
        return list;
    }

    private String generateString(int start, int end) {
        if (start == end) {
            return String.valueOf(start);
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(start));
        sb.append("->");
        sb.append(String.valueOf(end));
        return sb.toString();
    }
}
~~~
