## 360. Sort Transformed Array
Given a sorted array of integers nums and integer values a, b and c. Apply a function of the form f(x) = ax2 + bx + c to each element x in the array.

The returned array must be in sorted order.

Expected time complexity: O(n)

Example:
nums = [-4, -2, 2, 4], a = 1, b = 3, c = 5,

Result: [3, 9, 15, 33]

nums = [-4, -2, 2, 4], a = -1, b = 3, c = 5

Result: [-23, -5, 1, 7]

#### Solution

~~~
class Solution {
    public int[] sortTransformedArray(int[] nums, int a, int b, int c) {
        if (nums == null || nums.length == 0) return nums;

        int len = nums.length;
        int[] ans = new int[len];
        int index = a >= 0 ? len - 1 : 0;
        int i = 0;
        int j = len - 1;
        while (i <= j) {
            int v1 = quad(nums[i], a, b, c);
            int v2 = quad(nums[j], a, b, c);
            if (a >= 0) {    
                if (v1 >= v2) {
                    ans[index--] = v1;
                    i++;
                }
                else {
                    ans[index--] = v2;
                    j--;
                }
            }
            else {
                if (v1 <= v2) {
                    ans[index++] = v1;
                    i++;
                }
                else {
                    ans[index++] = v2;
                    j--;
                }
            }
        }

        return ans;
    }

    private int quad(int x, int a, int b, int c) {
        return a * x * x + b * x + c;
    }
}
~~~
