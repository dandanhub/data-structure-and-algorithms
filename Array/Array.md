## 41. First Missing Positive
Given an unsorted integer array, find the first missing positive integer.

For example,
Given [1,2,0] return 3,
and [3,4,-1,1] return 2.

Your algorithm should run in O(n) time and uses constant space.

#### Solution
进行swap的三个条件：
1. 当前num[i] > 0
2. 当前num[i]不在正确的index位置上
3. 当前nums[i] - 1在范围内
4. **正确index位置上的数字是错误的，处理重复数字**

Attempt: 1
~~~
public class Solution {
    public int firstMissingPositive(int[] nums) {
        if (nums == null || nums.length == 0) return 1;
        int i = 0;
        while (i < nums.length) {
            // nums[i] is positive and not in its right position
            if (nums[i] > 0 && nums[i] - 1 < nums.length && nums[i] - 1 != i && nums[nums[i] - 1] != nums[i]) {
                swap(nums, i, nums[i] - 1);
            }
            else {
                i++;
            }
        }

        // scan the array again to find the 1st missing pos num
        for (int j = 0; j < nums.length; j++) {
            if (nums[j] - 1 != j) return j + 1;
        }

        return nums.length + 1;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
~~~

## 321. Create Maximum Number
Given two arrays of length m and n with digits 0-9 representing two numbers. Create the maximum number of length k <= m + n from digits of the two. The relative order of the digits from the same array must be preserved. Return an array of the k digits. You should try to optimize your time and space complexity.

Example 1:
nums1 = [3, 4, 6, 5]
nums2 = [9, 1, 2, 5, 8, 3]
k = 5
return [9, 8, 6, 5, 3]

Example 2:
nums1 = [6, 7]
nums2 = [6, 0, 4]
k = 5
return [6, 7, 6, 0, 4]

Example 3:
nums1 = [3, 9]
nums2 = [8, 9]
k = 3
return [9, 8, 9]

#### Solution
1. 找出array1中i个digit最大的数字，找出array2中k-i个digit最大的数字
2. 合并array1和array2得到的最大数字
- 在合并数组的时候，如何判定取array1还是array2的数字，考虑测试用例 <br>
~~~
33338
333

33338
339
~~~
- 如何加快代码速度和解题速度
3. 这题可以分解为找出array中k个digit能组成的最大数字，以及合并两个array，在保持原有数组顺序的基础上，得到最大数字

~~~
public class Solution {
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int m = nums1.length;
        int n = nums2.length;
        int[] ans = new int[k];
        for (int i = 0; i <= k; i++) {
            if (i <= m && k - i <= n) {
                int[] array1 = maxNumberFromArray(nums1, i);
                int[] array2 = maxNumberFromArray(nums2, k - i);
                int[] merged = merge(array1, array2);
                if (compareArray(merged, 0, ans, 0)) ans = merged;
            }
        }

        return ans;
    }

    // compare int[] nums1 with int[] nums2
    private boolean compareArray(int[] nums1, int s1, int[] nums2, int s2) {
        int i = s1, j = s2;
        int m = nums1.length, n = nums2.length;
        while (i < m && j < n && nums1[i] == nums2[j]) {
            i++;
            j++;
        }

        // verbose
        if ((i < m && j < n && nums1[i] > nums2[j]) || (i < m && j == n && nums1[i] >= nums1[s1])
                 || (i == m && j < n && nums2[j] < nums2[s2])) return true;

        return false;
    }

    // merge two num array
    private int[] merge(int[] nums1, int[] nums2) {
        int i = 0, j = 0, k = 0;
        int m = nums1.length, n = nums2.length;
        int[] ans = new int[m + n];
        while (i < m && j < n) {
            if (nums1[i] > nums2[j]) {
                ans[k++] = nums1[i++];
            }
            else if (nums1[i] < nums2[j]) {
                ans[k++] = nums2[j++];
            }
            else { // equal
                if (compareArray(nums1, i, nums2, j)) ans[k++] = nums1[i++];
                else ans[k++] = nums2[j++];
            }
        }

        while (i < m) {
            ans[k++] = nums1[i++];
        }
        while (j < n) {
            ans[k++] = nums2[j++];
        }

        return ans;
    }

    // 119ms
    // generate max k-digit number from one array
    private int[] maxNumberFromArray(int[] nums, int k) {
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < nums.length; i++) {
            while (nums.length - i + stack.size() > k && !stack.isEmpty() && nums[i] > stack.peek()) {
                stack.pop();
            }
            if (stack.size() < k) {
                stack.push(nums[i]);
            }
        }

        int[] ans = new int[k];
        int index = k - 1;
        while (!stack.isEmpty() && index >= 0) {
            ans[index--] = stack.pop();
        }

        return ans;
    }

    // 15 ms
    // generate max k-digit number from one array
    private int[] maxNumberFromArray(int[] nums, int k) {
        int[] ans = new int[k];
        int top = 0;
        for (int i = 0; i < nums.length; i++) {
            while (nums.length - i + top > k && top > 0 && nums[i] > ans[top - 1]) {
                top--;
            }
            if (top < k) ans[top++] = nums[i];
        }

        return ans;
    }
}
~~~
