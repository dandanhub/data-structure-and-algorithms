## 66. Plus One
Given a non-negative integer represented as a non-empty array of digits, plus one to the integer.

You may assume the integer do not contain any leading zero, except the number 0 itself.

The digits are stored such that the most significant digit is at the head of the list.

#### Solution
问题的关键是最终结果的长度不一定是n, 如何写出简洁的代码分辨两种情况。
参照[discussion](https://discuss.leetcode.com/topic/24288/my-simple-java-solution/16),唯一可能导致result长度为n+1的情况是输入的数字都是9，那么这个时候我们只要new一个长度为n+1的数组，然后把第一位设置成1，直接返回即可。
~~~
public class Solution {
    public int[] plusOne(int[] digits) {
        int n = digits.length;
        for (int i = n - 1; i >= 0; i--) {
            if (digits[i] < 9) {
                digits[i]++;
                return digits;
            }
            digits[i] = 0;
        }

        int[] ans = new int[n + 1];
        ans[0] = 1;
        return ans;
    }
}
~~~
