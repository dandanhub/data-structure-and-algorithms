## 215. Kth Largest Element in an Array

Find the kth largest element in an unsorted array. Note that it is the kth largest element in the sorted order, not the kth distinct element.

For example,
Given [3,2,1,5,6,4] and k = 2, return 5.

Note:
You may assume k is always valid, 1 ≤ k ≤ array's length.

#### Solution
用Quick Sort来分段，选取pivot元素，一次quick sort后pivot左边的都小于pivot, 右边的都大于pivot, 然后每次丢弃一半的元素
1. average, O(n)
2. worst case O(n^2)

~~~
public class Solution {
    public int findKthLargest(int[] nums, int k) {
        return quickPartition(nums, 0, nums.length - 1, nums.length - k);
    }

    public int quickPartition(int[] nums, int start, int end, int k) {
        int pivot = end;
        int left = start;

        for (int i = start; i < end; i++) {
            if (nums[i] <= nums[pivot]) {
                swap(nums, i, left);
                left++;
            }
        }
        swap(nums, left, pivot);
        pivot = left;

        if (k == pivot) return nums[pivot];
        else if (k > pivot) {
            quickPartition(nums, pivot + 1, end, k);
        }
        else {
            quickPartition(nums, start, pivot - 1, k);
        }
        return Integer.MAX_VALUE;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
~~~
